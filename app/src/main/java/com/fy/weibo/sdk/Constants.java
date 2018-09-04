package com.fy.weibo.sdk;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by Fan on 2018/7/24.
 * Fighting!!!
 */
public final class Constants {

    // 2.00YhYe2GcjI2oB572035d98fYbknMC   action=login&response_type=code&redirect_uri=https%3A%2F%2Fapi.weibo.com%2Foauth2%2Fdefault.html&client_id=1659988100&appkey62=2FFUmo&userId=15540922270&passwd=zhangfan123
    public static final String APP_KEY = "1659988100";
    public static  String UID = "";
    public static String USER_ACCOUNT = "";
    public static String USER_PASSWORD = "";
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static Oauth2AccessToken accessToken = null;
    public static  String ACCESS_TOKEN = "";
    public static final String SCOPE = null;
    public static final String App_SECRET = "c624038a6b168c44d8fea099c11199e0";
    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;
    public static final int SHARE_ALL_IN_ONE = 2;
    public static final String TAG = "TAG";
    // post 请求
//    public static final String ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token";
    // post 请求
    public static final String GET_TOKEN_INFO = "https://api.weibo.com/oauth2/get_token_info";
    public static final String GET_PUBLIC_WEI_BO = "https://api.weibo.com/2/statuses/public_timeline.json";
    public static final String GET_ATTENTION_WEIBO = "https://api.weibo.com/2/statuses/home_timeline.json";
    public static final String GET_COMMENT = "https://api.weibo.com/2/comments/show.json";
    public static final String POST_REMOVE_TOKEN = "https://api.weibo.com/oauth2/revokeoauth2";
    public static final String GET_USERS_SHOW = "https://api.weibo.com/2/users/show.json";
    public static final String GET_USERS_COUNTS = "https://api.weibo.com/2/users/counts.json";
    public static final String GET_BY_ME_COMMENTS = "https://api.weibo.com/2/comments/by_me.json";
    public static final String GET_TO_ME_COMMENTS = "https://api.weibo.com/2/comments/to_me.json";
    public static final String GET_COMMENT_MENTION = "https://api.weibo.com/2/comments/mentions.json";
    public static final String GET_WEIBO_MENTION = "https://api.weibo.com/2/statuses/mentions.json";

}

/*
常量类 weibo开放的 API  还有应用的注册信息
 */