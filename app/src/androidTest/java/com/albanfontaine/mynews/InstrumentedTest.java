package com.albanfontaine.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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

        // Checks that the responses gets 40 articles
        //assertEquals(articlesFetched.getResults().size(),40);

        ApiResponseTopStories.Result article = articlesFetched.getResults().get(0);
        // Checks that the article has a title
        assertNotNull ("Article has title", article.getTitle());
        // Checks that the article has a date
        assertNotNull ("Article has date", article.getPublishedDate());
        // Checks that the article has a section
        assertNotNull ("Artidle has section", article.getSection());
        // Checks that the article has a url
        assertNotNull ("Article has URL", article.getUrl());
    }
}
