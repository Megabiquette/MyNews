package com.albanfontaine.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseSearch {
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() { return response; }


    public class Response {
        @SerializedName("docs")
        @Expose
        private List<Doc> docs = null;

        public List<Doc> getDocs() { return docs; }
    }


    public class Doc {
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
        @SerializedName("multimedia")
        @Expose
        private List<Multimedium> multimedia = null;

        public String getWebUrl() {
            return webUrl;
        }

        public Headline getHeadline() { return headline; }

        public String getPubDate() {return pubDate; }

        public String getNewsDesk() { return news_desk; }

        public List<Multimedium> getMultimedia() { return multimedia; }
        }


    public class Headline {
        @SerializedName("main")
        @Expose
        private String main;

        public String getMain() { return main; }
    }

    public class Multimedium {

        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() { return url; }
    }

}
