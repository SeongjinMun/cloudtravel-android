package com.example.cloudtravel.view.fragment;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.example.cloudtravel.databinding.FragmentHomeBinding;
import com.example.cloudtravel.view.adapter.MainBannerAdapter;
import com.example.cloudtravel.view.adapter.MainBottomCourseAdapter;
import com.example.cloudtravel.view.adapter.MainMiddleCourseAdapter;
import com.example.cloudtravel.view.adapter.MainTopCourseAdapter;
import com.example.cloudtravel.viewModel.MainViewModel;
import com.example.cloudtravel.viewModel.UserViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final static String TAG = HomeFragment.class.getSimpleName();

    private final int USER_REGISTERED       = 1;
    private final int USER_REFRESH_TOKEN    = 2;
    private final int USER_LOGOUT           = 3;
    private final int USER_INFO             = 4;
    private final int USER_UPDATE           = 5;
    private final int USER_NOT              = 6;


    private MainViewModel viewModel;
    private UserViewModel userViewModel;
    private FragmentHomeBinding binding;

    private MainBannerAdapter bannerAdapter = new MainBannerAdapter(new ArrayList<>());
    private MainTopCourseAdapter topCourseAdapter = new MainTopCourseAdapter(new ArrayList<>());
    private MainMiddleCourseAdapter middleCourseAdapter = new MainMiddleCourseAdapter(new ArrayList<>());
    private MainBottomCourseAdapter bottomCourseAdapter = new MainBottomCourseAdapter(new ArrayList<>());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        initCognito();
        checkPermission();

        binding=FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.load();

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            int status = extras.getInt("mainStatus");
            userViewModel.userStatus.setValue(status);
            userViewModel.resist.setValue(true);
            Log.d("userViewModel.userStatus", userViewModel.userStatus.getValue().toString());
        }

        binding.bannerList.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.bannerList.setAdapter(bannerAdapter);

        binding.MainTopCourseList.setAdapter(topCourseAdapter);
        binding.MainTopCourseList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        binding.MainMiddleCourseList.setAdapter(middleCourseAdapter);
        binding.MainMiddleCourseList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        binding.MainBottomCourseList.setAdapter(bottomCourseAdapter);
        binding.MainBottomCourseList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));



        binding.textView2.setOnClickListener(v->{
            Log.d("TESTEMAIL", "setOnClickListener");

        });

        observeViewModel();
    }

        private void initCognito() {


        if (AWSMobileClient.getInstance().getConfiguration() == null){

            AWSMobileClient.getInstance().initialize(getActivity().getApplicationContext(), new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {

                    switch (userStateDetails.getUserState()){
                        case SIGNED_IN:
                            Log.d(TAG, "SIGNED_IN");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    userViewModel.userStatus.setValue(2);
                                    if (null != userStateDetails.getDetails()){
                                        userViewModel.token.setValue(userStateDetails.getDetails().get("token"));
                                        Log.d("SIGNED_IN", userViewModel.token.getValue());
                                    }
                                    if (null != AWSMobileClient.getInstance().getIdentityId()){
                                        userViewModel.email.setValue(AWSMobileClient.getInstance().getIdentityId());
                                        Log.d("SIGNED_IN", userViewModel.email.getValue());
                                    }
                                }

                            });
                            break;
                        default:
                            if (userStateDetails.getUserState() != UserState.SIGNED_OUT){
                                AWSMobileClient.getInstance().signOut();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != AWSMobileClient.getInstance().getIdentityId()){
                                        userViewModel.email.setValue("");
                                        userViewModel.token.setValue("");
                                        userViewModel.userStatus.setValue(6);
                                        userViewModel.login.setValue(false);
                                        userViewModel.userSyn.setValue(false);
                                    }
                                }

                            });
                            break;
                    }
                }
                @Override
                public void onError(Exception e) {
                    Log.e("INIT", e.toString());
                }
            });
        } else if (AWSMobileClient.getInstance().isSignedIn()){

            Log.d(TAG, "2");

            AWSMobileClient.getInstance().initialize(getActivity().getApplicationContext(), new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails result) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userViewModel.userStatus.setValue(2);
                            if (null != result.getDetails()){
                                userViewModel.token.setValue(result.getDetails().get("token"));
                                Log.d(TAG, userViewModel.token.getValue());
                            }
                            if (null != AWSMobileClient.getInstance().getIdentityId()){
                                userViewModel.email.setValue(AWSMobileClient.getInstance().getIdentityId());
                                Log.d(TAG, userViewModel.email.getValue());
                            }
                        }
                    });

                }

                @Override
                public void onError(Exception e) {

                }
            });

            Log.d(TAG, "Logined user");


        } else {
            userViewModel.userSyn.setValue(false);
            userViewModel.login.setValue(false);
            Log.d(TAG, "3");
            Log.d(TAG, "Logouted user");

            userViewModel.userStatus.setValue(3);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("userViewModel.userStatus", userViewModel.userStatus.getValue().toString());
                    userViewModel.email.setValue(AWSMobileClient.getInstance().getIdentityId());
                    userViewModel.token.setValue("");
                }
            });


        }
    }

    // CHECK PERMISSION_CAMERA
    private void checkPermission() {
    }

    private void hideKeyboard()
    {

        if (getActivity() != null && getActivity().getCurrentFocus() != null)
        {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void observeViewModel() {

        userViewModel.userStatus.observe(getViewLifecycleOwner(), userStatus->{
            if (userStatus != null){
                userViewModel.synUser();
            }
        });

        userViewModel.email.observe(getViewLifecycleOwner(), email -> {
            if (userViewModel.resist.getValue()){
                userViewModel.userStatus.setValue(USER_REGISTERED);
            }
            if (email != null){
                userViewModel.synUser();
            }
        });

        userViewModel.token.observe(getViewLifecycleOwner(), token -> {
            if (userViewModel.resist.getValue()){
                userViewModel.userStatus.setValue(USER_REGISTERED);
            }
            if (token != null){
                userViewModel.synUser();
            }
        });

        userViewModel.login.observe(getViewLifecycleOwner(), login -> {
            if (login != null && (userViewModel.login.getValue() != null)){
                userViewModel.getUserInfo();
            }
        });


        viewModel.banners.observe(getViewLifecycleOwner(), bannerModels -> {
            if (bannerModels != null) {
                binding.bannerList.setVisibility(View.VISIBLE);
                bannerAdapter.updateCourses(bannerModels);
            }
        });

        viewModel.topCourses.observe(getViewLifecycleOwner(), courseModels -> {
            if (courseModels != null) {
                binding.MainTopCourseList.setVisibility(View.VISIBLE);
                topCourseAdapter.updateCourses(courseModels);
            }
        });

        viewModel.middleCourses.observe(getViewLifecycleOwner(), courseModels -> {
            if (courseModels != null) {
                binding.MainMiddleCourseList.setVisibility(View.VISIBLE);
                middleCourseAdapter.updateCourses(courseModels);
            }
        });

        viewModel.bottomCourses.observe(getViewLifecycleOwner(), courseModels -> {
            if (courseModels != null) {
                binding.MainBottomCourseList.setVisibility(View.VISIBLE);
                bottomCourseAdapter.updateCourses(courseModels);
            }
        });
    }

    @Override
    public void onResume() {
        Log.d("onResume", "HomeFragmentResume");
        hideKeyboard();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}