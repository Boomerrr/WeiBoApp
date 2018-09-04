package com.fy.weibo.sdk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;

/**
 * Created by Fan on 2018/8/18.
 * Fighting!!!
 */
public class WeiBoShare {


    private Context context;
    private WbShareHandler shareHandler;

    public WeiBoShare(Context context) {
        this.context = context;
        shareHandler = new WbShareHandler((Activity) context);
        shareHandler.registerApp();
        shareHandler.setProgressColor(Color.BLUE);
    }
    public  void sendWeiBo() {

        WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
        weiboMultiMessage.textObject = getTextObject();
        shareHandler.shareMessage(weiboMultiMessage, false);
    }

    private TextObject getTextObject() {

        TextObject textObject = new TextObject();
        textObject.text = "发微博测试";
        textObject.title = "title";
        textObject.actionUrl = "https://www.baidu.com";
        return textObject;
    }


}
