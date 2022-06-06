package com.example.cloudtravel.view.fragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.cloudtravel.model.RecyclerViewItem;
import com.example.cloudtravel.view.adapter.MainBannerAdapter;
import com.example.cloudtravel.view.adapter.MainMiddleCourse;
import com.example.cloudtravel.view.adapter.MainTopCourse;
import com.example.cloudtravel.viewModel.MainViewModel;

public class HomeFragment extends Fragment {
    private ViewPager2 viewPager2;
    private MainViewModel viewModel;
    private FragmentHomeBinding binding;
    private ArrayList<RecyclerViewItem> mList;
    private RecyclerView MainTopRecyclerView;
    private MainTopCourse MainTopRecyclerViewAdapter;
    private RecyclerView MainMiddleRecyclerView;
    private MainMiddleCourse MainMiddleRecyclerViewAdapter;
    private RecyclerView MainBottomRecyclerView;
    private MainMiddleCourse MainBottomRecyclerViewAdapter;

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

        MainTopCourse();
        MainMiddleCourse();
        MainBottomCourse();

        for(int i=0;i<5;i++){
            addItem("iconName", "Taek" + i, "taek2.tistory.com");
        }

        MainTopRecyclerViewAdapter = new MainTopCourse(mList);
        MainTopRecyclerView.setAdapter(MainTopRecyclerViewAdapter);
        MainTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MainTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        MainMiddleRecyclerViewAdapter = new MainMiddleCourse(mList);
        MainMiddleRecyclerView.setAdapter(MainMiddleRecyclerViewAdapter);
        MainMiddleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MainMiddleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        MainBottomRecyclerViewAdapter = new MainMiddleCourse(mList);
        MainBottomRecyclerView.setAdapter(MainBottomRecyclerViewAdapter);
        MainBottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MainBottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

    }

    private void setContentView(int fragment_home) {
    }

    public void MainTopCourse(){
        MainTopRecyclerView = (RecyclerView) getView().findViewById(R.id.MainTopCourse);
        mList = new ArrayList<>();
    }

    public void MainMiddleCourse(){
        MainMiddleRecyclerView = (RecyclerView) getView().findViewById(R.id.MainMiddleCourse);
        mList = new ArrayList<>();
    }

    public void MainBottomCourse(){
        MainBottomRecyclerView = (RecyclerView) getView().findViewById(R.id.MainBottomCourse);
        mList = new ArrayList<>();
    }

    public void addItem(String imgName, String mainText, String subText){
        RecyclerViewItem item = new RecyclerViewItem();

        item.setImgName(imgName);
        item.setMainText(mainText);
        item.setSubText(subText);

        mList.add(item);
    }

}