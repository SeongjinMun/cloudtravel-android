package com.example.cloudtravel.model;

import com.google.gson.annotations.SerializedName;

public class BannerModel {

    @SerializedName("title")
    String courseTitle;
    @SerializedName("url")
    String bannerViewUrl;
    @SerializedName("courseId")
    String courseId;

    public BannerModel(String courseTitle, String bannerViewUrl, String courseId) {
        this.courseTitle = courseTitle;
        this.bannerViewUrl = bannerViewUrl;
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getBannerViewUrl() {
        return bannerViewUrl;
    }

    public String getCourseId() {
        return courseId;
    }

}
