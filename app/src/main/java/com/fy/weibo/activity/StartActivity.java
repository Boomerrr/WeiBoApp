package com.fy.weibo.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fy.weibo.R;
import com.fy.weibo.sdk.Constants;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//   Java8 支持lambda表达式
        new Handler().postDelayed(this::LoginApp, 3000);
    }

    private void LoginApp() {

        Log.e("TAG", AccessTokenKeeper.readAccessToken(this).getToken());

        Intent intent;
        String token = AccessTokenKeeper.readAccessToken(this).getToken();
        if (!token.equals("")) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
//            Constants.ACCESS_TOKEN = token;
        } else {
            intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        StartActivity.this.finish();
    }
}
