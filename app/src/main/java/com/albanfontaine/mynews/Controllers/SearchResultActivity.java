package com.albanfontaine.mynews.Controllers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.albanfontaine.mynews.Models.ApiResponseSearch;
import com.albanfontaine.mynews.Models.Article;
import com.albanfontaine.mynews.R;
import com.albanfontaine.mynews.Utils.ItemClickSupport;
import com.albanfontaine.mynews.Utils.NYTimesStreams;
import com.albanfontaine.mynews.Views.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.fragment_main_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_progressBar) ProgressBar mProgressBar;
    @BindView(R.id.fragment_main_connection) TextView mTextViewConnection;

    // Keys for the Bundle
    private static final String QUERY = "query";
    private static final String CATEGORY = "category";
    private static final String BEGIN_DATE = "beginDate";
    private static final String END_DATE = "endDate";

    private Disposable mDisposable;
    private List<Article> mArticles;
    private ArticleAdapter mAdapter;

    private String mQuery;
    private String mCategory;
    private String mBeginDate;
    private String mEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        ButterKnife.bind(this);

        this.configureRecyclerView();
        this.configureOnClickRecyclerView();

        Intent intent = getIntent();
        mQuery = intent.getStringExtra(QUERY);
        mCategory = intent.getStringExtra(CATEGORY);
        mBeginDate = intent.getStringExtra(BEGIN_DATE);
        mEndDate = intent.getStringExtra(END_DATE);

        if(!isConnectedToInternet()){
            // No internet
            mTextViewConnection.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            this.executeSearchRequest();
        }
    }

    private void executeSearchRequest(){
        // Sets the dates to NULL if field is empty
        if(mBeginDate.isEmpty())
            mBeginDate = null;
        if(mEndDate.isEmpty())
            mEndDate = null;

        this.mDisposable = NYTimesStreams.streamFetchSearchArticles(mQuery, mCategory, mBeginDate, mEndDate)
                .subscribeWith(new DisposableObserver<ApiResponseSearch>(){
                    @Override
                    public void onNext(ApiResponseSearch apiResponseSearch) {
                        updateUIForSearchArticles(apiResponseSearch);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void updateUIForSearchArticles(ApiResponseSearch response){
        for (ApiResponseSearch.ArticleSearch result : response.getResponse().getDocs()){
            String section = result.getNewsDesk();
            String date = formatDate(result.getPubDate());
            String title = result.getHeadline().getMain();
            String url = result.getWebUrl();
            String thumbnail = "";
            mArticles.add(new Article(section, date, title, url, thumbnail));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void disposeWhenDestroy(){
        if(this.mDisposable != null && !this.mDisposable.isDisposed()){
            this.mDisposable.dispose();
        }
    }

    ////////////////////
    // CONFIGURATIONS //
    ////////////////////

    private void configureRecyclerView(){
        // Configures the RecyclerView and its components
        this.mArticles = new ArrayList<>();
        this.mAdapter = new ArticleAdapter(this.mArticles);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // Checks internet connection
                        if(!isConnectedToInternet()){
                            // No internet
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.no_internet),
                                    Toast.LENGTH_LONG).show();
                        }else{
                            // Starts new activity to read the article
                            Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                            intent.putExtra("ARTICLE_URL", mAdapter.getArticleUrl(position));
                            startActivity(intent);
                        }
                    }
                });
    }

    ///////////
    // UTILS //
    ///////////

    // Puts the date in DD/MM/YY format
    private String formatDate(String date){
        return date.substring(8, 10)+"/"+date.substring(5, 7)+"/"+date.substring(2,4);
    }

    // Checks the internet connection
    private boolean isConnectedToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        return network != null && network.isConnectedOrConnecting();
    }
}
