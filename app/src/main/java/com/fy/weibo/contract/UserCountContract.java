package com.fy.weibo.contract;

import com.fy.weibo.base.BasePresenter;
import com.fy.weibo.bean.UserCounts;
import com.fy.weibo.interfaces.IBaseView;
import com.fy.weibo.interfaces.IModel;

import java.util.Map;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public interface UserCountContract {


    abstract class UserCountContractPresenter extends BasePresenter<UserCountContract.UserCountModel, UserCountContract.UserCountView>{

        public abstract void loadUserCount(String baseUrl, Map<String, String> params);
        public abstract void onSuccess(UserCounts userCounts);
    }


    interface UserCountView extends IBaseView<UserCountContract.UserCountContractPresenter>{
        void loadUserCount();
        void setUserCount(UserCounts userCount);
    }

    interface UserCountModel extends IModel{
        void getUserCount(String baseUrl, Map<String, String> params, UserCountContractPresenter presenter);
    }
}
