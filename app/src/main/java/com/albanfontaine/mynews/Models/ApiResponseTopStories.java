package com.albanfontaine.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseTopStories {
    @SerializedName("results")
    @Expose
    private List<ArticleTopStories> results = null;

    public List<ArticleTopStories> getResults() {
        return results;
    }


    public class ArticleTopStories {
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("subsection")
        @Expose
        private String subsection;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("multimedia")
        @Expose
        private List<Multimedium> multimedia = null;

        public String getSection() {
            return section;
        }

        public String getSubsection() {
            return subsection;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public List<Multimedium> getMultimedia() { return multimedia; }
    }

    public class Multimedium {
        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }
    }
}

