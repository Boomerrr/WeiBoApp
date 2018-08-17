package com.fy.weibo.presenter;

import android.util.Log;

import com.fy.weibo.Base.BasePresenter;
import com.fy.weibo.activity.ContentActivity;
import com.fy.weibo.bean.Comments;
import com.fy.weibo.interfaces.IView;
import com.fy.weibo.model.ModelHandler;
import com.fy.weibo.util.HttpUtil;

import java.util.List;
import java.util.Map;

import okhttp3.Callback;

/**
 * Created by Fan on 2018/8/11.
 * Fighting!!!
 */
public class CommentsPresenter extends BasePresenter<List<Comments>> {


    private IView<List<Comments>> iView;
    public CommentsPresenter(IView<List<Comments>> view) {
        super(view);
        this.iView = view;
    }


    @Override
    public void onSuccess(List<Comments> data) {

        iView.setData(data);
    }

    @Override
    public void onFailure(String e) {
        Log.e("TAG", e);
        iView.showError(e);
    }

    @Override
    public void loadData(String baseUrl, Map<String, String> params, BasePresenter presenter) {

        ModelHandler.getInstance().getComments(baseUrl, params, this);
    }
}
/*

加载评论的presenter
 */


