package com.example.cloudtravel.model;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    @FormUrlEncoded
    @POST("User/synUser")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<UserModel> getUserInfo(@FieldMap HashMap<String ,Object> param);

    @FormUrlEncoded
    @POST("User/userInfo")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<UserModel> getUserSynConform(@FieldMap HashMap<String ,Object> param);

}
