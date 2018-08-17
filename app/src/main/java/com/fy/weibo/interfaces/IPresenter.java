package com.fy.weibo.interfaces;

/**
 * Created by Fan on 2018/8/12.
 * Fighting!!!
 */
public interface IPresenter<T> {

    void onSuccess(T data);
    void onFailure(String error);
}
