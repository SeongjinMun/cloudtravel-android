package com.example.cloudtravel.view.adapter;

import android.content.Context;
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
import com.example.cloudtravel.view.Util;

import java.util.ArrayList;

public class MainBannerAdapter extends RecyclerView.Adapter<MainBannerAdapter.MyViewHolder> {
    ArrayList<String> arrData;

    public MainBannerAdapter(ArrayList<String> arrData) {
        this.arrData = arrData;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCount;
        ImageView testImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCount = itemView.findViewById(R.id.txtCount);
            testImage = itemView.findViewById(R.id.itemImageView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_banner, parent, false);
        MyViewHolder viewHolder =  new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtCount.setText(arrData.get(position) + " page");
        holder.testImage.setClipToOutline(true);

        RequestOptions options = new RequestOptions()
                .placeholder(Util.getProgressDrawable(holder.testImage.getContext()));

        Glide.with(holder.testImage)
                .setDefaultRequestOptions(options)
                .load("https://a.cdn-hotels.com/gdcs/production146/d381/37197ff6-5992-4d4c-9cc1-ec6c0361452a.jpg")
                .into(holder.testImage);
    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }
}