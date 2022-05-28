package com.example.cloudtravel.view;

import android.content.Context;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class Util {

    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        return progressDrawable;
    }

}
