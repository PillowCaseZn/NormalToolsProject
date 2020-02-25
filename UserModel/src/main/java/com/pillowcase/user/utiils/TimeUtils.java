package com.pillowcase.user.utiils;

import android.annotation.SuppressLint;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author      : PillowCase
 * Create On   : 2019-12-24 11:03
 * Description :
 */
public class TimeUtils {
    /**
     * 格式化日期
     */
    @SuppressLint("SimpleDateFormat")
    public static Date stringToDate(String dateString) {
        Date dateValue = null;
        try {
            ParsePosition position = new ParsePosition(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateValue = simpleDateFormat.parse(dateString, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateValue;
    }

    /**
     * 将时间转换为时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToStamp(String time) {
        long ts = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(time);
            ts = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(ts);
    }

    /**
     * 将时间戳转换为时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间戳转换为时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String stampToDate(long timeMillis, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }
}
