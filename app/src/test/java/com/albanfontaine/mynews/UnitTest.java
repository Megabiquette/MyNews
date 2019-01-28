package com.albanfontaine.mynews;

import com.albanfontaine.mynews.Models.ApiResponseTopStories;
import com.albanfontaine.mynews.Utils.NYTimesStreams;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

public class UnitTest {
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
        assertEquals(articlesFetched.getResults().size(),40);

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