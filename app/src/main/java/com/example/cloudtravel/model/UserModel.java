package com.example.cloudtravel.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("email")
    String userEmail;
    @SerializedName("token")
    String userToken;
    @SerializedName("nickname")
    String userNickname;
    @SerializedName("birthdate")
    String userBirthdate;
    @SerializedName("sex")
    int userSex;
    @SerializedName("exp")
    int userExp;
    @SerializedName("view")
    String userView;
    @SerializedName("type")
    int responseType;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserBirthdate() {
        return userBirthdate;
    }

    public void setUserBirthdate(String userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public int getUserExp() {
        return userExp;
    }

    public void setUserExp(int userExp) {
        this.userExp = userExp;
    }

    public String getUserView() {
        return userView;
    }

    public void setUserView(String userView) {
        this.userView = userView;
    }

    public int getResponseType() {
        return responseType;
    }

    public void setResponseType(int responseType) {
        this.responseType = responseType;
    }

    public UserModel(String userEmail, String userToken, String userNickname, String userBirthdate, int userSex, int userExp, String userView, int responseType) {
        this.userEmail = userEmail;
        this.userToken = userToken;
        this.userNickname = userNickname;
        this.userBirthdate = userBirthdate;
        this.userSex = userSex;
        this.userExp = userExp;
        this.userView = userView;
        this.responseType = responseType;
    }
}
