package com.albanfontaine.mynews;

import android.support.test.runner.AndroidJUnit4;

import com.albanfontaine.mynews.Models.ApiResponseMostPopuplar;
import com.albanfontaine.mynews.Models.ApiResponseSearch;
import com.albanfontaine.mynews.Models.ApiResponseTopStories;
import com.albanfontaine.mynews.Utils.NYTimesStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

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
}
