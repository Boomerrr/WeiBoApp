package com.fy.weibo.bean;

import com.fy.weibo.util.ReUtil;
import com.fy.weibo.util.TimeUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fan on 2018/7/30.
 * Fighting!!!
 */
public final class WeiBo implements Serializable {

    private String idstr;
    private String created_at;
    private String text;
    private String thumbnail_pic;
    private String original_pic;
    private int reposts_count;
    private int comments_count;
    private int attitudes_count;
    private String source;
    private SimpleUser user;
    private List<PicUrlsBean> pic_urls;


    public List<PicUrlsBean> getPic_urls() {
        return pic_urls;
    }


    public String getIdstr() {
        return idstr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    public String getOriginal_pic() {
        return original_pic;
    }

    public void setOriginal_pic(String original_pic) {
        this.original_pic = original_pic;
    }

    public String getReposts_count() {
        return reposts_count + "";
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public String getComments_count() {
        return comments_count + "";
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getCreated_at() {
        return TimeUtil.GMTtoNormal(this.created_at);
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


    public String getAttitudes_count() {
        return attitudes_count + "";
    }

    public void setAttitudes_count(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public String getSource() {
        return ReUtil.getSource(this.source);
    }

    public void setSource(String source) {
        this.source = source;
    }

    public SimpleUser getUser() {
        return user;
    }

    public void setUser(SimpleUser user) {
        this.user = user;
    }

    public void setPic_urls(List<PicUrlsBean> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }


}
