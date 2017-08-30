package com.cxl.life.base;

import android.app.Application;
import android.content.res.Configuration;

import com.cxl.life.util.L;
import com.cxl.life.app.BaseActivity;

/**
 * Created by cxl on 2017/7/13.
 * 程序入口
 */

public class LifeApplication extends Application {
    @Override
    public void onCreate() {
        BaseActivity.isLogin = false;
        BaseActivity.time = System.currentTimeMillis();
        L.e("程序初始化..");
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        L.e("程序被终止..");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        L.e("内存偏低..");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        L.e("配置改变..");
    }
}
