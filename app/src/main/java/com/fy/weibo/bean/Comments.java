package com.fy.weibo.bean;

import java.io.Serializable;

/**
 * Created by Fan on 2018/8/8.
 * Fighting!!!
 */
public final class Comments implements Serializable {

    private String created_at;
    private String text;
    private int floor_number;
    private SimpleUser user;

    public String getCreated_at() {
        return created_at;
    }

    public String getText() {
        return text;
    }

    public int getFloor_number() {
        return floor_number;
    }

    public SimpleUser getUser() {
        return user;
    }

}
