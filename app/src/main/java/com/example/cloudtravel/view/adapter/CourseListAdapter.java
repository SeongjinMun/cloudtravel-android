package com.example.cloudtravel.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cloudtravel.R;
import com.example.cloudtravel.model.CourseModel;
import com.example.cloudtravel.view.Util;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private final List<CourseModel> courses;

    public CourseListAdapter(List<CourseModel> courses) {
        this.courses = courses;
    }

    public void updateCourses(List<CourseModel> newCourses) {
        courses.addAll(newCourses);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        final CourseModel model = courses.get(position);
        holder.title.setText(model.getCourseTitle());
        holder.subTitle.setText(model.getCourseSubTitle());
        holder.tags.setText(model.getCourseTags());
        holder.views.setText(model.getCourseViews());
        holder.rating.setRating(model.getCourseRating());
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

    public static class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView subTitle;
        private final TextView tags;
        private final TextView views;
        private final RatingBar rating;
        private final ImageView image;

            public CourseViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.courseTitle);
            subTitle = itemView.findViewById(R.id.courseSubTitle);
            tags = itemView.findViewById(R.id.courseTags);
            views = itemView.findViewById(R.id.courseViews);
            rating = itemView.findViewById(R.id.courseRatingBar);
            image = itemView.findViewById(R.id.courseImage);
        }
    }
}
