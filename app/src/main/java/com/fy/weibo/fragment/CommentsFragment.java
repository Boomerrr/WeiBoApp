package com.fy.weibo.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fy.weibo.activity.MainActivity;
import com.fy.weibo.base.BaseMVPFragment;
import com.fy.weibo.R;
import com.fy.weibo.adapter.CommentsAdapter;
import com.fy.weibo.bean.Comments;
import com.fy.weibo.contract.CommentContract;
import com.fy.weibo.contract.WeiBoContract;
import com.fy.weibo.interfaces.IBaseView;
import com.fy.weibo.listener.MyScrollListener;
import com.fy.weibo.presenter.CommentsPresenter;
import com.fy.weibo.sdk.Constants;
import com.fy.weibo.util.NetStateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by Fan on 2018/8/22.
 * Fighting!!!
 */
public final class CommentsFragment extends BaseMVPFragment<CommentContract.CommentContractPresenter> implements CommentContract.CommentView {


    String url = "";
    List<Comments> commentsList;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    CommentsAdapter commentsAdapter;

    public static CommentsFragment getInstance(String from) {

        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        CommentsFragment commentsFragment = new CommentsFragment();
        commentsFragment.setArguments(bundle);
        return commentsFragment;
    }

    @Override
    public void initPresenter() {
        mPresenter = new CommentsPresenter();
        mPresenter.attachMV(this);
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
            Log.e("TAG", "url---" + url);
        }
        ((MainActivity)mActivity).floatButton.animate().translationY(0).setDuration(100);
        recyclerView = mRootView.findViewById(R.id.rec_view);
        refreshLayout = mRootView.findViewById(R.id.refresh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getAttachActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getAttachActivity(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getAttachActivity(), R.drawable.item_decoration)));
        recyclerView.addItemDecoration(decoration);
        setScrollListener();
        refreshLayout.setColorSchemeResources(R.color.orange, R.color.orangered);
        refreshLayout.setOnRefreshListener(() -> {
            loadComments();
            if (!NetStateUtil.checkNet(mActivity))
            Toast.makeText(mActivity, "请检查网络", Toast.LENGTH_SHORT).show();
        });

    }


    @Override
    public void loadComments() {
        refreshLayout.setRefreshing(true);
        if (!NetStateUtil.checkNet(mActivity))
            refreshLayout.setRefreshing(false);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", Constants.ACCESS_TOKEN);
        mPresenter.loadComments(url, params);
    }

    @Override
    public void setComments(List<Comments> comments) {
        getAttachActivity().runOnUiThread(() -> {
            if (commentsAdapter != null) {
                commentsList.addAll(comments);
                commentsAdapter.notifyDataSetChanged();
            }

            commentsList = comments;
            commentsAdapter = new CommentsAdapter(getAttachActivity(), commentsList);
            recyclerView.setAdapter(commentsAdapter);
            refreshLayout.setRefreshing(false);
        });
    }


    @Override
    public CommentContract.CommentContractPresenter getPresenter() {
        return new CommentsPresenter();
    }

    @Override
    public void loadData() {
        loadComments();
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

    private void setScrollListener() {

        FloatingActionButton floatingActionButton =  ((MainActivity) mActivity).floatButton;
        recyclerView.addOnScrollListener(new MyScrollListener(new MyScrollListener.HideListener() {
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
