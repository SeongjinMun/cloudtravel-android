package com.example.cloudtravel.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.HostedUIOptions;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignInResult;
import com.example.cloudtravel.databinding.ActivityAuthMainBinding;

public class AuthMainActivity extends AppCompatActivity{

    private static final String TAG = AuthMainActivity.class.getSimpleName();
    private Context context;

    ActivityAuthMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding = ActivityAuthMainBinding.inflate(getLayoutInflater());
        context = this;
        setContentView(binding.getRoot());

        CommonAction.checkSession(this, true);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        binding.emailLogin.requestFocus();

        binding.facebookLoginButton.setOnClickListener(v->{
            openFacebookLogin();
        });

        binding.emailLoginButton.setOnClickListener(v->{
            _signIn(binding.emailLogin.getText().toString(), binding.passLogin.getText().toString());
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

    private void _signIn(String userName, String password) {
        AWSMobileClient.getInstance().signIn(userName, password, null, new Callback<SignInResult>() {
            @Override
            public void onResult(final SignInResult signInResult) {
                runOnUiThread(() -> {
                    Log.d(TAG, "Sign-in callback state: " + signInResult.getSignInState());
                    switch (signInResult.getSignInState()) {
                        case DONE:
                            Toast.makeText(AuthMainActivity.this,
                                    "Sign-in done.", Toast.LENGTH_SHORT).show();
                            CommonAction.openMain(context);
                            break;
                        case SMS_MFA:
                            Toast.makeText(AuthMainActivity.this,
                                    "Please confirm sign-in with SMS.", Toast.LENGTH_SHORT).show();
                            break;
                        case NEW_PASSWORD_REQUIRED:
                            Toast.makeText(AuthMainActivity.this,
                                    "Please confirm sign-in with new password.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(AuthMainActivity.this,
                                    "Unsupported sign-in confirmation: " + signInResult.getSignInState(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Sign-in error", e);
                runOnUiThread(() -> {
                    if (e instanceof AmazonServiceException){
                        Toast.makeText(AuthMainActivity.this,
                                ((AmazonServiceException) e).getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}