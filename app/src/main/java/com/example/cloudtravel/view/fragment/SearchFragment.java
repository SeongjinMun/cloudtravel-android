package com.example.cloudtravel.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudtravel.R;
import com.example.cloudtravel.databinding.FragmentSearchBinding;
import com.example.cloudtravel.view.AuthMainActivity;
import com.example.cloudtravel.view.FilterActivity;
import com.example.cloudtravel.view.adapter.CourseListAdapter;
import com.example.cloudtravel.viewModel.CourseViewModel;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private CourseViewModel viewModel;


    private CourseListAdapter adapter = new CourseListAdapter(new ArrayList<>());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);

        binding=FragmentSearchBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode== Activity.RESULT_OK){
                if (data!=null){
                    viewModel.searchTitle.setValue(data.getStringExtra("search"));
                    viewModel.courseTags.setValue(data.getStringExtra("tags"));

                    adapter.clear();
                    viewModel.reset();
                    viewModel.load();
                }
            }
        } else {
            Toast.makeText(getActivity(), "취소되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.setHasStableIds(true);
        viewModel.load();


        binding.courseList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.courseList.setAdapter(adapter);

        binding.filterButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), FilterActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 1);
        });

        binding.courseList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount()-1;

                if (lastVisibleItemPosition == itemTotalCount) {
                    if (viewModel.courses.getValue().size() == 8){
                        viewModel.load();
                    }
                }

                if (recyclerView.computeVerticalScrollOffset() == 0){
                    binding.swipeRefreshLayout.setEnabled(true);
                    binding.swipeRefreshLayout.setRefreshing(false);
                }else {
                    binding.swipeRefreshLayout.setEnabled(false);
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(v->{
            adapter.clear();
            viewModel.reset();
            viewModel.load();
            binding.swipeRefreshLayout.setEnabled(false);
            binding.swipeRefreshLayout.setRefreshing(false);
        });
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.courses.observe(getViewLifecycleOwner(), courseModels -> {
            if (courseModels != null) {
                binding.courseList.setVisibility(View.VISIBLE);
                adapter.updateCourses(courseModels);
            }
        });
        viewModel.courseLoadError.observe(getViewLifecycleOwner(), isError -> {
            if (isError != null) {
                binding.listError.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });
        viewModel.loading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                //Glide.with(this).load(R.drawable.loading).into(binding.imageView3);
                //binding.imageView3.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    binding.listError.setVisibility(View.GONE);
                    binding.courseList.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}