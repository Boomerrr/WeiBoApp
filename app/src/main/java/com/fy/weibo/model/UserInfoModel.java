package com.fy.weibo.model;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fy.weibo.App;
import com.fy.weibo.base.BasePresenter;
import com.fy.weibo.bean.UserInfo;
import com.fy.weibo.contract.UserInfoContract;
import com.fy.weibo.interfaces.IModel;
import com.fy.weibo.presenter.UserInfoPresenter;
import com.fy.weibo.util.HttpUtil;
import com.fy.weibo.util.JsonUtil;
import com.fy.weibo.util.NetStateUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public final class UserInfoModel implements UserInfoContract.UserInfoContractModel{



    public void getUserInfo(String baseUrl, Map<String, String> params, UserInfoContract.UserInfoContractPresenter presenter) {

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
                String  json;
                if (body != null) {
                    UserInfo userInfo = JsonUtil.getUserInfo(body.string());
                    if (userInfo != null) {

//                        Log.e("TAG", "cacheResponse-----" + response.cacheResponse());
//                        Log.e("TAG", "networkResponse-----" + response.networkResponse());

//                        使子线程等待一秒钟
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        presenter.onSuccess(userInfo);

                    } else presenter.onFailure("没有数据");
                }
            }
        });
    }


}
