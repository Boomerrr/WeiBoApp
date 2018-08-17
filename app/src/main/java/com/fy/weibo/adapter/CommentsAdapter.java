package com.fy.weibo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fy.weibo.R;
import com.fy.weibo.bean.Comments;
import com.fy.weibo.util.TimeUtil;

import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fan on 2018/8/14.
 * Fighting!!!
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {


    private Context context;
    private List<Comments> commentsList;

    public CommentsAdapter(Context context, List<Comments> commentsList) {
        this.commentsList = commentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.comment_content_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        Comments comments = commentsList.get(position);
//        Log.e("TAG", comments.getUser().getScreen_name());
        holder.userName.setText(comments.getUser().getScreen_name());
        holder.commentTime.setText(TimeUtil.GMTtoNormal(comments.getCreated_at()));
        holder.commentText.setText(comments.getText());
        RequestOptions requestOptions = new RequestOptions().placeholder(new ColorDrawable(Color.WHITE));
        Glide.with(context)
                .load(comments.getUser().getProfile_image_url())
                .apply(requestOptions)
                .into(holder.userImg);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImg;
        TextView userName;
        TextView commentText;
        TextView commentTime;
       ViewHolder(View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.commenter_user_img);
            userName = itemView.findViewById(R.id.commenter_user_name);
            commentText = itemView.findViewById(R.id.comment_text);
            commentTime = itemView.findViewById(R.id.comment_time);
        }
    }
}
