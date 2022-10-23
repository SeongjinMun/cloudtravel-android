package com.example.cloudtravel.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.example.cloudtravel.model.CourseModel;
import com.example.cloudtravel.model.Service;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CourseViewModel extends ViewModel {

    public MutableLiveData<List<CourseModel>> courses = new MutableLiveData<>();
    public MutableLiveData<Boolean> courseLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Integer> coursePage = new MutableLiveData<>(0);
    public MutableLiveData<String> courseTags = new MutableLiveData<>("");
    public MutableLiveData<String> searchTitle = new MutableLiveData<>("");

    public Service service = Service.getInstance();

    private final CompositeDisposable disposable = new CompositeDisposable();

    public void reset() {
        coursePage.setValue(0);
    }

    public void load() {
        loading.setValue(true);
        disposable.add(service.getCourses(courseTags.getValue(), searchTitle.getValue(), coursePage.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CourseModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<CourseModel> courseModels) {
                        Log.d("onSuccess", Integer.toString(coursePage.getValue()));
                        coursePage.setValue(coursePage.getValue()+1);
                        courses.setValue(courseModels);
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