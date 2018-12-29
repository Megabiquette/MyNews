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

    public static Observable<ApiResponseTopStories> streamFetchTopStoriesArticles(){
        NYTimesService nytService = NYTimesService.retrofit.create(NYTimesService.class);
        Log.e("STREAM", "TS");
        return nytService.fetchTopStoriesArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ApiResponseMostPopuplar> streamFetchMostPopularArticles(){
        NYTimesService nytService = NYTimesService.retrofit.create(NYTimesService.class);
        Log.e("STREAM", "MP");

        return nytService.fetchMostPopularArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ApiResponseSearch> streamFetchCategoryArticles(String section){
        Log.e("STREAM", "SEARCH");
        NYTimesService nytService = NYTimesService.retrofit.create(NYTimesService.class);
        return nytService.fetchCategoryArticles(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
