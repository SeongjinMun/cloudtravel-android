package com.example.cloudtravel.model;

import com.google.gson.annotations.SerializedName;

public class CommonModel {

    @SerializedName("type")
    int responseType;

    public int getResponseType() {
        return responseType;
    }

    public void setResponseType(int responseType) {
        this.responseType = responseType;
    }

    public CommonModel(int responseType) {
        this.responseType = responseType;
    }
}
