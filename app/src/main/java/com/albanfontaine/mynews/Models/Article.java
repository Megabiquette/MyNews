package com.albanfontaine.mynews.Models;


public class Article {
    private String mSection;
    private String mDate;
    private String mTitle;
    private String mUrl;
    private String mThumbnail;

    public Article(String section, String date, String title, String url, String thumbnail){
        this.mSection = section;
        this.mDate = date;
        this.mTitle = title;
        this.mUrl = url;
        this.mThumbnail = thumbnail;
    }

    public String getSection() { return mSection; }

    public String getDate() { return mDate; }

    public String getTitle() { return mTitle; }

    public String getUrl() { return mUrl; }

    public String getThumbnail() { return mThumbnail; }
}
