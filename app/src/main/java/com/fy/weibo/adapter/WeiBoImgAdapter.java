package com.fy.weibo.adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fy.weibo.R;

import java.util.List;


/**
 * Created by Fan on 2018/8/18.
 * Fighting!!!
 */
public final class WeiBoImgAdapter extends RecyclerView.Adapter<WeiBoImgAdapter.ViewHolder> {

    private Context context;
    private List<String> imgUrlList;

    public WeiBoImgAdapter(Context context, List<String> imgUrlList) {
        this.context = context;
        this.imgUrlList = imgUrlList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.weibo_img_item_layout,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String imgUrl = imgUrlList.get(position);
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(Color.WHITE))
                .centerCrop();
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imgUrlList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.weibo_img_item);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            Display display = ((Activity)context).getWindow().getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            int mWidth = displayMetrics.widthPixels / 3;
            layoutParams.width = mWidth;
            layoutParams.height = mWidth;
        }
    }
}
