package com.fy.weibo.contract;

import com.fy.weibo.base.BaseMVPFragment;
import com.fy.weibo.base.BasePresenter;
import com.fy.weibo.bean.Comments;
import com.fy.weibo.interfaces.IBaseView;
import com.fy.weibo.interfaces.IModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public interface CommentContract {

     abstract class CommentContractPresenter extends BasePresenter<CommentContract.CommentContractModel, CommentContract.CommentView>{

        public abstract void loadComments(String baseUrl, Map<String, String> params);
        public abstract void onSuccess(List<Comments> comments);
    }

    interface CommentContractModel extends IModel{

        void getComments(String baseUrl, Map<String, String> params, CommentContract.CommentContractPresenter presenter);
    }

    interface CommentView extends IBaseView<CommentContractPresenter> {

        void loadComments();
        void setComments(List<Comments> commentsList);
    }
}
