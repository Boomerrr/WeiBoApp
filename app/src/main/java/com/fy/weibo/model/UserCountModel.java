package com.fy.weibo.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fy.weibo.App;
import com.fy.weibo.bean.UserCounts;
import com.fy.weibo.contract.UserCountContract;
import com.fy.weibo.interfaces.IModel;
import com.fy.weibo.presenter.UserCountPresenter;
import com.fy.weibo.sdk.Constants;
import com.fy.weibo.util.HttpUtil;
import com.fy.weibo.util.NetStateUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public final class UserCountModel implements UserCountContract.UserCountModel{


    @Override
    public void getUserCount(String baseUrl, Map<String, String> params, UserCountContract.UserCountContractPresenter presenter) {

        HttpUtil.getHttpUtil().getData( baseUrl, params, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (!NetStateUtil.checkNet(App.getAppInstance().getApplicationContext()))
                    presenter.onFailure("无网络");
                else presenter.onFailure(e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                String json;
                if (body != null) {
                    json = body.string();
                    if (json != null) {
                        String regEx = "\\[(.*)]";
//                        Log.e("TAG", json + "这是用户数据");
                        Pattern pattern = Pattern.compile(regEx);
                        Matcher matcher = pattern.matcher(json);
                        json = matcher.find() ? matcher.group(1) : "";
//                        Log.e("TAG", json + "这是用户数据");
                        Gson gson = new Gson();
                        UserCounts userCounts = gson.fromJson(json, UserCounts.class);
                        if (userCounts != null)
                            presenter.onSuccess(userCounts);
                        else presenter.onFailure("没有数据");
                    } else presenter.onFailure("没有数据");

                }
            }
        });

    }
}
