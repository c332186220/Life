package com.cxl.life.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cxl.life.util.Constants;

/**
 * Created by cxl on 2018/1/23.
 * 原生定位广播
 */

public class LocationBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constants.LOCATION_ACTION)){
            String latitude = intent.getStringExtra(Constants.LOCATION_LATITUDE);
            String longitude = intent.getStringExtra(Constants.LOCATION_LONGITUDE);
        }
    }
}
