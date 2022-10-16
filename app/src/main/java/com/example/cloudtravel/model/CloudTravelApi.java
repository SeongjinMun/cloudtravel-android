package com.example.cloudtravel.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CloudTravelApi {
    @GET("Course/courseList")
    Single<List<CourseModel>> getCourses(@Query("tags") String tags, @Query("title") String title, @Query("page") int page);

    @GET("Course/mainTopCourseList")
    Single<List<CourseModel>> getTopCourses();

    @GET("Course/mainMiddleCourseList")
    Single<List<CourseModel>> getMiddleCourses();

    @GET("Course/mainBottomCourseList")
    Single<List<CourseModel>> getBottomCourses();

    @GET("Banner/mainBannerList")
    Single<List<BannerModel>> getBanners();

    @GET("User/userUpConform")
    Single<List<BannerModel>> getUpConform();
}
