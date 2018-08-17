package com.fy.weibo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fy.weibo.sdk.Constants;

/**
 * Created by Fan on 2018/8/16.
 * Fighting!!!
 */
public class DataBaseUtil extends SQLiteOpenHelper {


    private static final String CREATE_USER = "CREATE TABLE User (account text, password text, token text)";
    private Context context;
    public DataBaseUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        Log.e(Constants.TAG, "create successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

/*

数据库工具类
 */