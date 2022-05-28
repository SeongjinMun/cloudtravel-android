package com.example.cloudtravel.model;

import com.google.gson.annotations.SerializedName;

public class CourseModel {

    @SerializedName("id")
    int courseId;
    @SerializedName("rating")
    float courseRating;
    @SerializedName("views")
    String courseViews;
    @SerializedName("title")
    String courseTitle;
    @SerializedName("subtitle")
    String courseSubTitle;
    @SerializedName("url")
    String courseViewUrl;
    @SerializedName("tags")
    String courseTags;

    public CourseModel(int courseId, float courseRating, String courseViews,
                       String courseTitle, String courseSubTitle, String courseViewUrl, String courseTags) {
        this.courseId = courseId;
        this.courseRating = courseRating;
        this.courseViews = courseViews;
        this.courseTitle = courseTitle;
        this.courseSubTitle = courseSubTitle;
        this.courseViewUrl = courseViewUrl;
        this.courseTags = courseTags;
    }

    public int getCourseId() {
        return courseId;
    }

    public float getCourseRating() {
        return courseRating;
    }

    public String getCourseViews() {
        return courseViews;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseSubTitle() {
        return courseSubTitle;
    }

    public String getCourseViewUrl() {
        return courseViewUrl;
    }

    public String getCourseTags() {
        return courseTags;
    }

}
