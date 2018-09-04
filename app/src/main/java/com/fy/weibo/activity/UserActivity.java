package com.fy.weibo.activity;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fy.weibo.base.BaseMVPActivity;
import com.fy.weibo.R;
import com.fy.weibo.bean.UserCounts;
import com.fy.weibo.bean.UserInfo;
import com.fy.weibo.contract.UserCountContract;
import com.fy.weibo.presenter.UserCountPresenter;
import com.fy.weibo.sdk.Constants;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public final class UserActivity extends BaseMVPActivity<UserCountContract.UserCountContractPresenter> implements UserCountContract.UserCountView {


    private TextView friendCount;
    private TextView followerCount;
    private Toolbar toolbar;

    @Override
    public void loadData() {
        loadUserCount();
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_layout;
    }

    @Override
    public void initView() {
        initData();
        friendCount = findViewById(R.id.friends_count);
        followerCount = findViewById(R.id.followers_count);
    }

     void initData() {
        UserInfo userInfo = (UserInfo) getIntent().getSerializableExtra("user");
        String sex = "";
        switch (userInfo.getGender()) {
            case "m":
                sex = "男";
                break;
            case "f":
                sex = "女";
                break;
            default:
                sex = "未知";
                break;
        }
        TextView userLocation = findViewById(R.id.user_location);
        TextView userTime = findViewById(R.id.user_time);
        TextView userSex = findViewById(R.id.user_sex);
        CircleImageView userImg = findViewById(R.id.user_img);
        TextView userName = findViewById(R.id.user_name);
        toolbar = findViewById(R.id.user_tool_bar);
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(Color.WHITE))
                .centerCrop();
        Glide.with(this)
                .load(userInfo.getProfile_image_url())
                .apply(options)
                .into(userImg);
        userName.setText(userInfo.getScreen_name());
        userLocation.setText(userInfo.getLocation());
        userTime.setText(userInfo.getCreated_at());
        userSex.setText(sex);
        Glide.with(this)
                .load(userInfo.getCover_image_phone())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        toolbar.setBackground(resource);
                    }
                });
    }


    @Override
    public void initPresenter() {
        mPresenter = getPresenter();
        mPresenter.attachMV(this);
    }
    @Override
    public void loadUserCount() {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", Constants.ACCESS_TOKEN);
        params.put("uids", Constants.UID);
        Log.e("TAG", "uids------" + Constants.UID);
        mPresenter.loadUserCount(Constants.GET_USERS_COUNTS, params);
    }

    @Override
    public void setUserCount(UserCounts userCount) {
        runOnUiThread(() -> {
            friendCount.setText("关注: " + userCount.getFriends_count());
            followerCount.setText("粉丝:" + userCount.getFollowers_count());
        });

    }

    @Override
    public UserCountContract.UserCountContractPresenter getPresenter() {
        return new UserCountPresenter();
    }
}
