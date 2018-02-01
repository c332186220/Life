package com.cxl.life.util;

import android.os.Environment;

import com.cxl.life.bean.User;

import org.litepal.crud.DataSupport;

/**
 * Created by cxl on 2017/6/29.
 * 常量管理
 */

public class Constants {
    public static final String SD = Environment.getExternalStorageDirectory().getAbsolutePath();
    //存放录制声音
    public static String voice_sd = SD+"/life/";
    public static String journal_sd = SD+"/life/journal/";
    //默认下载路径
    public static String download_sd = SD+"/life/download/";
    //错误日志存储路径
    public static String crash_sd = SD+"/life/crash/";
    //错误日志存储路径
    public static String video_store_sd = SD+"/life/video/test.mp4";

    //以下是SP的key值
    public static final String SP_fIRST_ENTRY="sp_first_entry";

    public static String SERVICE_ACTION = "com.cxl.life.REFRESH_CONTENT";//广播名字
    public static String LOCATION_ACTION = "location_action";//定位跳转动作
    public static String LOCATION_LONGITUDE = "location_longitude";//经度
    public static String LOCATION_LATITUDE = "location_latitude";//纬度
    public static String LOCATION_INFO = "location_info";//定位详情

    public static User loginUser;

    public static User getUser(){
        if(loginUser==null){
            loginUser = DataSupport.where("loginState = ?","1").findFirst(User.class);
        }
        return loginUser;
    }

}
