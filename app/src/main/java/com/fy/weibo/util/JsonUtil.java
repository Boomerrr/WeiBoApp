package com.fy.weibo.util;

import android.util.Log;

import com.fy.weibo.bean.Comments;
import com.fy.weibo.bean.WeiBo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fan on 2018/7/31.
 * Fighting!!!
 */
public class JsonUtil {


    // 微博
    public static List<WeiBo> getLastedPublicWeiBo(String json) {

        List<WeiBo> lastedWeiBo = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("statuses");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject weiBoContent = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                WeiBo lastedWeiBoBean = gson.fromJson(weiBoContent.toString(), WeiBo.class);
                lastedWeiBo.add(lastedWeiBoBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lastedWeiBo;
    }
//  评论
    public static List<Comments> getComments(String json) {

        List<Comments> commentList = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("comments");
            if (jsonArray.length() == 0) {
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject commentContent = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                Comments comments = gson.fromJson(commentContent.toString(), Comments.class);
                commentList.add(comments);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return commentList;
    }
}

/*
json 工具类
 */