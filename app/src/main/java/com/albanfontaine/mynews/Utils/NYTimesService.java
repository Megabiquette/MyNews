package com.albanfontaine.mynews.Utils;

import com.albanfontaine.mynews.Models.ApiResponseMostPopuplar;
import com.albanfontaine.mynews.Models.ApiResponseSearch;
import com.albanfontaine.mynews.Models.ApiResponseTopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NYTimesService {

    String API_KEY = "190abb26a35547f29b03a63c6c5bf084";

    // Top Stories
    @GET("topstories/v2/home.json?api-key="+API_KEY)
    Observable<ApiResponseTopStories> fetchTopStoriesArticles();

    // Most Popular
    @GET("mostpopular/v2/mostviewed/all-sections/7.json?api-key="+API_KEY)
    Observable<ApiResponseMostPopuplar> fetchMostPopularArticles();

    // Categories
    @GET("search/v2/articlesearch.json?sort=newest&fl=web_url,headline,multimedia,news_desk,pub_date&api-key="+API_KEY)
    Observable<ApiResponseSearch> fetchCategoryArticles(@Query("fq") String section);

    // Search activity
    @GET("search/v2/articlesearch.json?sort=newest&fl=web_url,headline,multimedia,news_desk,pub_date&api-key="+API_KEY)
    Observable<ApiResponseSearch> fetchSearchArticles(@Query("q") String query,
                                                      @Query("fq") String section,
                                                    @Query("begin_date") String beginDate,
                                                    @Query("ebd_date") String endDate);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

}
