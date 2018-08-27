package com.fy.weibo.sdk;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.fy.weibo.R;
import com.fy.weibo.activity.Main2Activity;
import com.fy.weibo.util.DataBaseUtil;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * Created by Fan on 2018/8/16.
 * Fighting!!!
 */
public class Oauth {

    private Activity activity;
    private DataBaseUtil dataBaseUtil;

    public Oauth(Activity activity) {
        this.activity = activity;
        dataBaseUtil = new DataBaseUtil(activity, "UserData.db", null, 1);
    }

    public void oauthWeiBo(final String account, final String password) {

        SsoHandler mSsoHandler = new SsoHandler(activity);
        mSsoHandler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                String token = oauth2AccessToken.getToken();
                Log.e(Constants.TAG, oauth2AccessToken.getToken());
                saveUserData(account, password, token);
                Constants.ACCESS_TOKEN = oauth2AccessToken.getToken();
                AccessTokenKeeper.writeAccessToken(activity, oauth2AccessToken);
                if (activity != null) {
                    activity.runOnUiThread(() -> {
                        Intent intent = new Intent(activity, Main2Activity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    });
                }

            }

            @Override
            public void cancel() {
                Toast.makeText(activity, "取消授权", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {

            }
        });
    }

    private void saveUserData(String account, String password, String token) {

        SQLiteDatabase sqLiteDatabase = dataBaseUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", account);
        contentValues.put("password", password);
        contentValues.put("token", token);
        sqLiteDatabase.insert("User", null, contentValues);
        contentValues.clear();
        Cursor cursor = sqLiteDatabase.query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex("account"));
                String a = cursor.getString(cursor.getColumnIndex("password"));
                String d = cursor.getString(cursor.getColumnIndex("token"));
                Log.e("TAG", s);
                Log.e("TAG", a);
                Log.e("TAG", d);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}

/*
授权类  并将信息储存到数据库中
 */