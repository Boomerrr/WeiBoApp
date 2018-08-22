package com.fy.weibo.util;

import java.util.Map;
import java.util.Map.*;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Fan on 2018/7/24.
 * Fighting!!!
 */
public class HttpUtil {

/*
 参数只有token时才能使用该方法
*/
    public static void getData(String address, Map<String, String> params, Callback callback) {

        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = HttpUrl.parse(address);
        if (httpUrl != null) {

            HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
            for (Entry<String, String> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
//                System.out.println(entry.getKey() + entry.getValue());
            }
            httpUrl = urlBuilder.build();
            Request request = new Request.Builder()
                    .url(httpUrl.toString())
                    .build();
//            System.out.println(httpUrl.toString());

            Call call = client.newCall(request);
            if (call != null) {
                call.enqueue(callback);
            }

        }

    }

    //  post 请求

    public static void post(String baseUrl, Map<String, String> params, Callback callback) {

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Entry<String, String> entry : params.entrySet()) {
            formBodyBuilder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder()
                .url(baseUrl)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);

    }




//评论部分post
    public static void sendOkhttpRequest1(String address, String token, String id, String comment, Callback callback){

        try {
            OkHttpClient client=new OkHttpClient();
            String idstr=String.valueOf(id);
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            FormBody formBody = formBodyBuilder.add("comment",comment).build();
            Request request=new Request.Builder().url(
                    address+"?access_token="+token+"&id="+idstr
            ).post(formBody).build();
            Call call = client.newCall(request);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

/*
将请求参数封装到了Map中
 */

