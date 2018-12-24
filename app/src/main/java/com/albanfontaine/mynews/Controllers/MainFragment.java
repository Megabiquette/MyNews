package com.albanfontaine.mynews.Controllers;


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

import com.albanfontaine.mynews.Models.ApiResponseMostPopuplar;
import com.albanfontaine.mynews.Models.ApiResponseSearch;
import com.albanfontaine.mynews.Models.ApiResponseTopStories;
import com.albanfontaine.mynews.Models.Article;
import com.albanfontaine.mynews.R;
import com.albanfontaine.mynews.Utils.NYTimesStreams;
import com.albanfontaine.mynews.Views.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MainFragment extends Fragment {

    @BindView(R.id.fragment_main_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_progressBar) ProgressBar mProgressBar;
    @BindView(R.id.frag_test) TextView mTextView;

    // Create keys for our Bundle
    private static final String KEY_POSITION="position";
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

        // Gets which tab is currently viewed
        int position = getArguments().getInt(KEY_POSITION, 0);
        // Execute request according to the tab
        switch (position){
            case 0:
                this.executeTopStoriesRequest();
                break;
            case 1:
                this.executeMostPopularRequest();
                break;
            case 2:
                this.executeSearchRequest("news_desk:(\"Arts\")");
                break;
            case 3:
                this.executeSearchRequest("news_desk:(\"Business\")");
                break;
            case 4:
                this.executeSearchRequest("news_desk:(\"Politics\")");
                break;
            case 5:
                this.executeSearchRequest("news_desk:(\"Travel\")");
                break;
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

    private void configureRecyclerView(){
        // Configures the RecyclerView and its components
        this.mArticles = new ArrayList<>();
        this.mAdapter = new ArticleAdapter(this.mArticles);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                    public void onError(Throwable e) {
                        Log.e("TAG", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mProgressBar.setVisibility(View.GONE);
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
                    }
                });
    }

    private void executeSearchRequest(String category){
        this.mDisposable = NYTimesStreams.streamFetchSearchArticles(category)
                .subscribeWith(new DisposableObserver<ApiResponseSearch>(){
                    @Override
                    public void onNext(ApiResponseSearch apiResponseSearch) {
                        updateUIForSearchArticles(apiResponseSearch, category);
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

    private void updateUIForTopStoriesArticles(ApiResponseTopStories response){
        for (ApiResponseTopStories.ArticleTopStories result : response.getResults()){
            // Creates an Article object
            String section = result.getSection();
            if(!result.getSubsection().isEmpty()){
                section += " > " + result.getSubsection();
            }
            String date = formatDate(result.getPublishedDate());
            String title = result.getTitle();
            String url = result.getUrl();
            String thumbnail = "";
            if(!result.getMultimedia().isEmpty()){
                thumbnail = result.getMultimedia().get(0).getUrl();
            }
            mArticles.add(new Article(section, date, title, url, thumbnail));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void updateUIForMostPopularArticles(ApiResponseMostPopuplar response){
        for (ApiResponseMostPopuplar.ArticleMostPopular result : response.getResults()){
            String section = result.getSection();
            String date = formatDate(result.getPublishedDate());
            String title = result.getTitle();
            String url = result.getUrl();
            String thumbnail = "";
            if(!result.getMedia().get(0).getMediaMetadata().isEmpty()){
                thumbnail =  result.getMedia().get(0).getMediaMetadata().get(1).getUrl();
            }
            mArticles.add(new Article(section, date, title, url, thumbnail));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void updateUIForSearchArticles(ApiResponseSearch response, String category){
        for (ApiResponseSearch.ArticleSearch result : response.getResponse().getDocs()){
            String section = result.getSection_name();
            String date = formatDate(result.getPubDate());
            String title = result.getHeadline().getMain();
            String url = result.getWebUrl();
            String thumbnail = "";
            mArticles.add(new Article(section, date, title, url, thumbnail));
        }
        mAdapter.notifyDataSetChanged();
    }

    private String formatDate(String date){
        // Puts the date in DD/MM/YY format
        return date.substring(8, 10)+"/"+date.substring(5, 7)+"/"+date.substring(2,4);
    }

}
