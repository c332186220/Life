package com.cxl.life.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * Created by cxl on 2018/1/30.
 * sp存储的所有内容
 */

public class TUtil {
    private static Context mContext;
    private static SharedPreferences mPreferences;

    public void init(Context context) {
        this.mContext = context;
        String name = mContext.getPackageName();
        this.mPreferences = this.mContext
                .getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 保存字符串
     *
     * @param key   键
     * @param value 值
     */
    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public static String getString(String key) {
        return mPreferences.getString(key, "");
    }

    /**
     * 保存long型
     *
     * @param key   键
     * @param value 值
     */
    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    /**
     * 保存int型
     *
     * @param key   键
     * @param value 值
     */
    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    /**
     * 保存布尔型
     *
     * @param key   键
     * @param value 值
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public static boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    /**
     * 删除对应的值
     *
     * @param key
     */
    public static void delete(String key) {
        File file = mContext.getFileStreamPath(key);
        if (file.exists()) {
            file.delete();
        }
    }
}
