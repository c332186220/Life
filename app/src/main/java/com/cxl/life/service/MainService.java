package com.cxl.life.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.cxl.life.R;
import com.cxl.life.login.LoginActivity;
import com.cxl.life.util.L;
import com.cxl.life.util.TimeUtil;

/**
 * 跟主界面绑定的服务
 */
public class MainService extends Service {

    private MainBinder mBinder = new MainBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.e("MainService", "onCreate()");
        super.onCreate();
        //通知栏
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        notificationIntent.putExtra("from","我来自服务");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("标题")
                .setContentText("这是内容部分")
                .setTicker("您有新消息，请注意查收！======")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent).build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;//设置可以清除
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //根据id来管理通知
        mNotificationManager.notify(101, notification);
//        startForeground(101, notification);//将服务变为前台服务

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MainService", "onStartCommand()");
        //开启系统定时闹钟
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int minute = 1000;
        long triggerTime = SystemClock.elapsedRealtime() + minute;
        Intent i = new Intent(this,MainService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerTime,pi);
        //发送广播，通知更新
        Intent broadcast = new Intent("com.cxl.life.REFRESH_CONTENT");
        Bundle bundle = new Bundle();
        bundle.putString("message", "您有一则新消息，请注意查收！    " + TimeUtil.getCurrentTime(2));
        broadcast.putExtras(bundle);
        sendBroadcast(broadcast);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("MainService", "onDestroy()");
        super.onDestroy();
    }

    /**
     * 自定义Binger类
     */
    public class MainBinder extends Binder {

        public void startDownload() {
            L.e("MainService", "开启下载任务");
            // 执行具体的下载任务
        }

    }
}
