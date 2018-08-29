package com.fy.weibo.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.fy.weibo.R;
import com.fy.weibo.fragment.FirstPageFragment;
import com.fy.weibo.sdk.Constants;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

public class Main2Activity extends AppCompatActivity implements WbShareCallback {

    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private FloatingActionButton floatButton;
    private WbShareHandler shareHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        drawerLayout = findViewById(R.id.drawer_layout);
        fragmentManager = getSupportFragmentManager();
        NavigationView navigationView = findViewById(R.id.design_nav_view);
        floatButton = findViewById(R.id.float_button);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setCheckedItem(R.id.me);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.me:
                    Toast.makeText(Main2Activity.this, "hello", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.first_page:
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, new FirstPageFragment());
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                default:
                    break;
            }
            return true;
        });

        floatButton.setOnClickListener(view -> {
            initWeibo();
            sendMultiMessage();
        });
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

    private void saveToken() {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("token", Constants.ACCESS_TOKEN);
        editor.apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveToken();
        Log.e("TAG", "onDestroy" + "  " + Constants.ACCESS_TOKEN);
    }


    /**
     * 微博分享
     *
     *
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent,this);
    }


    private void initWeibo() {
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
        shareHandler.setProgressColor(0xff33b5e5);
    }
    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMultiMessage() {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.imageObject = new ImageObject();
        weiboMessage.textObject = new TextObject();

        shareHandler.shareMessage(weiboMessage, false);
    }

    @Override
    public void onWbShareSuccess() {
        Toast.makeText(this,"分享成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWbShareFail() {
        Toast.makeText(this,"分享失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWbShareCancel() {
        Toast.makeText(this,"分享取消", Toast.LENGTH_LONG).show();
    }
}

/**
 微博展示界面
 */