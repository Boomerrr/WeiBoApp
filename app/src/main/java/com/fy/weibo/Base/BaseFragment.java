package com.fy.weibo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public abstract class BaseFragment extends Fragment {

    protected BasePresenter presenter;
    public abstract int getContentViewId();
    public abstract void initAllMembersView(Bundle saveInstanceState);

    protected Toast toast;
    public Context mContext;
    public View mRootView;
    protected Activity mActivity;

    public void loadData(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(getContentViewId(), container, false);
        checkActivityAttachContext();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAllMembersView(savedInstanceState);
        initData();
        loadData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity) context;
    }

    public void showError(String e) {

    }

    protected boolean isAttachContext() {

        return getActivity() != null;
    }

    public void checkActivityAttachContext() {

        if (getActivity() == null) {
            throw new BaseMVPFragment.ActivityNotAttachedException();
        }
    }
    public FragmentActivity getAttachActivity() {
        checkActivityAttachContext();
        return (FragmentActivity) mContext;
    }

    public static class ActivityNotAttachedException extends RuntimeException{

        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity !");
        }
    }

    public void showLoading() {

    }
    public void hideLoading() {

    }

    public void initData() {

    }

}
