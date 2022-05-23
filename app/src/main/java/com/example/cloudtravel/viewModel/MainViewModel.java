package com.example.cloudtravel.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cloudtravel.model.BannerModel;
import com.example.cloudtravel.model.Service;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    public MutableLiveData<List<BannerModel>> banners = new MutableLiveData<>();
    public MutableLiveData<Boolean> bannerLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<String> courseTags = new MutableLiveData<>("");

    public Service service = Service.getInstance();

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }


}
