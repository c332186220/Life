package com.cxl.life.app;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.cxl.life.util.L;

/**
 * Created by cxl on 2017/7/13.
 * 基类
 */

public class BaseActivity extends AppCompatActivity {
    public static long time;
    public static boolean isLogin;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            long t = System.currentTimeMillis();
            if (isLogin && t - time > 1000 * 20) {
                L.e((t - time) / 1000 + "秒未操作，将执行自动退出");
                isLogin = false;
                this.finish();
                time = t;
                return true;
            } else {
                L.e("距离上次点击：" + (t - time) / 1000);
                time = t;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
