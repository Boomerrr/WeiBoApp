package com.fy.weibo.base;

import com.fy.weibo.interfaces.IModel;
import com.fy.weibo.interfaces.IPresenter;
import com.fy.weibo.interfaces.IBaseView;

import java.util.Map;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public abstract class BasePresenter<M extends IModel, V extends IBaseView> implements IPresenter<V>{

    protected V iView;
    protected M iModel;

    protected abstract M getModel();

    @Override
    public void attachMV(V view) {

        this.iView =  view;
        iModel = getModel();
    }

    @Override
    public void detach() {
        if (iModel != null && iView != null) {
            iModel = null;
            iView = null;
        }
    }

    public boolean isViewAttached() {

        return iView != null;
    }


}
