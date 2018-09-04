package com.fy.weibo.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;


import com.fy.weibo.APPManager;
import com.fy.weibo.R;
import com.fy.weibo.base.BaseActivity;
import com.fy.weibo.fragment.LoginFragment;
import com.fy.weibo.util.NetStateUtil;

public final class LoginActivity extends BaseActivity {

    public TextView textView;
    public Toolbar toolbar;
    public FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!NetStateUtil.checkNet(this))
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();

        APPManager.getInstance().addActivity(this);
        // Set up the login form.
        toolbar = findViewById(R.id.login_tool_bar);
        textView = findViewById(R.id.tool_bar_text);

        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.login_frame, new LoginFragment());
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
//        transaction.addToBackStack(null);
        transaction.commit();
    }


}

