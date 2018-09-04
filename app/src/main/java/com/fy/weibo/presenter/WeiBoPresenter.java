package com.fy.weibo.presenter;

import android.util.Log;

import com.fy.weibo.base.BasePresenter;
import com.fy.weibo.bean.WeiBo;
import com.fy.weibo.contract.WeiBoContract;
import com.fy.weibo.fragment.WeiBoFragment;
import com.fy.weibo.model.WeiBoModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public final class WeiBoPresenter extends WeiBoContract.WeiBoContractPresenter {


    @Override
    protected WeiBoModel getModel() {
        return new WeiBoModel();
    }

    @Override
    public void onFailure(String e) {
        Log.e("TAG", "错误信息 :" + e);
        iView.showError(e);
    }

    @Override
    public void loadWeiBo(String baseUrl, Map<String, String> params) {
        iModel.getWeiBoList(baseUrl, params, this);
    }

    @Override
    public void onSuccess(List<WeiBo> weiBoList) {
        iView.setWeiBoList(weiBoList);
    }
}

/*
加载微博内容的presenter
 */
