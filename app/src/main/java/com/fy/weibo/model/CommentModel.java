package com.fy.weibo.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fy.weibo.App;
import com.fy.weibo.bean.Comments;
import com.fy.weibo.contract.CommentContract;
import com.fy.weibo.interfaces.IModel;
import com.fy.weibo.presenter.CommentsPresenter;
import com.fy.weibo.util.HttpUtil;
import com.fy.weibo.util.JsonUtil;
import com.fy.weibo.util.NetStateUtil;

import java.io.IOException;
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
public final class CommentModel implements CommentContract.CommentContractModel {



    public void getComments(final String baseUrl, Map<String, String> params, final CommentContract.CommentContractPresenter presenter) {


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
                if ( body != null) {
                    List<Comments> commentsList = JsonUtil.getComments(body.string());
                    if (commentsList != null)
                        presenter.onSuccess(commentsList);
                    else presenter.onFailure("没有评论");
                }
            }
        });
    }

}
