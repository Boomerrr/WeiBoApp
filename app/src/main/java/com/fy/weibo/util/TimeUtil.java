package com.fy.weibo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Fan on 2018/8/3.
 * Fighting!!!
 */
public class TimeUtil {


    public static String GMTtoNormal(String GMTTime) {

        SimpleDateFormat GMTFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        SimpleDateFormat normalTime;
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        GMTFormat.setTimeZone(timeZone);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDate = calendar.get(Calendar.DATE);
        int currentHour = calendar.get(Calendar.HOUR);
        int currentMin = calendar.get(Calendar.MINUTE);
        Date time;
        try {
            time = GMTFormat.parse(GMTTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time);
            int year = calendar1.get(Calendar.YEAR);
            int month = calendar1.get(Calendar.MONTH) + 1;
            int date = calendar1.get(Calendar.DATE);
            int hour = calendar1.get(Calendar.HOUR);
            int min = calendar1.get(Calendar.MINUTE);
            if (year < currentYear) {
                normalTime = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                return normalTime.format(time);
            } else if (month < currentMonth) {
                normalTime = new SimpleDateFormat("MM-dd", Locale.CHINA);
                return normalTime.format(time);
            } else if (currentDate > date) {
                if (date == currentDate - 1) {
                    normalTime = new SimpleDateFormat("HH:mm", Locale.CHINA);
                    return "昨天" + " " + normalTime.format(time);
                } else {
                    normalTime = new SimpleDateFormat("MM-dd HH:mm", Locale.US);
                    return normalTime.format(time);
                }
            } else if(date == currentDate && month == currentMonth && hour == currentHour && min == currentMin){
                return "刚刚";
            } else
                return new SimpleDateFormat("HH:mm", Locale.CHINA).format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}

/*
时间工具类  用于微博的时间格式化
 */