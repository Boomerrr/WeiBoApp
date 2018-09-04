package com.fy.weibo.fragment;


import com.fy.weibo.base.BaseViewPagerFragment;
import com.fy.weibo.adapter.PagerAdapter;
import com.fy.weibo.sdk.Constants;

/**
 * Created by Fan on 2018/8/28.
 * Fighting!!!
 */
public final class WeiBoViewPagerFragment extends BaseViewPagerFragment {


    @Override
    protected void initViewPager() {

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(WeiBoFragment.getInstance(Constants.GET_PUBLIC_WEI_BO), "随机微博");
        pagerAdapter.addFragment(WeiBoFragment.getInstance(Constants.GET_ATTENTION_WEIBO), "我的关注");
        viewPager.setAdapter(pagerAdapter);
    }

}
