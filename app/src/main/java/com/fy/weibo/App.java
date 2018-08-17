package com.fy.weibo;

import android.app.Application;

import com.fy.weibo.sdk.Constants;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

/**
 * Created by Fan on 2018/8/15.
 * Fighting!!!
 */
public class App extends Application {

    private static App appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        WbSdk.install(this, new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));

    }
}

/*
初始化Application
 */
