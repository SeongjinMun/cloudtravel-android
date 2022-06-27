package com.example.cloudtravel.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.SignInResult;
import com.example.cloudtravel.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener{

    private static final String TAG = LoginActivity.class.getSimpleName();

    Validator validator;

    @BindView(R.id.loginEmail)
    @NotEmpty
    @Email
    EditText email;

    @BindView(R.id.loginPassword)
    @Password(min = 8, scheme = Password.Scheme.ANY)
    EditText password;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        context = this;
    }

    @Override
    public void onValidationSucceeded() {
        _signIn(email.getText().toString(), password.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(LoginActivity.this,
                                    "Sign-in done.", Toast.LENGTH_SHORT).show();
                            CommonAction.openMain(context);
                            break;
                        case SMS_MFA:
                            Toast.makeText(LoginActivity.this,
                                    "Please confirm sign-in with SMS.", Toast.LENGTH_SHORT).show();
                            break;
                        case NEW_PASSWORD_REQUIRED:
                            Toast.makeText(LoginActivity.this,
                                    "Please confirm sign-in with new password.", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(LoginActivity.this,
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
                        Toast.makeText(LoginActivity.this,
                                ((AmazonServiceException) e).getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void doLogin(View view) {
        validator.validate();
    }
}