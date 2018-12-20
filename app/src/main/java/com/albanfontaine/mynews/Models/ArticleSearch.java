package com.albanfontaine.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private String newsDesk;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }
}
