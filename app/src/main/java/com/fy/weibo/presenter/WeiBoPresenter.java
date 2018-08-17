package com.fy.weibo.presenter;

import com.fy.weibo.Base.BasePresenter;
import com.fy.weibo.bean.WeiBo;
import com.fy.weibo.fragment.FirstPageFragment;
import com.fy.weibo.interfaces.IView;
import com.fy.weibo.model.ModelHandler;
import com.fy.weibo.sdk.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public class WeiBoPresenter extends BasePresenter<List<WeiBo>> {


    private IView<List<WeiBo>> iView;
    private ModelHandler modelHandler;


    public WeiBoPresenter(IView<List<WeiBo>> view) {
        super(view);
        modelHandler = ModelHandler.getInstance();
        this.iView = view;
    }


    @Override
    public void onSuccess(List<WeiBo> data) {
        iView.setData(data);
    }

    @Override
    public void onFailure(String e) {

    }

    @Override
    public void loadData(String baseUrl, Map<String, String> params, BasePresenter presenter) {

        modelHandler.getLastedPublicWeibo(baseUrl, params, this);
    }

}

/*
加载微博内容的presenter
 */
