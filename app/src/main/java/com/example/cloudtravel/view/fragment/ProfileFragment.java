package com.example.cloudtravel.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.cloudtravel.R;
import com.example.cloudtravel.databinding.FragmentProfileBinding;
import com.example.cloudtravel.view.AuthMainActivity;
import com.example.cloudtravel.view.FilterActivity;
import com.example.cloudtravel.view.SettingActivity;
import com.example.cloudtravel.viewModel.MainViewModel;
import com.example.cloudtravel.viewModel.UserViewModel;

public class ProfileFragment extends Fragment {

    private final static String TAG = ProfileFragment.class.getSimpleName();
    private FragmentProfileBinding binding;
    private MainViewModel viewModel;
    private UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        userViewModel  = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("onViewCreated", userViewModel.token.getValue());


        binding.loginButton.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), AuthMainActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.profileSet.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            intent.putExtra("eamil", userViewModel.email.getValue());
            intent.putExtra("token", userViewModel.token.getValue());
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode== Activity.RESULT_OK){
                if (data!=null){
                    if (data.getStringExtra("nickName") != null){
                        userViewModel.nickName.setValue(data.getStringExtra("nickName"));
                    }


//
//                    switch (){
//                        case "성별선택" :
//                            userSex = 0;
//                            break;
//                        case "남성" :
//                            userSex = 1;
//                            break;
//                        case "여성" :
//                            userSex = 2;
//                            break;
//                    }


                }
            }
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}