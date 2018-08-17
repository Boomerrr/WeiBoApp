package com.fy.weibo.Base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fy.weibo.interfaces.IView;

import java.util.List;
import java.util.Map;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public abstract class BaseFragment<T> extends Fragment implements IView<T>{


    protected BasePresenter presenter;
    protected abstract void loadData();
    public abstract void initPresenter();
    public abstract int getContentViewId();
    public abstract void initAllMembersView(Bundle saveInstanceState);

    protected Toast toast;
    public Context mContext;
    public View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(getContentViewId(), container, false);
        checkActivityAttachContext();
        this.mContext = getActivity();
        initPresenter();
        initAllMembersView(savedInstanceState);
        loadData();
        return mRootView;
    }

    @Override
    public void showError(String e) {
        if (isAttachContext()) {
            toast = Toast.makeText(mContext, e, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    protected boolean isAttachContext() {

        return getActivity() != null;
    }

    public void checkActivityAttachContext() {

        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
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

}
