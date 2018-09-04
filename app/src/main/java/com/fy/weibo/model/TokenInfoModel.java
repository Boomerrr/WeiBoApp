package com.fy.weibo.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fy.weibo.App;
import com.fy.weibo.bean.TokenInfo;
import com.fy.weibo.interfaces.LoadListener;
import com.fy.weibo.sdk.Constants;
import com.fy.weibo.util.HttpUtil;
import com.fy.weibo.util.JsonUtil;
import com.fy.weibo.util.NetStateUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */


public final class TokenInfoModel {

    public void get_token_info(LoadListener<TokenInfo> loadListener) {

        Map<String, String> params = new HashMap<>();
        params.put("access_token", Constants.ACCESS_TOKEN);
        Log.e("TAG", "---------------");
        Log.e("TAG", "我是token" + Constants.ACCESS_TOKEN + "你长啥样");
        HttpUtil.getHttpUtil().post(Constants.GET_TOKEN_INFO, params, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (!NetStateUtil.checkNet(App.getAppInstance().getApplicationContext()))
                    loadListener.onFailure("无网络");
                else loadListener.onFailure(e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();

                if (body != null) {
                    String json = body.string();
                    Log.e("TAG", "授权数据" + json);
                    TokenInfo tokenInfo = JsonUtil.get_token_info(json);
                    if (tokenInfo.getUid() == null) {
                        loadListener.onFailure("授权出错");
                    } else loadListener.onSuccess(tokenInfo);
                }
            }
        });
    }

}

/*
数据处理类   加载数据的方法都放在这里

 */

