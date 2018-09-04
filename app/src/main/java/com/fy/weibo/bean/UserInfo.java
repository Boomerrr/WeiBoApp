package com.fy.weibo.bean;

import com.fy.weibo.util.TimeUtil;

import java.io.Serializable;

/**
 * Created by Fan on 2018/8/21.
 * Fighting!!!
 */
public final class UserInfo implements Serializable{

    private String idstr;
    private String screen_name;
    private String location;
    private String cover_image_phone;
    private String created_at;
    private boolean following;
    private String city;
    private String province;
    private String profile_image_url;
    private String gender;

    public String getGender() {
        return gender;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getIdstr() {
        return idstr;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getLocation() {
        return location;
    }

    public String getCover_image_phone() {
        return cover_image_phone;
    }

    public String getCreated_at() {
        return TimeUtil.GMTtoNormal(created_at);
    }

    public boolean isFollowing() {
        return following;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }
}
