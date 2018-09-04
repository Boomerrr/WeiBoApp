package com.fy.weibo.sdk;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.fy.weibo.APPManager;
import com.fy.weibo.App;
import com.fy.weibo.R;
import com.fy.weibo.activity.LoginActivity;
import com.fy.weibo.activity.MainActivity;
import com.fy.weibo.bean.TokenInfo;
import com.fy.weibo.interfaces.LoadListener;
import com.fy.weibo.model.TokenInfoModel;
import com.fy.weibo.util.DataBase;
import com.fy.weibo.util.DataBaseHelper;
import com.fy.weibo.util.UserState;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * Created by Fan on 2018/8/16.
 * Fighting!!!
 */
public final class Oauth {

    private Activity activity;
    private DataBase dataBase;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken accessToken;
    public Oauth(Activity activity) {
        this.activity = activity;
        mSsoHandler = new SsoHandler(activity);
    }


    public void oauthWeiBo(final String account, final String password) {

        dataBase = new DataBase(App.getAppInstance().getApplicationContext(), "UserData.db", null, 1);
        mSsoHandler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {

                accessToken = oauth2AccessToken;
                String token = oauth2AccessToken.getToken();
                Log.e(Constants.TAG, oauth2AccessToken.getToken());
//                saveUserData(account, password, token);

                DataBaseHelper.getDataBaseHelper().saveUserToken(account, password, token);
                Constants.ACCESS_TOKEN = oauth2AccessToken.getToken();
                Constants.UID = oauth2AccessToken.getUid();
                AccessTokenKeeper.writeAccessToken(activity, oauth2AccessToken);

                if (activity != null) {
                    activity.runOnUiThread(() -> {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        activity.finish();
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

    public void oauthWeiBo(){

        mSsoHandler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {

                Log.e("TAG", oauth2AccessToken.getToken() + "授权成功");
                AccessTokenKeeper.writeAccessToken(activity, oauth2AccessToken);
                Constants.UID = oauth2AccessToken.getUid();

                if (!Constants.USER_ACCOUNT.equals("") && !Constants.USER_PASSWORD.equals("")) {
                    Log.e("TAG", "我在更新token");
                    DataBaseHelper.getDataBaseHelper().updateToken(Constants.USER_ACCOUNT, Constants.USER_PASSWORD, oauth2AccessToken.getToken());
                    DataBaseHelper.getDataBaseHelper().checkAccount();
                }
                Constants.ACCESS_TOKEN = oauth2AccessToken.getToken();
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                APPManager.getInstance().finishBeforeActivity();
                Log.e("TAG", "结束之前的Activity");
            }

            @Override
            public void cancel() {
                Log.e("TAG", "取消授权");
                Toast.makeText(activity, "取消授权", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                Log.e("TAG", "授权失败");
                Toast.makeText(activity, "授权失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void isOauth() {

        new TokenInfoModel().get_token_info(new LoadListener<TokenInfo>() {
            @Override
            public void onSuccess(TokenInfo tokenInfo) {

                Log.e("TAG", "你应该是子线程" + Thread.currentThread().getName());
                if (tokenInfo == null || (tokenInfo.getExpire_in().equals("")||Integer.valueOf(tokenInfo.getExpire_in()) <= 0)) {
                    activity.runOnUiThread(() -> {
                        Toast.makeText(activity, "微博授权过期请重新授权", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    activity.runOnUiThread(() ->{
                        String userInfo = "{\"uid\":" + tokenInfo.getUid() + ",\"access_token\":" + Constants.ACCESS_TOKEN + "}";
                        UserState.saveCurrentUserInfo(activity, userInfo);
                        Constants.UID = AccessTokenKeeper.readAccessToken(activity).getUid();
                        Log.e("TAG", "StartActivity----uid----" + Constants.UID);
                        Log.e("TAG", "线程" + Thread.currentThread().getName());
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        activity.finish();
                    });

                }
            }

            @Override
            public void onFailure(String e) {
                Log.e("TAG", e);
                Looper.prepare();
                Toast.makeText(activity, "授权失败请检查后重试", Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
                Looper.loop();
            }
        });

    }

}

/*
授权类  并将信息储存到数据库中
 */