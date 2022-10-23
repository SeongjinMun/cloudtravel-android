package com.example.cloudtravel.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cloudtravel.model.BannerModel;
import com.example.cloudtravel.model.CourseModel;
import com.example.cloudtravel.model.Service;
import com.example.cloudtravel.view.MainActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    public MutableLiveData<List<BannerModel>> banners = new MutableLiveData<>();
    public MutableLiveData<List<CourseModel>> topCourses = new MutableLiveData<>();
    public MutableLiveData<List<CourseModel>> middleCourses = new MutableLiveData<>();
    public MutableLiveData<List<CourseModel>> bottomCourses = new MutableLiveData<>();
    public MutableLiveData<Boolean> bannerLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> courseLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> firstOpen = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<String> test = new MutableLiveData<>();

    public Service service = Service.getInstance();

    private final CompositeDisposable disposable = new CompositeDisposable();

    public void load() {

        loading.setValue(true);
        disposable.add(service.getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<BannerModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<BannerModel> bannerModels) {
                        banners.setValue(bannerModels);
                        bannerLoadError.setValue(false);
                        loading.setValue(false);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        bannerLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                }));

        disposable.add(service.getTopCourses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CourseModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<CourseModel> topCourseModels) {
                        topCourses.setValue(topCourseModels);
                        courseLoadError.setValue(false);
                        loading.setValue(false);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        courseLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                }));


        disposable.add(service.getMiddleCourses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CourseModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<CourseModel> middleCourseModels) {
                        middleCourses.setValue(middleCourseModels);
                        courseLoadError.setValue(false);
                        loading.setValue(false);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        courseLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                }));

        disposable.add(service.getBottomCourses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CourseModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<CourseModel> bottomCourseModels) {
                        bottomCourses.setValue(bottomCourseModels);
                        courseLoadError.setValue(false);
                        loading.setValue(false);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        courseLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
