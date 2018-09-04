package com.fy.weibo.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fy.weibo.APPManager;
import com.fy.weibo.App;
import com.fy.weibo.bean.WeiBo;
import com.fy.weibo.contract.WeiBoContract;
import com.fy.weibo.util.HttpUtil;
import com.fy.weibo.util.JsonUtil;
import com.fy.weibo.util.NetStateUtil;

import java.io.IOException;
import java.net.CacheResponse;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public final class WeiBoModel implements WeiBoContract.WeiBoModel {

    public static WeiBoModel getInstance() {

        return new WeiBoModel();
    }


    @Override
    public void getWeiBoList(String baseUrl, Map<String, String> params, WeiBoContract.WeiBoContractPresenter presenter) {

        HttpUtil.getHttpUtil().getData(baseUrl, params, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (!NetStateUtil.checkNet(App.getAppInstance().getApplicationContext()))
                    presenter.onFailure("无网络");
                else presenter.onFailure(e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    Log.e("TAG", "cacheResponse-----" + response.cacheResponse());
                    Log.e("TAG", "networkResponse-----" + response.networkResponse());

                    List<WeiBo> weiBoList = JsonUtil.getWeiBo(body.string());
                    if (weiBoList != null) {
                        presenter.onSuccess(weiBoList);
                    } else presenter.onFailure("没有数据");

                }
            }
        });

    }
}
