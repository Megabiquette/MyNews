package com.albanfontaine.mynews;

import android.support.test.runner.AndroidJUnit4;

import com.albanfontaine.mynews.Models.ApiResponseTopStories;
import com.albanfontaine.mynews.Utils.NYTimesStreams;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;


@RunWith(AndroidJUnit4.class)
public class MainFragmentTest {

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
        assert (article.getTitle() != null);
        // Checks that the article has a date
        assert (article.getPublishedDate() != null);
        // Checks that the article has a section
        assert (article.getSection() != null);
        // Checks that the article has a url
        assert (article.getUrl() != null);
    }
}
