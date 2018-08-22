package com.fy.weibo.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fy.weibo.Base.BaseActivity;
import com.fy.weibo.R;
import com.fy.weibo.adapter.CommentsAdapter;
import com.fy.weibo.adapter.WeiBoImgAdapter;
import com.fy.weibo.bean.Comments;
import com.fy.weibo.bean.PicUrlsBean;
import com.fy.weibo.bean.WeiBo;
import com.fy.weibo.presenter.CommentsPresenter;
import com.fy.weibo.sdk.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContentActivity extends BaseActivity<List<Comments>> {


    private RecyclerView recyclerView;
    private CommentsAdapter commentsAdapter;
    private List<Comments> commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }


    @Override
    public void setData(final List<Comments> comments) {

        runOnUiThread(() -> {
            commentsList = comments;
            commentsAdapter = new CommentsAdapter(ContentActivity.this, commentsList);
            recyclerView.setAdapter(commentsAdapter);
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.content_layout;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.comment_content_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.comment_decoration)));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void initPresenter() {
        presenter = new CommentsPresenter(this);
    }

    public void initData() {

        WeiBo weiBo = (WeiBo) getIntent().getSerializableExtra("weibo");
        TextView weiBoText = this.findViewById(R.id.wei_bo_content_text);
        CircleImageView userImage = this.findViewById(R.id.user_img);
        TextView weiBoTime = findViewById(R.id.send_time);
        TextView weiBoSource = findViewById(R.id.wei_bo_source);
        TextView userName = findViewById(R.id.user_name);
        TextView commentCounts = findViewById(R.id.comment_counts);
        TextView shareCounts = findViewById(R.id.share_counts);
        TextView thumbUpCounts = findViewById(R.id.thumb_up_counts);
        weiBoText.setMaxLines(100);
        weiBoText.setText(weiBo.getText());
        RequestOptions options = new RequestOptions().placeholder(new ColorDrawable(Color.WHITE));
        Glide.with(this)
                .load(weiBo.getUser().getProfile_image_url())
                .apply(options)
                .into(userImage);
        weiBoTime.setText(weiBo.getCreated_at());
        weiBoSource.setText(weiBo.getSource());
        userName.setText(weiBo.getUser().getScreen_name());
        commentCounts.setText(weiBo.getComments_count());
        shareCounts.setText(weiBo.getReposts_count());
        thumbUpCounts.setText(weiBo.getAttitudes_count());
        List<PicUrlsBean> imgUrls = weiBo.getPic_urls();
        if (imgUrls != null) {
            List<String> urls = new ArrayList<>();
            for (PicUrlsBean url: imgUrls) {
                urls.add(url.getThumbnail_pic().replaceFirst("thumbnail", "bmiddle"));
            }
            RecyclerView imgRecyclerView = findViewById(R.id.wei_bo_img_recycler);
            imgRecyclerView.setNestedScrollingEnabled(false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            imgRecyclerView.setLayoutManager(gridLayoutManager);
            WeiBoImgAdapter adapter = new WeiBoImgAdapter(this, urls);
            imgRecyclerView.setAdapter(adapter);
        }

    }


    @Override
    public void loadData() {
        WeiBo weiBo = (WeiBo) getIntent().getSerializableExtra("weibo");
        String strId = weiBo.getIdstr();
        Map<String, String> params = new HashMap<>();
        params.put("access_token", Constants.ACCESS_TOKEN);
        params.put("id", strId);
        presenter.loadData(Constants.GET_COMMENT, params, presenter);
    }

    @Override
    public void showError(String e) {
        super.showError(e);
    }
}

/*

详情展示界面
 */