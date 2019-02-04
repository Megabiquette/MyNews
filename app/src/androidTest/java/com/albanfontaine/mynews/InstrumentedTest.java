package com.albanfontaine.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.albanfontaine.mynews.Models.ApiResponseMostPopuplar;
import com.albanfontaine.mynews.Models.ApiResponseSearch;
import com.albanfontaine.mynews.Models.ApiResponseTopStories;
import com.albanfontaine.mynews.Utils.Helper;
import com.albanfontaine.mynews.Utils.NYTimesStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    Context context = InstrumentationRegistry.getTargetContext();

    // Top Stories API
    @Test
    public void fetchTopStoriesArticlesTest() throws Exception{
        Observable<ApiResponseTopStories> observableArticles =
                NYTimesStreams.streamFetchTopStoriesArticles();

        TestObserver<ApiResponseTopStories> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        ApiResponseTopStories articlesFetched = testObserver.values().get(0);

        // Checks that the responses gets at least 1 article
        assertNotEquals(articlesFetched.getResults().size(),0);

        ApiResponseTopStories.Result article = articlesFetched.getResults().get(0);
        // Checks that the article has a title
        assertNotNull ("Article has title", article.getTitle());
        // Checks that the article has a date
        assertNotNull ("Article has date", article.getPublishedDate());
        // Checks that the article has a section
        assertNotNull ("Article has section", article.getSection());
        // Checks that the article has a url
        assertNotNull ("Article has URL", article.getUrl());
    }

    // Most Popular API
    @Test
    public void fetchMostPopularArticlesTest() throws Exception{
        Observable<ApiResponseMostPopuplar> observableArticles =
                NYTimesStreams.streamFetchMostPopularArticles();

        TestObserver<ApiResponseMostPopuplar> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        ApiResponseMostPopuplar articlesFetched = testObserver.values().get(0);

        // Checks that the responses gets at least 1 article
        assertNotEquals(articlesFetched.getResults().size(),0);

        ApiResponseMostPopuplar.Result article = articlesFetched.getResults().get(0);
        // Checks that the article has a title
        assertNotNull ("Article has title", article.getTitle());
        // Checks that the article has a date
        assertNotNull ("Article has date", article.getPublishedDate());
        // Checks that the article has a section
        assertNotNull ("Article has section", article.getSection());
        // Checks that the article has a url
        assertNotNull ("Article has URL", article.getUrl());
    }

    // Search API for categories
    @Test
    public void fetchCategoryArticlesTest() throws Exception{
        Observable<ApiResponseSearch> observableArticles =
                NYTimesStreams.streamFetchCategoryArticles("news_desk:(\"Arts\")");

        TestObserver<ApiResponseSearch> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        ApiResponseSearch articlesFetched = testObserver.values().get(0);

        // Checks that the responses gets at least 1 article
        assertNotEquals(articlesFetched.getResponse().getDocs().size(),0);

        ApiResponseSearch.Doc article = articlesFetched.getResponse().getDocs().get(0);
        // Checks that the article has a title
        assertNotNull ("Article has title", article.getHeadline().getMain());
        // Checks that the article has a date
        assertNotNull ("Article has date", article.getPubDate());
        // Checks that the article is in the "Arts" section
        assertEquals("Arts", article.getNewsDesk());
        // Checks that the article has a url
        assertNotNull ("Article has URL", article.getWebUrl());
    }

    // Put a date in the spinner in DD/MM/YYYY format and in a variable in YYYYMMDD format for the API calls
    @Test
    public void dateProcessingTest(){

        TextView spinner = new TextView(context);
        int year = 2019;
        int month = 0;
        int day = 1;
        String date;

        date = Helper.processDate(year, month, day, spinner);

        assertEquals("01/01/2019", spinner.getText());
        assertEquals("20190101", date);
    }

    @Test
    public void dateValidationTest(){
        String beginDate = "20190211";
        String endDate = "20190302";

        assertTrue(Helper.datesAreValid(context, beginDate, endDate));

        beginDate = "20190211";
        endDate = "20190102";

        assertFalse(Helper.datesAreValid(context, beginDate, endDate));
    }
/*
    @Test
    public void parametersValidationTest(){
        EditText searchField = new EditText(context);
        CheckBox cbArts = new CheckBox(context);
        CheckBox cbBusiness = new CheckBox(context);
        CheckBox cbPolitics = new CheckBox(context);
        CheckBox cbTravel = new CheckBox(context);


    }*/
}
