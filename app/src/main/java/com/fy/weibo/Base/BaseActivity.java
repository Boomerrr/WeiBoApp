package com.fy.weibo.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fy.weibo.R;
import com.fy.weibo.interfaces.IView;

import java.util.Map;

/**
 * Created by Fan on 2018/8/12.
 * Fighting!!!
 */
public abstract class BaseActivity<T> extends AppCompatActivity implements IView<T>{


    protected BasePresenter presenter;
    public abstract void loadData();
    public abstract int getLayoutId();
    public abstract void initView();
    public abstract void initPresenter();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initPresenter();
        loadData();
    }

    @Override
    public void showError(final String e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
}



