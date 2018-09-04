package com.fy.weibo.fragment;


import com.fy.weibo.base.BaseViewPagerFragment;
import com.fy.weibo.adapter.PagerAdapter;
import com.fy.weibo.sdk.Constants;


/**
 * Created by Fan on 2018/8/23.
 * Fighting!!!
 */
public final class CommentViewPagerFragment extends BaseViewPagerFragment {


    @Override
    public void initViewPager() {

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(CommentsFragment.getInstance(Constants.GET_TO_ME_COMMENTS), "我收到的评论");
        pagerAdapter.addFragment(CommentsFragment.getInstance(Constants.GET_BY_ME_COMMENTS), "我发出的评论");
        viewPager.setAdapter(pagerAdapter);
    }


//  通过反射设置下划线的宽度
}
