package com.cxl.life.Util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by cxl on 2017/6/28.
 * 时间管理类
 */

public class TimeUtil {
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

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
