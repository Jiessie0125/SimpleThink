package com.example.simple.simplethink.model.bean;

import android.graphics.Bitmap;

/**
 * Created by mobileteam on 2019/7/22.
 */

public class ShareMediaBean {

    private String text;
    private String imagePath;
    private String imageUrl;
    private String filePath;
    private Bitmap imageData;
    private int shareType;
    private String title;
    private String titleUrl;
    private String site;
    private String siteUrl;
    private String url;
    private String musicUrl;

    public String getText() {
        return text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public Bitmap getImageData() {
        return imageData;
    }

    public int getShareType() {
        return shareType;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public String getSite() {
        return site;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setImageData(Bitmap imageData) {
        this.imageData = imageData;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }
}
