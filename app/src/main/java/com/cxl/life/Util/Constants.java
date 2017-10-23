package com.cxl.life.util;

import android.os.Environment;

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

}
