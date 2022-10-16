package com.example.cloudtravel.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cloudtravel.R;
import com.example.cloudtravel.model.CourseModel;
import com.example.cloudtravel.view.Util;

import java.util.List;

public class MainBottomCourseAdapter extends RecyclerView.Adapter<MainBottomCourseAdapter.CourseViewHolder> {

    private final List<CourseModel> courses;

    public MainBottomCourseAdapter(List<CourseModel> courses) {
        this.courses = courses;
    }

    public void updateCourses(List<CourseModel> newCourses) {
        courses.addAll(newCourses);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainBottomCourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coruse, parent, false);
        return new MainBottomCourseAdapter.CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainBottomCourseAdapter.CourseViewHolder holder, int position) {
        final CourseModel model = courses.get(position);

        holder.image.setClipToOutline(true);

        RequestOptions options = new RequestOptions()
                .placeholder(Util.getProgressDrawable(holder.image.getContext()))
                .error(R.mipmap.ic_launcher_round);

        Glide.with(holder.image)
                .setDefaultRequestOptions(options)
                .load(model.getCourseViewUrl())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.imgView_item);
        }
    }


}