package com.example.cloudtravel.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    private static final String BASE_URL = "https://d0901311-061f-454f-82f0-0e23f9cf5201.mock.pstmn.io/";
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

    //
    public Single<List<CourseModel>> getCourses(String tags, int page) {
        return api.getCourses(tags, page);
    }

    //
    public Single<List<BannerModel>> getBanners() {
        return api.getBanners();
    }
    
}
