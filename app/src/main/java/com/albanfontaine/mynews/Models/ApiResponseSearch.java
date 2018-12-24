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
        @SerializedName("section_name")
        @Expose
        private String section_name;

        public String getWebUrl() {
            return webUrl;
        }
        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public Headline getHeadline() { return headline; }
        public void setHeadline(Headline headline) {
            this.headline = headline;
        }

        public String getPubDate() {
            return pubDate;
        }
        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getSection_name() { return section_name; }
        public void setSection_name(String section_name) { this.section_name = section_name; }
    }


    public class Headline {
        @SerializedName("main")
        @Expose
        private String main;

        public String getMain() {
            return main;
        }
        public void setMain(String main) {
            this.main = main;
        }
    }
}
