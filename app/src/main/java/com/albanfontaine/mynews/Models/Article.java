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
    public void setSection(String section) { mSection = section; }

    public String getDate() { return mDate; }
    public void setDate(String date) { mDate = date; }

    public String getTitle() { return mTitle; }
    public void setTitle(String title) { mTitle = title; }

    public String getUrl() { return mUrl; }
    public void setUrl(String url) { mUrl = url; }

    public String getThumbnail() { return mThumbnail; }
    public void setThumbnail(String thumbnail) { mThumbnail = thumbnail; }
}
