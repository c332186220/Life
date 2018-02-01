package com.cxl.life.app.function.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.app.BaseTitleActivity;
import com.cxl.life.util.L;
import com.cxl.life.util.TimeUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends BaseTitleActivity {
    private TextView longitude, latitude, altitude, time, from, addressDetail,accuracy;//经度 纬度 海拔  定位时间  位置来源 地址详情 精确
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Geocoder geocoder = null;
    private Location curLocation;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_location);
    }

    @Override
    public void initView() {
        super.initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, getResources().getString(R.string.activity_location), true);

        longitude = (TextView) findViewById(R.id.location_longitude);
        altitude = (TextView) findViewById(R.id.location_altitude);
        latitude = (TextView) findViewById(R.id.location_latitude);
        time = (TextView) findViewById(R.id.location_achieve_time);
        from = (TextView) findViewById(R.id.location_from);
        addressDetail = (TextView) findViewById(R.id.location_address);
        accuracy = (TextView) findViewById(R.id.location_accuracy);
        initLocation();
    }

    private void initLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            toast("缺少定位权限");
            return;
        }
        //获取定位管理
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(isBetterLocation(location,curLocation)){
                    curLocation=location;
                    getLocationDetail(curLocation);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                switch (status) {
                    case LocationProvider.AVAILABLE:
                        toast("当前GPS为可用状态!");
                        break;

                    case LocationProvider.OUT_OF_SERVICE:
                        toast("当前GPS不在服务内");
                        break;

                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        toast("当前GPS不在服务内");
                        break;
                }
            }

            @Override
            public void onProviderEnabled(String provider) {
                toast("GPS开启了");
            }

            @Override
            public void onProviderDisabled(String provider) {
                toast("GPS关闭了");
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
    //获取并展示定位详情
    private void getLocationDetail(Location location) {
        double lon = location.getLongitude();//经度
        double lat = location.getLatitude();//纬度
        double alt = location.getAltitude();//海拔
        long ti = location.getTime();
        String fr = location.getProvider();

        longitude.setText(String.valueOf(lon));
        latitude.setText(String.valueOf(lat));
        altitude.setText(String.valueOf(alt));
        time.setText(TimeUtil.longToString(ti, 0));
        from.setText(fr);
        accuracy.setText(String.valueOf(location.getAccuracy()));

        if (geocoder == null) {
            geocoder = new Geocoder(this, Locale.CHINA);
        }
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses == null || addresses.size() == 0) {
                toast("没找到相关地址");
            } else {
                Address address = addresses.get(0);
                StringBuilder strb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    strb.append(address.getAddressLine(i));
                    strb.append("-\n");
                }
                if (!TextUtils.isEmpty(address.getFeatureName())) {
                    strb.append(address.getFeatureName());
                }
                addressDetail.setText(strb.toString());
            }
        } catch (IOException e) {
            L.e("定位获取详情服务不可用！");
            e.printStackTrace();
        }
    }
    //比较两个定位的准确性(从定位间隔，精准度，位置提供者)
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return true;
        }
        long EXPIRE_TIME = 10000;//查定位间隔时长
        long timeDelta = location.getTime() -currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta> EXPIRE_TIME;
        boolean isSignificantlyOlder = timeDelta< -EXPIRE_TIME;
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }

        int accuracyDelta = (int)(location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta >0;
        boolean isMoreAccurate = accuracyDelta <0;
        boolean isSignificantlyLessAccurate =accuracyDelta > 200;

        boolean isFromSameProvider =location.getProvider().equals(currentBestLocation.getProvider());

        if (isMoreAccurate) {
            return true;
        } else if (isNewer &&!isLessAccurate) {
            return true;
        } else if (isNewer &&!isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
