package com.example.cloudtravel.view.fragment;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cloudtravel.R;
import com.example.cloudtravel.databinding.FragmentHomeBinding;
import com.example.cloudtravel.view.adapter.MainBannerAdapter;
import com.example.cloudtravel.viewModel.MainViewModel;

public class HomeFragment extends Fragment {
    private ViewPager2 viewPager2;
    private MainViewModel viewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainViewModel homeViewModel =
                new ViewModelProvider(this).get(MainViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater);
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

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setContentView(R.layout.fragment_home);

        ArrayList<String> arrData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            arrData.add(Integer.toString(i));
        }

        MainBannerAdapter adapter = new MainBannerAdapter(arrData);

        viewPager2 = getView().findViewById(R.id.bannerList);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter);
    }

    private void setContentView(int fragment_home) {
    }

}