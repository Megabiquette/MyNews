package com.albanfontaine.mynews.Controllers;

import android.content.Intent;
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
import com.albanfontaine.mynews.Utils.Constants;
import com.albanfontaine.mynews.Utils.ItemClickSupport;
import com.albanfontaine.mynews.Utils.NYTimesStreams;
import com.albanfontaine.mynews.Views.ArticleAdapter;
import com.albanfontaine.mynews.Utils.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.fragment_main_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_progressBar) ProgressBar mProgressBar;
    @BindView(R.id.fragment_main_text_info) TextView mTextViewInfo;

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
        mQuery = intent.getStringExtra(Constants.QUERY);
        mCategory = intent.getStringExtra(Constants.CATEGORY);
        mBeginDate = intent.getStringExtra(Constants.BEGIN_DATE);
        mEndDate = intent.getStringExtra(Constants.END_DATE);

        if(!Helper.isConnectedToInternet(getBaseContext())){
            // No internet
            mTextViewInfo.setVisibility(View.VISIBLE);
            mTextViewInfo.setText(getResources().getString(R.string.no_internet));
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
                    public void onError(Throwable e) { Log.e("TAG", e.getMessage()); }

                    @Override
                    public void onComplete() {
                        mProgressBar.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void updateUIForSearchArticles(ApiResponseSearch response){
        for (ApiResponseSearch.Doc result : response.getResponse().getDocs()){
            String section = result.getNewsDesk();
            String date = Helper.formatDate(result.getPubDate());
            String title = result.getHeadline().getMain();
            String url = result.getWebUrl();
            String thumbnail = "";
            if(!result.getMultimedia().isEmpty()){
                thumbnail = getResources().getString(R.string.new_york_times_dns) + result.getMultimedia().get(2).getUrl();
            }
            mArticles.add(new Article(section, date, title, url, thumbnail));
        }

        // In case no article was found
        if(response.getResponse().getDocs().isEmpty()){
            mTextViewInfo.setText(R.string.no_article_found);
            mTextViewInfo.setVisibility(View.VISIBLE);
        }
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
                        if(!Helper.isConnectedToInternet(getBaseContext())){
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
}
