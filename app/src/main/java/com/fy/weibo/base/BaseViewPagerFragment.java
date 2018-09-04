package com.fy.weibo.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;


import com.fy.weibo.R;

import java.lang.reflect.Field;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public abstract class BaseViewPagerFragment extends BaseFragment {


    public TabLayout tabLayout;
    public ViewPager viewPager;

    protected abstract void initViewPager();

    @Override
    public int getContentViewId() {
        return R.layout.view_pager;
    }

    @Override
    public void initAllMembersView(Bundle saveInstanceState) {

        tabLayout = mRootView.findViewById(R.id.comment_tab);
        tabLayout.post(()-> setIndicator(tabLayout, 30, 30));
        viewPager = mRootView.findViewById(R.id.comment_view_pager);
        viewPager.setOffscreenPageLimit(2);
        initViewPager();
        tabLayout.setupWithViewPager(viewPager);

    }

    protected void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            //通过反射得到tablayout的下划线的Field
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            //得到承载下划线的LinearLayout   //源码可以看到SlidingTabStrip继承得到承载下划线的LinearLayout
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        //循环设置下划线的左边距和右边距
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


}
