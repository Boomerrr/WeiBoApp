package com.fy.weibo.interfaces;



/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public interface IBaseView<P extends IPresenter> {

    void showError(String e);
    P getPresenter();
}
