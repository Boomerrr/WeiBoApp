package com.fy.weibo.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fy.weibo.bean.Comments;
import com.fy.weibo.bean.WeiBo;
import com.fy.weibo.presenter.CommentsPresenter;
import com.fy.weibo.presenter.WeiBoPresenter;
import com.fy.weibo.util.HttpUtil;
import com.fy.weibo.util.JsonUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */


public class ModelHandler {



    private ModelHandler(){}

    public static synchronized ModelHandler getInstance() {
        return GetModelHandler.modelHandler;
    }

    private static class GetModelHandler{
        private static final ModelHandler modelHandler = new ModelHandler();
    }


    private  void getWeiBo (String baseUrl, Map<String , String> params, final WeiBoPresenter presenter) {

        HttpUtil.getData(baseUrl, params, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("TAG", e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (response.isSuccessful() && body != null) {
                    List<WeiBo> publicWeiBo = JsonUtil.getLastedPublicWeiBo(body.string());
                    if (publicWeiBo != null)
                        presenter.onSuccess(publicWeiBo);
                    if (publicWeiBo == null){
                        presenter.onFailure("没有数据");
                    }

                }
            }
        });

    }


    public  void getLastedPublicWeibo(String BaseUrl, Map<String, String> params, WeiBoPresenter presenter) {

        getWeiBo(BaseUrl, params, presenter);
    }

    public void getComments(final String baseUrl, Map<String, String> params, final CommentsPresenter presenter) {


        HttpUtil.getData(baseUrl, params, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                presenter.onFailure(e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    List<Comments> commentsList = JsonUtil.getComments(body.string());
                    if (commentsList != null) {
                        presenter.onSuccess(commentsList);
                    } else {
                        Log.e("TAG", "没有数据");
                        presenter.onFailure("没有数据");
                    }

                }
            }
        });
    }
}

/*
数据处理类   加载数据的方法都放在这里
 */
