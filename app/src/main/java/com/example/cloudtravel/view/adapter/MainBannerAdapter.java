package com.example.cloudtravel.view.adapter;

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
import com.example.cloudtravel.view.Util;

import java.util.List;

public class MainBannerAdapter extends RecyclerView.Adapter<MainBannerAdapter.BannerViewHolder> {

    private final List<BannerModel> banners;

    public MainBannerAdapter(List<BannerModel> banners) {
        this.banners = banners;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        final BannerModel model = banners.get(position);
        holder.title.setText(model.getCourseTitle());
        holder.image.setClipToOutline(true);

        RequestOptions options = new RequestOptions()
                .placeholder(Util.getProgressDrawable(holder.image.getContext()))
                .error(R.mipmap.ic_launcher_round);

        Glide.with(holder.image)
                .setDefaultRequestOptions(options)
                .load(model.getBannerViewUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }


    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView image;

        public BannerViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bannerTitle);
            image = itemView.findViewById(R.id.bannerImage);
        }
    }
}
