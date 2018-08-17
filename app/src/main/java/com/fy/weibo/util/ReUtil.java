package com.fy.weibo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fan on 2018/8/3.
 * Fighting!!!
 */
public class ReUtil {

    //   匹配微博来源
    public static String getSource(String source) {

        String regEx = ">(.*)<";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(source);
        return matcher.find() ? matcher.group(1) : "";
    }
}

/*
正则工具类
 */