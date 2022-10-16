package com.example.cloudtravel.model;

import com.google.gson.annotations.SerializedName;

public class BannerModel {

    @SerializedName("title")
    String courseTitle;
    @SerializedName("url")
    String bannerViewUrl;
    @SerializedName("courseId")
    String courseId;
    @SerializedName("link")
    String bannerLink;

    public BannerModel(String courseTitle, String bannerViewUrl, String courseId, String bannerLink) {
        this.courseTitle = courseTitle;
        this.bannerViewUrl = bannerViewUrl;
        this.courseId = courseId;
        this.bannerLink = bannerLink;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getBannerViewUrl() {
        return bannerViewUrl;
    }

    public String getBannerLink() { return bannerLink; }

    public String getCourseId() {
        return courseId;
    }

}
