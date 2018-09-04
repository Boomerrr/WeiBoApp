package com.fy.weibo.contract;

import com.fy.weibo.base.BasePresenter;
import com.fy.weibo.bean.UserInfo;
import com.fy.weibo.interfaces.IBaseView;
import com.fy.weibo.interfaces.IModel;
import java.util.Map;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public interface UserInfoContract {

     abstract class UserInfoContractPresenter extends BasePresenter<UserInfoContract.UserInfoContractModel, UserInfoContract.UserInfoView>{

        public abstract void loadUserInfo(String baseUrl, Map<String, String> params);
        public abstract void onSuccess(UserInfo userInfo);
    }

     interface UserInfoContractModel extends IModel{

        void getUserInfo(String baseUrl, Map<String, String> params, UserInfoContractPresenter presenter);
    }

    interface UserInfoView extends IBaseView<UserInfoContract.UserInfoContractPresenter>{

        void loadUserInfo();
        void setUserInfo(UserInfo userInfo);
    }
}
