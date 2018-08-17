package com.fy.weibo.Base;

import com.fy.weibo.interfaces.IPresenter;
import com.fy.weibo.interfaces.IView;

import java.util.Map;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public abstract class BasePresenter<T> implements IPresenter<T>{

    private IView myView;


    public abstract void onSuccess(T data);
    public abstract void onFailure(String e);
    public BasePresenter(IView view){
        this.myView = view;
    }

    public void attachView(IView view) {

        this.myView = view;
    }

    public void detachView() {
        this.myView = null;
    }

    public boolean isViewAttached() {

        return myView != null;
    }

    public IView getMyView() {

        return myView;
    }

    public void loadData(String baseUrl, Map<String, String> params, BasePresenter presenter){

    }



}
