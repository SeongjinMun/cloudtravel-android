package com.example.cloudtravel.view;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.SignInResult;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;
import com.example.cloudtravel.R;
import com.example.cloudtravel.view.fragment.SignUpConfirmFragment;
import com.example.cloudtravel.view.fragment.SignUpFragment;
import com.example.cloudtravel.viewModel.UserViewModel;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends FragmentActivity
        implements SignUpFragment.OnFragmentInteractionListener,
        SignUpConfirmFragment.OnFragmentInteractionListener {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private String userName, password;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setSignUpFragment();
    }

    private void setSignUpFragment() {
        SignUpFragment signUpFragment = new SignUpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.signup_layout, signUpFragment);
        transaction.commit();
    }

    private void setSignUpConfirmFragment() {
        SignUpConfirmFragment signUpConfirmFragment = new SignUpConfirmFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.signup_layout, signUpConfirmFragment);
        transaction.commit();
    }

    @Override
    public void signUp(String email, String password) {
        userName = email;
        this.password = password;

        final Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email);

        AWSMobileClient.getInstance().signUp(userName, password, attributes, null, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(() -> {
                    if (!signUpResult.getConfirmationState()) {
                        final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                        Toast.makeText(SignUpActivity.this,
                                "Confirm sign-up with: " + details.getDestination(), Toast.LENGTH_SHORT).show();
                        setSignUpConfirmFragment();

                    } else {
                        Toast.makeText(SignUpActivity.this, "else Sign-up done.", Toast.LENGTH_SHORT).show();




                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Sign-up error", e);
                runOnUiThread(() -> {
                    if (e instanceof AmazonServiceException){
                        Toast.makeText(SignUpActivity.this,
                                ((AmazonServiceException) e).getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void confirmSignUp(String code) {
        AWSMobileClient.getInstance().confirmSignUp(userName, code, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(() -> {
                    Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                    if (!signUpResult.getConfirmationState()) {
                        final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                        Toast.makeText(SignUpActivity.this,
                                "확인코드를 전송했습니다: " + details.getDestination(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                        _signIn(userName, password);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Confirm sign-up error", e);
                runOnUiThread(() -> {
                    if (e instanceof AmazonServiceException){
                        Toast.makeText(SignUpActivity.this,
                                ((AmazonServiceException) e).getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void _signIn(String username, String password) {
        // Add code here
        AWSMobileClient.getInstance().signIn(username, password, null, new Callback<SignInResult>() {
            @Override
            public void onResult(final SignInResult signInResult) {
                runOnUiThread(() -> {
                    Log.d(TAG, "Sign-in callback state: " + signInResult.getSignInState());
                    switch (signInResult.getSignInState()) {
                        case DONE:
                            Toast.makeText(SignUpActivity.this,
                                    "환영합니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("mainStatus", 1);
                            startActivity(intent);
                            break;
                        case SMS_MFA:
                            Toast.makeText(SignUpActivity.this,
                                    "Please confirm sign-in with SMS.", Toast.LENGTH_SHORT).show();
                            break;
                        case NEW_PASSWORD_REQUIRED:
                            Toast.makeText(SignUpActivity.this,
                                    "Please confirm sign-in with new password.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(SignUpActivity.this,
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
                        Toast.makeText(SignUpActivity.this,
                                ((AmazonServiceException) e).getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}