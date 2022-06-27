package com.example.cloudtravel.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cloudtravel.R;
import com.example.cloudtravel.model.BannerModel;
import com.example.cloudtravel.model.CourseModel;
import com.example.cloudtravel.view.Util;

import java.util.ArrayList;
import java.util.List;

public class MainBannerAdapter extends RecyclerView.Adapter<MainBannerAdapter.BannerViewHolder> {

    private final List<BannerModel> banners;

    public MainBannerAdapter(List<BannerModel> banners) {
        this.banners = banners;
    }

    public void updateCourses(List<BannerModel> newBanners) {
        banners.addAll(newBanners);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        final BannerModel model = banners.get(position);
        holder.txtCount.setText(model.getCourseTitle());
        holder.testImage.setClipToOutline(true);
        RequestOptions options = new RequestOptions()
                .placeholder(Util.getProgressDrawable(holder.testImage.getContext()));

        Glide.with(holder.testImage)
                .setDefaultRequestOptions(options)
                .load(model.getBannerViewUrl())
                .into(holder.testImage);
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtCount;
        private final ImageView testImage;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCount = itemView.findViewById(R.id.txtCount);
            testImage = itemView.findViewById(R.id.itemImageView);
        }
    }
}