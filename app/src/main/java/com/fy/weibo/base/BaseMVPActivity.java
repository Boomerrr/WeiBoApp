package com.fy.weibo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fy.weibo.APPManager;
import com.fy.weibo.R;
import com.fy.weibo.interfaces.IBaseView;

/**
 * Created by Fan on 2018/8/12.
 * Fighting!!!
 */
public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements IBaseView<P> {


    public P mPresenter;

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initPresenter();


    public void loadData() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPManager.getInstance().addActivity(this);
        setContentView(getLayoutId());
        initView();
        initPresenter();
        loadData();
    }

    @Override
    public void showError(final String e) {
        runOnUiThread(() ->
                Toast.makeText(BaseMVPActivity.this,  e, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}


