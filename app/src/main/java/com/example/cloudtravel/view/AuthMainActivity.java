package com.example.cloudtravel.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.HostedUIOptions;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.example.cloudtravel.databinding.ActivityAuthMainBinding;

public class AuthMainActivity extends AppCompatActivity {

    private static final String TAG = AuthMainActivity.class.getSimpleName();

    ActivityAuthMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding = ActivityAuthMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonAction.checkSession(this, true);

        binding.facebookLoginButton.setOnClickListener(v->{
            openFacebookLogin();
        });

        binding.emailLoginButton.setOnClickListener(v->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.signupButton.setOnClickListener(v->{
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

    }

    private void openFacebookLogin() {
        HostedUIOptions hostedUIOptions = HostedUIOptions.builder()
                .scopes("openid", "email")
                .identityProvider("Facebook")
                .build();

        SignInUIOptions signInUIOptions = SignInUIOptions.builder()
                .hostedUIOptions(hostedUIOptions)
                .build();

        AWSMobileClient.getInstance().showSignIn((Activity) this, signInUIOptions, new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails details) {
                Log.d(TAG, "onResult: " + details.getUserState());
                try {
                    Log.d("asdasdasd", AWSMobileClient.getInstance().getTokens().getIdToken().getTokenString().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Exception e) {
                Log.e(TAG, "onError: ", e);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent activityIntent = getIntent();
        if (activityIntent.getData() != null &&
                "myapp".equals(activityIntent.getData().getScheme())) {
            if (AWSMobileClient.getInstance().handleAuthResponse(activityIntent)){
                Intent intent = new Intent(AuthMainActivity.this, MainActivity.class);
                intent.putExtra("moveFragment", "profile");
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }
}