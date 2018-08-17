package com.fy.weibo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fy.weibo.Base.BaseFragment;
import com.fy.weibo.R;
import com.fy.weibo.adapter.WeiBoAdapter;
import com.fy.weibo.bean.WeiBo;
import com.fy.weibo.presenter.WeiBoPresenter;
import com.fy.weibo.sdk.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public class FirstPageFragment extends BaseFragment<List<WeiBo>> {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private WeiBoAdapter weiBoAdapter;
    private SwipeRefreshLayout refreshLayout;
    private List<WeiBo> publicWeiBoList = null;


    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", Constants.ACCESS_TOKEN);
        presenter.loadData(Constants.GET_LASTED_PUBLIC_WEI_BO, params, presenter);
    }

    @Override
    public void initPresenter() {
        presenter = new WeiBoPresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.first_page_layout;
    }


    @Override
    public void initAllMembersView(Bundle saveInstanceState) {

        recyclerView = mRootView.findViewById(R.id.public_wei_bo_rec_view);
        refreshLayout = mRootView.findViewById(R.id.refresh);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        // 设置item间距
        DividerItemDecoration decoration = new DividerItemDecoration(getAttachActivity(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getAttachActivity(), R.drawable.item_decoration)));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout = mRootView.findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(R.color.orange, R.color.orangered);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    public void setData(final List<WeiBo> weiBoList) {
        publicWeiBoList = weiBoList;
        getAttachActivity().runOnUiThread(() -> {
            weiBoAdapter = new WeiBoAdapter(getAttachActivity(), publicWeiBoList);
            recyclerView.setAdapter(weiBoAdapter);
            refreshLayout.setRefreshing(false);
        });
    }
}

/*

首页微博展示
 */
