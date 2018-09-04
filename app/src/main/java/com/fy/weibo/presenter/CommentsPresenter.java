package com.fy.weibo.presenter;

import android.util.Log;
import android.widget.Toast;

import com.fy.weibo.base.BasePresenter;
import com.fy.weibo.bean.Comments;
import com.fy.weibo.contract.CommentContract;
import com.fy.weibo.fragment.CommentsFragment;
import com.fy.weibo.model.CommentModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Fan on 2018/8/11.
 * Fighting!!!
 */
public final class CommentsPresenter extends CommentContract.CommentContractPresenter {



    @Override
    public void onFailure(String error) {
        Log.e("TAG", "错误信息" + error);
        iView.showError(error);
    }


    @Override
    public void loadComments(String baseUrl, Map<String, String> params) {

        iModel.getComments(baseUrl, params, this);
    }

    @Override
    public void onSuccess(List<Comments> comments) {
        iView.setComments(comments);
    }

    @Override
    protected CommentContract.CommentContractModel getModel() {
        return new CommentModel();
    }
}
/*

加载评论的presenter
 */


