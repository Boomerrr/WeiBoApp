package com.fy.weibo.fragment;

import com.fy.weibo.adapter.PagerAdapter;
import com.fy.weibo.base.BaseViewPagerFragment;
import com.fy.weibo.sdk.Constants;

/**
 * Created by Fan on 2018/8/29.
 * Fighting!!!
 */
public final class MentionViewPagerFragment extends BaseViewPagerFragment {


    @Override
    protected void initViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(WeiBoFragment.getInstance(Constants.GET_WEIBO_MENTION), "微博");
        pagerAdapter.addFragment(CommentsFragment.getInstance(Constants.GET_COMMENT_MENTION), "评论");
        viewPager.setAdapter(pagerAdapter);
    }
}
