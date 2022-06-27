package com.example.cloudtravel.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudtravel.R;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class SignUpFragment extends Fragment implements Validator.ValidationListener {

    private static final String TAG = SignUpFragment.class.getSimpleName();

    @BindView(R.id.sigupEmail)
    @NotEmpty
    @Email
    EditText email;

    @BindView(R.id.sigupPassword)
    @Password(min = 8, scheme = Password.Scheme.ANY)
    EditText etPassword;

    @BindView(R.id.sigupPasswordConfirm)
    @ConfirmPassword
    EditText etPassword1;

    Validator validator;

    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        validator = new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onValidationSucceeded() {
        mListener.signUp(email.getText().toString(), etPassword.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void signUp(String email, String password);
    }

    @OnClick(R.id.registrationButton)
    void doSignUp() {
        Log.d(TAG, "doSignUp");
        validator.validate();
    }

}