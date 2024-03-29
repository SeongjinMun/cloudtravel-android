package com.example.cloudtravel.view;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;

public class Util {

    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        return progressDrawable;
    }

    public static void checkSession(Context context, String move) {
        // Add code here
        AWSMobileClient.getInstance().addUserStateListener(new UserStateListener() {
            @Override
            public void onUserStateChanged(UserStateDetails userStateDetails) {
                switch(userStateDetails.getUserState()){
                    case SIGNED_IN:
                        Log.i("checkSession", "user signed in");

                        break;
                    default:
                        Log.i("checkSession", "unsupported");

                        break;
                }
            }
        });
    }

}
