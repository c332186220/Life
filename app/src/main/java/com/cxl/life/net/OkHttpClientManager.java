package com.cxl.life.net;

import android.os.Handler;
import android.os.Looper;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.OkHttpClient;

/**
 * Created by cxl on 2017/9/25.
 * 摘自张鸿洋的博客
 */

public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
//    private Gson mGson;

    private OkHttpClientManager()
    {
        mOkHttpClient = new OkHttpClient();
        //cookie enabled
//        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
//        mGson = new Gson();
    }
}
