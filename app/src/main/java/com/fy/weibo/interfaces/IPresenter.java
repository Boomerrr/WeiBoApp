package com.fy.weibo.interfaces;

import java.io.Serializable;

/**
 * Created by Fan on 2018/8/12.
 * Fighting!!!
 */
public interface IPresenter<V extends IBaseView> {


    void attachMV(V view);
    void detach();
    void onFailure(String e);
}
