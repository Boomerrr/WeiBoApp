package com.fy.weibo.activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fy.weibo.APPManager;
import com.fy.weibo.base.BaseMVPActivity;
import com.fy.weibo.R;
import com.fy.weibo.bean.UserInfo;
import com.fy.weibo.contract.UserInfoContract;

import com.fy.weibo.fragment.MentionViewPagerFragment;
import com.fy.weibo.fragment.ShareWeiBoFragment;
import com.fy.weibo.fragment.CommentViewPagerFragment;
import com.fy.weibo.fragment.WeiBoViewPagerFragment;
import com.fy.weibo.presenter.UserInfoPresenter;
import com.fy.weibo.sdk.Constants;
import com.fy.weibo.util.NetStateUtil;

import java.util.HashMap;
import java.util.Map;


import de.hdodenhof.circleimageview.CircleImageView;


public final class MainActivity extends BaseMVPActivity<UserInfoContract.UserInfoContractPresenter> implements UserInfoContract.UserInfoView {

    private DrawerLayout drawerLayout;
    private CircleImageView userImg;
    private TextView userName;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    public FloatingActionButton floatButton;
    public Toolbar toolbar;


    @Override
    public int getLayoutId() {
        return R.layout.main_layout;
    }

    @Override
    public void initView() {

        APPManager.getInstance().finishBeforeActivity();
        if (!NetStateUtil.checkNet(this))
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        toolbar = findViewById(R.id.tool_bar);
        drawerLayout = findViewById(R.id.drawer_layout);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        NavigationView navigationView = findViewById(R.id.design_nav_view);
        floatButton = findViewById(R.id.float_button);
        setSupportActionBar(toolbar);
        userName = findViewById(R.id.nav_user_name);
        userImg = findViewById(R.id.nav_head_img);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navigationView.setCheckedItem(R.id.first_page);
        transaction.replace(R.id.main_frame, new WeiBoViewPagerFragment());
        transaction.commit();
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.first_page:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_frame, new WeiBoViewPagerFragment());
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.my_comment:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_frame, new CommentViewPagerFragment());
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.message:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_frame, new MentionViewPagerFragment());
                    transaction.commit();
                    drawerLayout.closeDrawers();
                default:
                    break;
            }
            return true;
        });

        floatButton.setOnClickListener(view -> {

        });

    }

    @Override
    public UserInfoPresenter getPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    public void initPresenter() {
        mPresenter = getPresenter();
        mPresenter.attachMV(this);
    }

    @Override
    public void loadData() {
        loadUserInfo();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }

        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
            this.finish();
        }
//        Log.e("TAG", "onDestroy" + "  " + Constants.ACCESS_TOKEN);
    }

    @Override
    public void loadUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", Constants.ACCESS_TOKEN);
        params.put("uid", Constants.UID);
//        Log.e("TAG", Constants.UID + "这是uid loadUserInfo");
        mPresenter.loadUserInfo(Constants.GET_USERS_SHOW, params);
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        this.runOnUiThread(() -> {

            userName = findViewById(R.id.nav_user_name);
            userImg = findViewById(R.id.nav_head_img);
//            Log.e("TAG", "这是在MainActivity()用户数据" + userInfo.getScreen_name());
            userName.setText(userInfo.getScreen_name());

            RelativeLayout relativeLayout = findViewById(R.id.nav_back_ground);
            RequestOptions options = new RequestOptions()
                    .placeholder(new ColorDrawable(Color.WHITE))
                    .centerCrop();
            Glide.with(this)
                    .load(userInfo.getProfile_image_url())
                    .apply(options)
                    .into(userImg);

            Glide.with(this)
                    .load(userInfo.getCover_image_phone())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            relativeLayout.setBackground(resource);
                        }
                    });

            userImg.setOnClickListener(v -> {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("user", userInfo);
                startActivity(intent);
            });
        });

    }

}

/*
微博展示界面
 */