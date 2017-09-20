package com.cxl.life.util;

import android.app.Application;
import android.support.annotation.StringRes;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by cxl on 2017/9/15.
 * toast工具
 */

public class ToastUtil {
    private static Toast toast = null;  //全局Toast
    private static WeakReference<Application> app;

    public static void init(Application application) {
        app = new WeakReference<>(application);
    }

    /**
     * 根据资源id展示toast内容
     */
    public static void showToast(@StringRes int resId) {
        if (toast == null) {
            toast = Toast.makeText(app.get(), resId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }

    /**
     * 直接展示toast提示内容
     */
    public static void showToast(String res) {
        if (toast == null) {
            toast = Toast.makeText(app.get(), res, Toast.LENGTH_SHORT);
        } else {
            toast.setText(res);
        }
        toast.show();
    }

    /**
     * 展示连续的toast内容
     */
    public static void showLinkToast(String res) {
        Toast toast = Toast.makeText(app.get(), res, Toast.LENGTH_SHORT);
//        toast.setDuration(200);
        toast.setText(res);
        toast.show();
    }


}
