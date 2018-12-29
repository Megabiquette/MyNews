package com.albanfontaine.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseMostPopuplar {
    @SerializedName("results")
    @Expose
    private List<ArticleMostPopular> results = null;

    public List<ArticleMostPopular> getResults() {
        return results;
    }
    public void setResults(List<ArticleMostPopular> results) {
        this.results = results;
    }


    public class ArticleMostPopular {
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("media")
        @Expose
        private List<Medium> media = null;

        public String getUrl() {
            return url;
        }

        public String getSection() {
            return section;
        }

        public String getTitle() {
            return title;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public List<Medium> getMedia() {
            return media;
        }
    }

    public class Medium {
        @SerializedName("media-metadata")
        @Expose
        private List<MediaMetadatum> mediaMetadata = null;

        public List<MediaMetadatum> getMediaMetadata() {
            return mediaMetadata;
        }
    }

    public class MediaMetadatum {
        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }
    }

}


