package com.fy.weibo.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fy.weibo.R;
import com.fy.weibo.activity.MainActivity;
import com.fy.weibo.adapter.WeiBoAdapter;
import com.fy.weibo.base.BaseMVPFragment;
import com.fy.weibo.bean.WeiBo;
import com.fy.weibo.contract.WeiBoContract;
import com.fy.weibo.listener.MyScrollListener;
import com.fy.weibo.presenter.WeiBoPresenter;
import com.fy.weibo.sdk.Constants;
import com.fy.weibo.util.NetStateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public final class WeiBoFragment extends BaseMVPFragment<WeiBoContract.WeiBoContractPresenter> implements WeiBoContract.WeiBoView {

    private RecyclerView itemRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private WeiBoAdapter weiBoAdapter;
    private SwipeRefreshLayout refreshLayout;
    private List<WeiBo> publicWeiBoList = null;
    private String url = "";

    public static WeiBoFragment getInstance(String from) {

        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        WeiBoFragment weiBoFragment = new WeiBoFragment();
        weiBoFragment.setArguments(bundle);
        return weiBoFragment;
    }


    @Override
    public int getContentViewId() {
        return R.layout.recycler_layout;
    }


    @Override
    public void initAllMembersView(Bundle saveInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("from");
        }
        ((MainActivity) mActivity).floatButton.animate().translationY(0).setDuration(100);
        itemRecyclerView = mRootView.findViewById(R.id.rec_view);
        refreshLayout = mRootView.findViewById(R.id.refresh);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        // 设置item间距
        DividerItemDecoration decoration = new DividerItemDecoration(getAttachActivity(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.item_decoration)));
        itemRecyclerView.addItemDecoration(decoration);
        itemRecyclerView.setLayoutManager(linearLayoutManager);
        setScrollListener();
        refreshLayout = mRootView.findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(R.color.orange, R.color.orangered);
        refreshLayout.setOnRefreshListener(() -> {
            loadWeiBo();
            if (!NetStateUtil.checkNet(mActivity)) {
                Toast.makeText(mActivity, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setWeiBoList(List<WeiBo> weiBoList) {
        publicWeiBoList = weiBoList;
        getAttachActivity().runOnUiThread(() -> {
            weiBoAdapter = new WeiBoAdapter(getAttachActivity(), publicWeiBoList);
            itemRecyclerView.setAdapter(weiBoAdapter);
            refreshLayout.setRefreshing(false);
        });

    }

    @Override
    public void loadWeiBo() {

        refreshLayout.setRefreshing(true);
        if (!NetStateUtil.checkNet(mActivity))
            refreshLayout.setRefreshing(false);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", Constants.ACCESS_TOKEN);
        mPresenter.loadWeiBo(url, params);

    }

    @Override
    public void showError(String e) {
        if (isAttachContext()) {
            mActivity.runOnUiThread(() -> {
                refreshLayout.setRefreshing(false);
//                Toast.makeText(mActivity, e, Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public WeiBoContract.WeiBoContractPresenter getPresenter() {
        return new WeiBoPresenter();
    }

    @Override
    public void initPresenter() {
        mPresenter = getPresenter();
        mPresenter.attachMV(this);
    }

    @Override
    public void loadData() {
        loadWeiBo();
    }

    private void setScrollListener() {

        FloatingActionButton floatingActionButton = ((MainActivity) mActivity).floatButton;
        itemRecyclerView.addOnScrollListener(new MyScrollListener(new MyScrollListener.HideListener() {
            @Override
            public void hide() {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) floatingActionButton.getLayoutParams();
                // 属性动画
                floatingActionButton.animate().translationY(floatingActionButton.getHeight() + params.bottomMargin).setInterpolator(new AccelerateDecelerateInterpolator());
            }

            @Override
            public void show() {
                // 回到原位置
                floatingActionButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator());
            }
        }));
    }
}

/*

首页微博展示
 */
