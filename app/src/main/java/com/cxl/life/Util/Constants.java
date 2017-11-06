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

    public static User loginUser;

    public static User getUser(){
        if(loginUser==null){
            loginUser = DataSupport.where("loginState = ?","1").findFirst(User.class);
        }
        return loginUser;
    }

}
