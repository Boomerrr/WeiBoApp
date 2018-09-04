package com.fy.weibo.util;

import android.util.Log;
import com.fy.weibo.App;
import java.io.File;
import java.util.Map;
import java.util.Map.*;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Fan on 2018/7/24.
 * Fighting!!!
 */
public  class HttpUtil {

    private HttpCacheInterceptor cacheInterceptor = new HttpCacheInterceptor();
    private  int CACHE_SIZE = 10 * 1024 * 1024;
    private String CACHE_PATH = App.getAppInstance().getApplicationContext().getCacheDir() + "/okCache";
    public static HttpUtil getHttpUtil() {
        return HttpUtilHolder.httpUtil;
    }

    private static class HttpUtilHolder{
        private static HttpUtil httpUtil = new HttpUtil();
    }

    private OkHttpClient.Builder okHttpBuilder = new OkHttpClient
            .Builder()
            .addInterceptor(getLoggerInterceptor());

    private HttpLoggingInterceptor getLoggerInterceptor() {
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.HEADERS;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d("APIUrl", "----->" + message));
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    private Cache getCache(String cachePath, long cacheSize) {
        final String CACHE_PATH = App.getAppInstance()
                .getApplicationContext()
                .getCacheDir().getAbsolutePath() + cachePath;
        File cacheFile = new File(CACHE_PATH);
        return new Cache(cacheFile, cacheSize);
    }


    public  void getData(String address, Map<String, String> params, Callback callback) {

        HttpUrl httpUrl = HttpUrl.parse(address);
        if (httpUrl != null) {
            HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
            for (Entry<String, String> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            httpUrl = urlBuilder.build();

             Request.Builder requestBuilder = new Request
                    .Builder()
                     .url(httpUrl.toString());
            Call call = new OkHttpClient.Builder()
                    .cache(getCache(CACHE_PATH, CACHE_SIZE))
                    .addInterceptor(getLoggerInterceptor())
                    .addNetworkInterceptor(cacheInterceptor)
                    .build()
                    .newCall(requestBuilder.build());
            if (call != null) {
                call.enqueue(callback);
            }

        }

    }

    //  post 请求

    public  void post(String baseUrl, Map<String, String> params, Callback callback) {

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Entry<String, String> entry : params.entrySet()) {
            formBodyBuilder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = formBodyBuilder.build();

         Request.Builder requestBuilder = new Request
                .Builder()
                 .url(baseUrl)
                 .post(formBody);

        Call call = okHttpBuilder
                .build()
                .newCall(requestBuilder.build());
        if (call != null) {
            call.enqueue(callback);
        }
    }
}



/*
将请求参数封装到了Map中
 */

