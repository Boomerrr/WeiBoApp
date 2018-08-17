package com.fy.weibo.interfaces;



/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public interface IView<T> {

    void showError(String e);
    void setData(T data);
}
