package com.albanfontaine.mynews.Utils;

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
        return nytService.getTopStoriesArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ApiResponseMostPopuplar> streamFetchMostPopularArticles(){
        NYTimesService nytService = NYTimesService.retrofit.create(NYTimesService.class);
        return nytService.getMostPopularArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ApiResponseSearch> streamFetchSearchArticles(String section){
        NYTimesService nytService = NYTimesService.retrofit.create(NYTimesService.class);
        return nytService.getSearchArticles(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
