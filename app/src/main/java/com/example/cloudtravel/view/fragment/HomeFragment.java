package com.example.cloudtravel.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.example.cloudtravel.databinding.FragmentHomeBinding;
import com.example.cloudtravel.view.adapter.MainBannerAdapter;
//import com.example.cloudtravel.view.adapter.MainBottomCourse;
//import com.example.cloudtravel.view.adapter.MainMiddleCourse;
//import com.example.cloudtravel.view.adapter.MainTopCourse;
import com.example.cloudtravel.view.adapter.MainBottomCourseAdapter;
import com.example.cloudtravel.view.adapter.MainMiddleCourseAdapter;
import com.example.cloudtravel.view.adapter.MainTopCourseAdapter;
import com.example.cloudtravel.viewModel.MainViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final static String TAG = HomeFragment.class.getSimpleName();

    private MainViewModel viewModel;
    private FragmentHomeBinding binding;

    private MainBannerAdapter bannerAdapter = new MainBannerAdapter(new ArrayList<>());
    private MainTopCourseAdapter topCourseAdapter = new MainTopCourseAdapter(new ArrayList<>());
    private MainMiddleCourseAdapter middleCourseAdapter = new MainMiddleCourseAdapter(new ArrayList<>());
    private MainBottomCourseAdapter bottomCourseAdapter = new MainBottomCourseAdapter(new ArrayList<>());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        checkPermission();
        initCognito();

        binding=FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.load();

        binding.bannerList.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.bannerList.setAdapter(bannerAdapter);

        binding.MainTopCourseList.setAdapter(topCourseAdapter);
        binding.MainTopCourseList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        binding.MainMiddleCourseList.setAdapter(middleCourseAdapter);
        binding.MainMiddleCourseList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        binding.MainBottomCourseList.setAdapter(bottomCourseAdapter);
        binding.MainBottomCourseList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));


        observeViewModel();
    }

    private void initCognito() {
        if (AWSMobileClient.getInstance().getConfiguration() == null){
            // Initialize user
            AWSMobileClient.getInstance().initialize(getActivity().getApplicationContext(), new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    switch (userStateDetails.getUserState()){
                        case SIGNED_IN:
                            Log.d(TAG, "SIGNED_IN");
                            break;
                        case SIGNED_OUT:
                            Log.d(TAG, "Do nothing yet");
                            break;
                        default:
                            AWSMobileClient.getInstance().signOut();
                            Log.d(TAG, "AWSMobileClient.getInstance().signOut()");
                            break;
                    }
                }
                @Override
                public void onError(Exception e) {
                    Log.e("INIT", e.toString());
                }
            });
        } else if (AWSMobileClient.getInstance().isSignedIn()){
            // Logined user
            Log.d(TAG, "Logined user");
        } else {
            // Logouted user
            Log.d(TAG, "Logouted user");
        }
    }

    // CHECK PERMISSION_CAMERA
    private void checkPermission() {
    }

    private void observeViewModel() {

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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}