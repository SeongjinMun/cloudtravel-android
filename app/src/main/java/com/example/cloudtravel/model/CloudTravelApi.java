package com.example.cloudtravel.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CloudTravelApi {
    @GET("Course/courseList")
    Single<List<CourseModel>> getCourses(@Query("tags") String tags, @Query("page") int page);

    @GET("Banner/mainBannerList")
    Single<List<BannerModel>> getBanners();

}
