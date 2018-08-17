package com.fy.weibo.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fy.weibo.R;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//   Java8 支持lambda表达式
        new Handler().postDelayed(this::LoginApp, 3000);
    }

    private void LoginApp() {

        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        StartActivity.this.finish();
    }
}
