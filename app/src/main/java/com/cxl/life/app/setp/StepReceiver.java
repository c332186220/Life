package com.cxl.life.app.setp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StepReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent mainIntent = new Intent(context,StepActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);
    }
}
