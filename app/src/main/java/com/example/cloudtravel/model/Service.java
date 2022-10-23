package com.example.cloudtravel.model;

import java.util.HashMap;
import java.util.List;
import java.util.SplittableRandom;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    private static final String BASE_URL = "http://3.38.192.7/";
//    private static final String BASE_URL = "https://cloudtvl.com/";
    private static Service instance;

    public CloudTravelApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CloudTravelApi.class);

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public Single<List<CourseModel>> getCourses(String tags, String title, int page) {
        return api.getCourses(tags, title, page);
    }

    public Single<List<CourseModel>> getTopCourses() {
        return api.getTopCourses();
    }

    public Single<List<CourseModel>> getMiddleCourses() {
        return api.getMiddleCourses();
    }

    public Single<List<CourseModel>> getBottomCourses() {
        return api.getBottomCourses();
    }

    public Single<List<BannerModel>> getBanners() {
        return api.getBanners();
    }


    
}
