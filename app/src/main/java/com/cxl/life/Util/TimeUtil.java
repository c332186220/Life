package com.cxl.life.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cxl on 2017/6/28.
 * 时间管理类
 */

public class TimeUtil {
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("M/d/yy H:mm", Locale.getDefault());

    /**
     * 返回当前时间的格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime(int type) {
        String time = "最新时间";
        switch (type) {
            case 1:
                time = sdf1.format(System.currentTimeMillis());
                break;
            case 2:
                time = sdf2.format(System.currentTimeMillis());
                break;
        }
        return time;
    }


    /**
     * 字符串转时间戳
     *
     * @param time 字符串
     * @return
     */
    public static long stringToLong(String time, int type) {
        Date date;
        long simpleTime;
        try {
            switch (type) {
                case 3:
                    date = sdf3.parse(time);
                    break;
                default:
                    date = new Date();
                    break;
            }
            simpleTime = date.getTime();
        } catch (ParseException e) {
            simpleTime = System.currentTimeMillis();
        }
        return simpleTime;
    }

    /**
     * 时间戳转化为日期
     */
    public static String longToString(long longTime, int type) {
        if (longTime == 0) {
            longTime = System.currentTimeMillis();
        }
        String time;
        switch (type) {
            case 3:
                time = sdf3.format(longTime);
                break;
            default:
                time = sdf2.format(longTime);
                break;
        }
        return time;
    }


    /**
     * 时间戳转换为星期
     */
    public static int longToWeek(long longTime) {
        if (longTime == 0) {
            longTime = System.currentTimeMillis();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(longTime);
        //星期天-星期六 对应 1-7
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 毫秒转秒
     */
    public static String longToString(long time) {
        int sec = (int) time / 1000;
        int min = sec / 60;    //分钟
        sec = sec % 60;        //秒
        if (min < 10) {    //分钟补0
            if (sec < 10) {    //秒补0
                return "0" + min + ":0" + sec;
            } else {
                return "0" + min + ":" + sec;
            }
        } else {
            if (sec < 10) {    //秒补0
                return min + ":0" + sec;
            } else {
                return min + ":" + sec;
            }
        }
    }
}
