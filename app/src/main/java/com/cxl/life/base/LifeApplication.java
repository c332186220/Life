package com.cxl.life.base;

import android.content.res.Configuration;

import com.cxl.life.util.CrashHandler;
import com.cxl.life.util.L;
import com.cxl.life.app.BaseActivity;
import com.cxl.life.util.TUtil;
import com.cxl.life.util.ToastUtil;

import org.litepal.LitePalApplication;

/**
 * Created by cxl on 2017/7/13.
 * 程序入口
 */

public class LifeApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        BaseActivity.isLogin = false;
        BaseActivity.time = System.currentTimeMillis();
        L.e("程序初始化..");
        ToastUtil.init(this);//初始化toast
        //异常日志捕获
        CrashHandler.getInstance().init(this);
        new TUtil().init(this);
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
