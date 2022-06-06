package com.example.cloudtravel.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudtravel.R;
import com.example.cloudtravel.model.RecyclerViewItem;

import java.util.ArrayList;

public class MainTopCourse extends RecyclerView.Adapter<MainTopCourse.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_item;
        TextView txt_main;
        TextView txt_sub;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView_item = (ImageView) itemView.findViewById(R.id.imgView_item);
            txt_main = (TextView) itemView.findViewById(R.id.txt_main);
            txt_sub = (TextView) itemView.findViewById(R.id.txt_sub);
        }
    }

    private ArrayList<RecyclerViewItem> mList = null;

    public MainTopCourse(ArrayList<RecyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_recycler_item, parent, false);
        MainTopCourse.ViewHolder vh = new MainTopCourse.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull MainTopCourse.ViewHolder holder, int position) {
        RecyclerViewItem item = mList.get(position);

        holder.imgView_item.setImageResource(R.drawable.ic_baseline_cloud_24);
        holder.txt_main.setText(item.getMainText());
        holder.txt_sub.setText(item.getSubText());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}