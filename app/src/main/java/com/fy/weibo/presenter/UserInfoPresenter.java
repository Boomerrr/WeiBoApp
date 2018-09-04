package com.fy.weibo.presenter;

import android.util.Log;

import com.fy.weibo.bean.UserInfo;
import com.fy.weibo.contract.UserInfoContract;
import com.fy.weibo.model.UserInfoModel;

import java.util.Map;

/**
 * Created by Fan on 2018/8/22.
 * Fighting!!!
 */
public final class UserInfoPresenter extends UserInfoContract.UserInfoContractPresenter{


    @Override
    public void loadUserInfo(String baseUrl, Map<String, String> params) {
        iModel.getUserInfo(baseUrl, params, this);
    }

    @Override
    public  void onSuccess(UserInfo data) {
        if (iView == null) {
            Log.e("TAG", "现在iView是null");
        }
        iView.setUserInfo(data);
    }


    @Override
    public void onFailure(String error) {
        Log.e("TAG", "错误信息:" + error);
        iView.showError(error);
    }

    @Override
    protected UserInfoContract.UserInfoContractModel getModel() {
        return new UserInfoModel();
    }
}
