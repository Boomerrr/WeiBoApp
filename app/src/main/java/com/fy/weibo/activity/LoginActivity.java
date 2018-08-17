package com.fy.weibo.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.fy.weibo.R;
import com.fy.weibo.fragment.LoginFragment;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public TextView textView;
    public Toolbar toolbar;
    public FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        toolbar = findViewById(R.id.login_tool_bar);
        textView = findViewById(R.id.tool_bar_text);

        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.login_frame, new LoginFragment());
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}

