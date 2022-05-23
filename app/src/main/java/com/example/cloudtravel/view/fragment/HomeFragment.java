package com.example.cloudtravel.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cloudtravel.databinding.FragmentHomeBinding;
import com.example.cloudtravel.viewModel.MainViewModel;

public class HomeFragment extends Fragment {

    private MainViewModel viewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainViewModel homeViewModel =
                new ViewModelProvider(this).get(MainViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding=FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    private void observeViewModel() {

        viewModel.banners.observe(getViewLifecycleOwner(), bannerModels -> {
            if (bannerModels != null) {
                Log.i("courses_observe", "OK");
                //binding.bannerList.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}