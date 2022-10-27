package com.example.cloudtravel.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cloudtravel.model.Service;
import com.example.cloudtravel.model.UserModel;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel{


    private final int USER_REGISTERED       = 1;
    private final int USER_REFRESH_TOKEN    = 2;
    private final int USER_LOGOUT           = 3;
    private final int USER_INFO             = 4;
    private final int USER_UPDATE           = 5;
    private final int USER_NOT              = 6;

    private final int REQUEST_OK            = 200;
    private final int REQUEST_ERROR         = 202;

    public MutableLiveData<String> email    = new MutableLiveData<>("");
    public MutableLiveData<String> token    = new MutableLiveData<>("");
    public MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> userSyn = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> login   = new MutableLiveData<>();
    public MutableLiveData<Boolean> resist  = new MutableLiveData<>(false);

    public MutableLiveData<String> nickName = new MutableLiveData<>("");
    public MutableLiveData<String> birthdate = new MutableLiveData<>("");
    public MutableLiveData<Integer> userSex = new MutableLiveData<>(0);
    public MutableLiveData<Integer> userExp = new MutableLiveData<>(0);
    public MutableLiveData<String>  userView = new MutableLiveData<>("");

    public MutableLiveData<Integer> requestType = new MutableLiveData<>(0);
    public MutableLiveData<Integer> userStatus  = new MutableLiveData<>(4);

    public Service service = Service.getInstance();

    private final CompositeDisposable disposable = new CompositeDisposable();


    public void synUser(){


        if (email.getValue() != ""){
            switch (userStatus.getValue()){
                case USER_REGISTERED:
                    requestType.setValue(1);
                    userSyn();
                    break;
                case USER_REFRESH_TOKEN:
                    requestType.setValue(2);
                    if (token.getValue() != ""){
                        userSyn();
                    }
                    break;
                case USER_LOGOUT:
                    break;
                case USER_INFO:
                    break;
            }
        }

        if (email.getValue() != null){
            Log.d("email.email()", email.getValue());
        }
        if (userStatus.getValue().toString() != null){
            Log.d("email.userStatus()", userStatus.getValue().toString());
        }
        if (email.getValue() != null){
            Log.d("email.requestType()", requestType.getValue().toString());
        }
        if (email.getValue() != null){
            Log.d("email.email()", email.getValue());
        }


    }

    public void userSyn(){

        Log.d("userStatus.start()", "start response");
        loading.setValue(true);

        HashMap<String, Object> input = new HashMap<>();
        input.put("type", requestType.getValue());
        input.put("email", email.getValue());
        input.put("token", token.getValue());

        Log.d("REQUEST4", Integer.toString(requestType.getValue()));
        Log.d("REQUEST5", email.getValue());
        Log.d("REQUEST6", token.getValue());

        service.api.getUserSynConform(input).enqueue(new Callback<UserModel>() {

            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponseType() == REQUEST_OK){
                        userSyn.setValue(false);
                        loading.setValue(false);
                        login.setValue(true);
                    }
                    Log.d("REQUEST7", "REQUEST" + input.toString());
                    Log.d("REQUEST7", "REQUEST" + response.message().toString());
                    Log.d("REQUEST7", "REQUEST" + Integer.toString(response.body().getResponseType()));
                    Log.d("REQUEST8", "REQUEST" + response.body().toString());
                }
                Log.d("REQUEST01", "REQUEST" + input.toString());
                Log.d("REQUEST01", "REQUEST" + response.message().toString());
                Log.d("REQUEST01", "REQUEST" + response.headers().toString());
                Log.d("REQUEST01", "REQUEST" + response.raw().toString());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("onFailure", t.toString());
                loading.setValue(false);
                if (!userSyn.getValue()){
                    userSyn.setValue(false);
                    login.setValue(false);
                }

            }
        });

    }

    public void getUserInfo(){

        HashMap<String, Object> input = new HashMap<>();
        input.put("email", email.getValue());

        service.api.getUserSynConform(input).enqueue(new Callback<UserModel>() {

            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponseType() == REQUEST_OK){
                        if (response.body().getUserNickname() != null){
                            nickName.setValue(response.body().getUserNickname());
                        }
                        if (response.body().getUserBirthdate() != null){
                            birthdate.setValue(response.body().getUserBirthdate());
                        }
                        if (response.body().getUserSex() != 0){
                            userSex.setValue(response.body().getUserSex());
                        }
                        if (response.body().getUserExp() != 0){
                            userExp.setValue(response.body().getUserExp());
                        }
                        if (response.body().getUserView() != null){
                            userView.setValue(response.body().getUserView());
                        }
                    }
                    login.setValue(true);
                    loading.setValue(false);
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }
        });

    }

    public void updateUserInfo(){

        HashMap<String, Object> input = new HashMap<>();
        input.put("email", email.getValue());
        input.put("token", email.getValue());
        input.put("nickname", nickName.getValue());
        input.put("birthdate", birthdate.getValue());
        input.put("sex", userSex.getValue());

        service.api.getUserSynConform(input).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponseType() == REQUEST_OK){
                        if (response.body().getUserNickname() != null){
                            nickName.setValue(response.body().getUserNickname());
                        }
                        if (response.body().getUserBirthdate() != null){
                            birthdate.setValue(response.body().getUserBirthdate());
                        }
                        if (response.body().getUserSex() != 0){
                            userSex.setValue(response.body().getUserSex());
                        }
                        if (response.body().getUserExp() != 0){
                            userExp.setValue(response.body().getUserExp());
                        }
                        if (response.body().getUserView() != null){
                            userView.setValue(response.body().getUserView());
                        }
                    }
                    loading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }
        });

    }


}

