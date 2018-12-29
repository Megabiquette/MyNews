package com.albanfontaine.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseSearch {
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() { return response; }
    public void setResponse(Response response) { this.response = response; }


    public class Response {
        @SerializedName("docs")
        @Expose
        private List<ArticleSearch> docs = null;

        public List<ArticleSearch> getDocs() {
            return docs;
        }
        public void setDocs(List<ArticleSearch> docs) {
            this.docs = docs;
        }
    }


    public class ArticleSearch {
        @SerializedName("web_url")
        @Expose
        private String webUrl;
        @SerializedName("headline")
        @Expose
        private Headline headline;
        @SerializedName("pub_date")
        @Expose
        private String pubDate;
        @SerializedName("news_desk")
        @Expose
        private String news_desk;
        @SerializedName("section_name")
        @Expose
        private String section_name;

        public String getWebUrl() {
            return webUrl;
        }

        public Headline getHeadline() { return headline; }

        public String getPubDate() {
            return pubDate;
        }

        public String getNewsDesk() { return news_desk; }

        public String getSection_name() { return section_name; }
        }


    public class Headline {
        @SerializedName("main")
        @Expose
        private String main;

        public String getMain() {
            return main;
        }
    }

}
