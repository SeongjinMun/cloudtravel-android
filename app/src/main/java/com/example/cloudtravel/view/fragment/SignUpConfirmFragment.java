package com.example.cloudtravel.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloudtravel.R;
import com.example.cloudtravel.databinding.FragmentSignUpComfirmBinding;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpConfirmFragment extends Fragment {

    private static final String TAG = SignUpConfirmFragment.class.getSimpleName();

    @BindView(R.id.confirmCode)
    @NotEmpty
    EditText etConfirmCode;

    private FragmentSignUpComfirmBinding binding;

    private OnFragmentInteractionListener mListener;

    public SignUpConfirmFragment() {
        // Required empty public constructor
    }

    public static SignUpConfirmFragment newInstance(String param1, String param2) {
        SignUpConfirmFragment fragment = new SignUpConfirmFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_comfirm, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

    public interface OnFragmentInteractionListener {
        void confirmSignUp(String responseCode);
    }

    @OnClick(R.id.SignUpConfirmButton)
    void doConfirm() {
        mListener.confirmSignUp(etConfirmCode.getText().toString());
    }
}