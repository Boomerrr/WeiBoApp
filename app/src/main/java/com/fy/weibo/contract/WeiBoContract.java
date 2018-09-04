package com.fy.weibo.contract;

import com.fy.weibo.base.BasePresenter;
import com.fy.weibo.bean.WeiBo;
import com.fy.weibo.interfaces.IBaseView;
import com.fy.weibo.interfaces.IModel;
import com.fy.weibo.presenter.WeiBoPresenter;

import java.util.List;
import java.util.Map;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public interface WeiBoContract {

    abstract class WeiBoContractPresenter extends BasePresenter<WeiBoContract.WeiBoModel, WeiBoContract.WeiBoView>{

        public abstract void  loadWeiBo(String baseUrl, Map<String, String> params);
        public abstract void onSuccess(List<WeiBo> weiBoList);
    }


    interface WeiBoView extends IBaseView<WeiBoContractPresenter> {

        void setWeiBoList(List<WeiBo> weiBoList);
        void loadWeiBo();
    }

    interface WeiBoModel extends IModel{

        void getWeiBoList(String baseUrl, Map<String, String> params, WeiBoContractPresenter presenter);
    }

}
