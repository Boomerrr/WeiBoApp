package com.fy.weibo.base;


import com.fy.weibo.interfaces.IBaseView;


/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public  abstract class BaseMVPFragment<P extends BasePresenter> extends BaseFragment implements IBaseView<P> {


    protected P mPresenter;
    public abstract void initPresenter();



    @Override
    public void initData() {
        super.initData();
        initPresenter();
    }


    public void showLoading() {

    }
    public void hideLoading() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}
