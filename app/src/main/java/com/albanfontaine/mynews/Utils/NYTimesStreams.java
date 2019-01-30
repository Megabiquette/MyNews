package com.albanfontaine.mynews.Utils;

import android.util.Log;

import com.albanfontaine.mynews.Models.ApiResponseMostPopuplar;
import com.albanfontaine.mynews.Models.ApiResponseSearch;
import com.albanfontaine.mynews.Models.ApiResponseTopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTimesStreams {

    // Top Stories API
    public static Observable<ApiResponseTopStories> streamFetchTopStoriesArticles(){
        NYTimesService nytService = NYTimesService.retrofit.create(NYTimesService.class);
        return nytService.fetchTopStoriesArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Most Popular API
    public static Observable<ApiResponseMostPopuplar> streamFetchMostPopularArticles(){
        NYTimesService nytService = NYTimesService.retrofit.create(NYTimesService.class);
        return nytService.fetchMostPopularArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Search API for categories
    public static Observable<ApiResponseSearch> streamFetchCategoryArticles(String section){
        NYTimesService nytService = NYTimesService.retrofit.create(NYTimesService.class);
        return nytService.fetchCategoryArticles(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Search API for searches and notifications
    public static Observable<ApiResponseSearch> streamFetchSearchArticles(String query, String category, String beginDate, String endDate){
        NYTimesService nytService = NYTimesService.retrofit.create(NYTimesService.class);
        return nytService.fetchSearchArticles(query, category, beginDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
