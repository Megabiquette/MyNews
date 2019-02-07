package com.albanfontaine.mynews.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.albanfontaine.mynews.Models.ApiResponseMostPopuplar;
import com.albanfontaine.mynews.Models.ApiResponseSearch;
import com.albanfontaine.mynews.Models.ApiResponseTopStories;
import com.albanfontaine.mynews.Models.Article;
import com.albanfontaine.mynews.R;
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

public class MainFragment extends Fragment {

    @BindView(R.id.fragment_main_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_progressBar) ProgressBar mProgressBar;
    @BindView(R.id.fragment_main_text_info) TextView mTextViewInfo;

    // Key for the Bundle
    private static final String KEY_POSITION = "position";

    // Tabs as constants
    private final int TAB_TOP_STORIES = 0;
    private final int TAB_MOST_POPULAR = 1;
    private final int TAB_ARTS = 2;
    private final int TAB_BUSINESS = 3;
    private final int TAB_POLITICS = 4;
    private final int TAB_TRAVEL = 5;

    private Disposable mDisposable;
    private List<Article> mArticles;
    private ArticleAdapter mAdapter;

    public MainFragment() { }

    public static MainFragment newInstance(int position){
        MainFragment frag = new MainFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, result);

        this.configureRecyclerView();
        this.configureOnClickRecyclerView();

        // Checks the internet connection
        if(!Helper.isConnectedToInternet(getContext())){
            // No internet
            mTextViewInfo.setVisibility(View.VISIBLE);
            mTextViewInfo.setText(getResources().getString(R.string.no_internet));
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        }else{
            // Gets which tab is currently viewed
            int position = getArguments().getInt(KEY_POSITION, 0);
            // Execute request according to the tab
            switch (position){
                case TAB_TOP_STORIES:
                    this.executeTopStoriesRequest();
                    break;
                case TAB_MOST_POPULAR:
                    this.executeMostPopularRequest();
                    break;
                case TAB_ARTS:
                    this.executeCategoryRequest("news_desk:(\"Arts\")");
                    break;
                case TAB_BUSINESS:
                    this.executeCategoryRequest("news_desk:(\"Business\")");
                    break;
                case TAB_POLITICS:
                    this.executeCategoryRequest("news_desk:(\"Politics\")");
                    break;
                case TAB_TRAVEL:
                    this.executeCategoryRequest("news_desk:(\"Travel\")");
                    break;
            }
        }
        return result;
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
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // Checks internet connection
                        if(!Helper.isConnectedToInternet(getContext())){
                            // No internet
                            Toast.makeText(getContext(), getResources().getString(R.string.no_internet),
                                    Toast.LENGTH_LONG).show();
                        }else{
                            // Starts new activity to read the article
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            intent.putExtra("ARTICLE_URL", mAdapter.getArticleUrl(position));
                            startActivity(intent);
                        }
                    }
                });
    }

    ///////////////////
    // HTTP REQUESTS //
    ///////////////////

    private void executeTopStoriesRequest(){
        this.mDisposable = NYTimesStreams.streamFetchTopStoriesArticles()
                .subscribeWith(new DisposableObserver<ApiResponseTopStories>(){
                    @Override
                    public void onNext(ApiResponseTopStories apiResponseTopStories) {
                        updateUIForTopStoriesArticles(apiResponseTopStories);
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

    private void executeMostPopularRequest(){
        this.mDisposable = NYTimesStreams.streamFetchMostPopularArticles()
                .subscribeWith(new DisposableObserver<ApiResponseMostPopuplar>(){
                    @Override
                    public void onNext(ApiResponseMostPopuplar apiResponseMostPopuplar) {
                        updateUIForMostPopularArticles(apiResponseMostPopuplar);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mProgressBar.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();

                    }
                });
    }

    private void executeCategoryRequest(String category){
        this.mDisposable = NYTimesStreams.streamFetchCategoryArticles(category)
                .subscribeWith(new DisposableObserver<ApiResponseSearch>(){
                    @Override
                    public void onNext(ApiResponseSearch apiResponseSearch) {
                        updateUIForCategoryArticles(apiResponseSearch);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mProgressBar.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();

                    }
                });
    }

    ////////////////
    // UI UPDATES //
    ////////////////

    private void updateUIForTopStoriesArticles(ApiResponseTopStories response){
        for (ApiResponseTopStories.Result result : response.getResults()){
            // Creates an Article object
            String section = result.getSection();
            if(!result.getSubsection().isEmpty()){
                section += " > " + result.getSubsection();
            }
            String date = Helper.formatDate(result.getPublishedDate());
            String title = result.getTitle();
            String url = result.getUrl();
            String thumbnail = "";
            if(!result.getMultimedia().isEmpty()){
                thumbnail = result.getMultimedia().get(0).getUrl();
            }
            mArticles.add(new Article(section, date, title, url, thumbnail));
        }
    }

    private void updateUIForMostPopularArticles(ApiResponseMostPopuplar response){
        for (ApiResponseMostPopuplar.Result result : response.getResults()){
            String section = result.getSection();
            String date = Helper.formatDate(result.getPublishedDate());
            String title = result.getTitle();
            String url = result.getUrl();
            String thumbnail = "";
            if(!result.getMedia().get(0).getMediaMetadata().isEmpty()){
                thumbnail =  result.getMedia().get(0).getMediaMetadata().get(1).getUrl();
            }
            mArticles.add(new Article(section, date, title, url, thumbnail));
        }
    }

    private void updateUIForCategoryArticles(ApiResponseSearch response){
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
    }
}
