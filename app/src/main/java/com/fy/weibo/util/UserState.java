package com.fy.weibo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by Fan on 2018/8/29.
 * Fighting!!!
 */
public class UserState {

    private static SharedPreferences sharedPreferences;

    public static void saveUserMsg(Context context, String fileName) {

        SharedPreferences.Editor editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();

    }

    public static void getUserState(String token) {

    }

    public static void setUserStateNull(Context context){
        sharedPreferences = context.getSharedPreferences("com_weibo_sdk_android", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid", "");
        editor.putString("access_token", "");
        editor.putString("refresh_token", "");
        editor.putLong("expires_in", 0);
        editor.putString("name", "");
        editor.putString("password", "");
        editor.apply();
    }

    public static void saveCurrentUserInfo(Context context, String userInfo) {

        Oauth2AccessToken oauth2AccessToken = Oauth2AccessToken.parseAccessToken(userInfo);
        AccessTokenKeeper.writeAccessToken(context, oauth2AccessToken);
//        sharedPreferences = context.getSharedPreferences("com_weibo_sdk_android", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("name", );
//        editor.putString("password", );
    }

    public static void getCurrentUserInfo(Context context) {

    }
}
