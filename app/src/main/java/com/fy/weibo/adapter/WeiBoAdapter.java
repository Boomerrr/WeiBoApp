package com.fy.weibo.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fy.weibo.R;
import com.fy.weibo.activity.ContentActivity;
import com.fy.weibo.bean.PicUrlsBean;
import com.fy.weibo.bean.WeiBo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public final class WeiBoAdapter extends RecyclerView.Adapter<WeiBoAdapter.MyViewHolder> {


    private List<WeiBo> lastedWeiBoList;
    private Context context;
    private LayoutInflater layoutInflater;

    public WeiBoAdapter(FragmentActivity context, List<WeiBo> lastedWeiBoList) {

        this.context = context;
        this.lastedWeiBoList = lastedWeiBoList;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.wei_bo_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final WeiBo lastedWeiBo = this.lastedWeiBoList.get(position);
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(Color.WHITE))
                .centerCrop();
        Glide.with(context)
                .load(lastedWeiBo.getUser().getProfile_image_url())
                .apply(options)
                .into(holder.userImage);

        holder.userName.setText(lastedWeiBo.getUser().getScreen_name());
        holder.weiBoSource.setText(lastedWeiBo.getSource());
        holder.weiBoTime.setText(lastedWeiBo.getCreated_at());
        holder.weiBoText.setText(lastedWeiBo.getText());
        holder.thumbUpCounts.setText(lastedWeiBo.getAttitudes_count());
        holder.commentCounts.setText(lastedWeiBo.getComments_count());
        holder.shareCounts.setText(lastedWeiBo.getReposts_count());
        if (lastedWeiBo.getPic_urls() != null) {
            List<String> imgUrlList = new ArrayList<>();
            for (PicUrlsBean picUrl : lastedWeiBo.getPic_urls()) {
                imgUrlList.add(picUrl.getThumbnail_pic());
            }
            imgUrlList = getBmiddleImgUrl(imgUrlList);
            holder.imgRecyclerView.setNestedScrollingEnabled(false);
            GridLayoutManager manager = new GridLayoutManager(context, 3);
            holder.imgRecyclerView.setLayoutManager(manager);
            WeiBoImgAdapter imgAdapter = new WeiBoImgAdapter(context, imgUrlList);
            holder.imgRecyclerView.setAdapter(imgAdapter);
        }
        holder.mainContent.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentActivity.class);
            intent.putExtra("weibo", lastedWeiBo);
            context.startActivity(intent);
            ((FragmentActivity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        });

    }

    @Override
    public int getItemCount() {
        return lastedWeiBoList.size();
    }



      class MyViewHolder extends RecyclerView.ViewHolder{

        TextView weiBoText;
        CircleImageView userImage;
        TextView weiBoTime;
        TextView weiBoSource;
        TextView userName;
        TextView commentCounts;
        TextView shareCounts;
        TextView thumbUpCounts;
        RelativeLayout mainContent;
        RecyclerView imgRecyclerView;

        MyViewHolder(View itemView) {
            super(itemView);
            weiBoSource = itemView.findViewById(R.id.wei_bo_source);
            weiBoText = itemView.findViewById(R.id.wei_bo_content_text);
            weiBoTime = itemView.findViewById(R.id.send_time);
            userName = itemView.findViewById(R.id.user_name);
            userImage = itemView.findViewById(R.id.user_img);
            commentCounts = itemView.findViewById(R.id.comment_counts);
            shareCounts = itemView.findViewById(R.id.share_counts);
            thumbUpCounts = itemView.findViewById(R.id.thumb_up_counts);
            mainContent = itemView.findViewById(R.id.main_content);
            imgRecyclerView = itemView.findViewById(R.id.wei_bo_img_recycler);

        }


    }

    private List<String> getBmiddleImgUrl(List<String> urlList) {


        List<String> bmiddleImgUrlList = new ArrayList<>();
        for (String url: urlList) {
            bmiddleImgUrlList.add(url.replaceFirst("thumbnail", "bmiddle"));
        }
        return bmiddleImgUrlList;
    }
}
