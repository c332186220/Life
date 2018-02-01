package com.cxl.life.service;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.cxl.life.R;
import com.cxl.life.login.LoginActivity;
import com.cxl.life.util.Constants;
import com.cxl.life.util.L;
import com.cxl.life.util.TimeUtil;
import com.cxl.life.util.ToastUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 跟主界面绑定的服务
 */
public class MainService extends Service {
    private LocationManager locationManager;//定位服务

    private MainBinder mBinder = new MainBinder();
    private Geocoder geocoder = null;

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
        notificationIntent.putExtra("from", "我来自服务");
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
        Intent i = new Intent(this, MainService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        //发送广播，通知更新
//        Intent broadcast = new Intent(Constants.SERVICE_ACTION);
//        Bundle bundle = new Bundle();
//        bundle.putString("message", "您有一则新消息，请注意查收！    " + TimeUtil.getCurrentTime(2));
//        broadcast.putExtras(bundle);
//        sendBroadcast(broadcast);

        initLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initLocation() {
        if (locationManager != null) {
            return;
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //权限判断
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } else if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
            locationManager
                    .requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                            locationListener);
        } else {
            ToastUtil.showToast("无法定位");
        }
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

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //通知Activity
            getLocationDetail(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    //获取并展示定位详情
    private void getLocationDetail(Location location) {
        StringBuilder strb = new StringBuilder();
        strb.append("经度：" + location.getLongitude() + "\n");
        strb.append("纬度：" + location.getLatitude() + "\n");
        strb.append("海拔：" + location.getAltitude() + "\n");
        strb.append("定位时间：" + TimeUtil.longToString(location.getTime(), 0) + "\n");
        strb.append("位置来源：" + location.getProvider() + "\n");
        strb.append("精确度：" + location.getAccuracy() + "\n");

        if (geocoder == null) {
            geocoder = new Geocoder(this, Locale.CHINA);
        }
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses == null || addresses.size() == 0) {
                strb.append("没找到相关地址");
            } else {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    strb.append(address.getAddressLine(i));
                    strb.append("-\n");
                }
                if (!TextUtils.isEmpty(address.getFeatureName())) {
                    strb.append(address.getFeatureName());
                }
            }
        } catch (IOException e) {
            strb.append("定位获取详情服务不可用");
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setAction(Constants.SERVICE_ACTION);
        intent.putExtra("message", strb.toString());
        sendBroadcast(intent);
    }
}
