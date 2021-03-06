package com.cxl.life;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cxl.life.app.OtherActivity;
import com.cxl.life.app.effect.JdActivity;
import com.cxl.life.app.function.FunctionActivity;
import com.cxl.life.app.layout.LayoutMainActivity;
import com.cxl.life.image.PhotoPickerActivity;
import com.cxl.life.image.SelectModel;
import com.cxl.life.image.intent.PhotoPickerIntent;
import com.cxl.life.util.Constants;
import com.cxl.life.util.L;
import com.cxl.life.util.ScreenUtil;
import com.cxl.life.app.JournalActivity;
import com.cxl.life.app.draw.DrawLineActivity;
import com.cxl.life.app.king.KingGloryActivity;
import com.cxl.life.app.SettingActivity;
import com.cxl.life.app.wechat.WeChatActivity;
import com.cxl.life.login.LoginActivity;
import com.cxl.life.service.MainService;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout drawer;//抽屉
    private TextView show;//展示一些更新信息
    private ImageView dispersed, disperse1, disperse2, disperse3, disperse4;//分散式菜单

    private RefreshReceiver receiver;//通知性广播
    private boolean isAnimation = false;
    private RelativeLayout rl;//添加动态控件

    private ImageView imageHeader;//用户头像
    private TextView textHeader;//用户名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        bindService(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startBroadcast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopBroadcast();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //抽屉
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);  用此方法需要注销布局里的headerLayout
        View navHeaderView = navigationView.getHeaderView(0);
        imageHeader = (ImageView) navHeaderView.findViewById(R.id.nav_image_header);
        textHeader = (TextView) navHeaderView.findViewById(R.id.nav_name_header);
        textHeader.setText("登录");
        imageHeader.setOnClickListener(this);
        textHeader.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmFinishApp(view);
            }
        });

        show = (TextView) findViewById(R.id.main_text_show);
        dispersed = (ImageView) findViewById(R.id.main_dispersed);
        dispersed.setOnClickListener(this);
        disperse1 = (ImageView) findViewById(R.id.main_disperse1);
        disperse1.setOnClickListener(this);
        disperse2 = (ImageView) findViewById(R.id.main_disperse2);
        disperse2.setOnClickListener(this);
        disperse3 = (ImageView) findViewById(R.id.main_disperse3);
        disperse3.setOnClickListener(this);
        disperse4 = (ImageView) findViewById(R.id.main_disperse4);
        disperse4.setOnClickListener(this);

        rl = (RelativeLayout) findViewById(R.id.main_rl);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        addViewInRl();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        boolean gps = AUtil.gpsIsOpen(this);
//        if (!gps) {
//            initGPS();
//        }
    }

    //监听抽屉里的菜单按钮
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu1:
                startActivity(new Intent(this, KingGloryActivity.class));
                break;
            case R.id.nav_menu2:
                startActivity(new Intent(this, FunctionActivity.class));
                break;
            case R.id.nav_menu3:
                startActivity(new Intent(this, OtherActivity.class));
                break;
            case R.id.nav_menu4:
                startActivity(new Intent(this, LayoutMainActivity.class));
                break;
            case R.id.nav_share:
                startActivity(new Intent(this, DrawLineActivity.class));
                break;
            case R.id.nav_feedback:
                startActivity(new Intent(this, WeChatActivity.class));
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            default:
                Toast.makeText(this, "此地无银三百两", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        //监听返回键
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (disperse1.getVisibility() == View.VISIBLE) {
            //TODO 动画中会多次执行
            showDispersed();
        } else {
            confirmFinishApp(drawer);
        }
    }

    @Override
    public void onClick(View v) {
        if (isAnimation) {
            return;
        }
        switch (v.getId()) {
            case R.id.nav_image_header:
                if (Constants.loginUser == null) {
                    startActivityForResult(new Intent(this, LoginActivity.class), 1001);
                } else {
                    PhotoPickerIntent intent1 = new PhotoPickerIntent(MainActivity.this);
                    intent1.setSelectModel(SelectModel.SINGLE);
                    intent1.setShowCarema(true);
                    startActivityForResult(intent1, 1002);
                }
                break;
            case R.id.nav_name_header:
                startActivityForResult(new Intent(this, LoginActivity.class), 1001);
                break;
            case R.id.main_dispersed:
                showDispersed();
                break;
            case R.id.main_disperse1:
                Toast.makeText(this, "电话", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, JdActivity.class));
                showDispersed();
                break;
            case R.id.main_disperse2:
                Toast.makeText(this, "链接", Toast.LENGTH_SHORT).show();
                showDispersed();
                break;
            case R.id.main_disperse3:
                Toast.makeText(this, "首页", Toast.LENGTH_SHORT).show();
                showDispersed();
                break;
            case R.id.main_disperse4:
                Toast.makeText(this, "我的", Toast.LENGTH_SHORT).show();
                showDispersed();
                break;
        }
    }

    //提示关闭程序
    private void confirmFinishApp(View view) {
        Snackbar.make(view, "你想退出程序吗？", Snackbar.LENGTH_LONG)
                .setAction("是的", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.finish();
                    }
                }).show();

//        boolean b = moveTaskToBack(false);//程序移到后台
//        L.e("moveTask=="+b);
    }

    @Override
    protected void onDestroy() {
        bindService(false);
        super.onDestroy();
    }

    /**
     * true绑定 false解绑服务
     */
    private void bindService(boolean bind) {
        if (bind) {
            Intent intent = new Intent(this, MainService.class);
            startService(intent);
        } else {
            //取消闹钟
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent i = new Intent(this, MainService.class);
            PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
            alarmManager.cancel(pi);

            Intent intent = new Intent(this, MainService.class);
            stopService(intent);
        }
    }

    /**
     * 广播，用于获取服务的数据更新界面
     */
    class RefreshReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction()!=null&&intent.getAction().equals(Constants.LOCATION_ACTION)){
//                String latitude = intent.getStringExtra(Constants.LOCATION_LATITUDE);
//                String longitude = intent.getStringExtra(Constants.LOCATION_LONGITUDE);
//            }else {
                Bundle bundle = intent.getExtras();
                refresh(bundle.getString("message"));
//            }
        }
    }

    private void refresh(String content) {
        show.setText(content);
    }

    /**
     * 注册广播
     */
    private void startBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.SERVICE_ACTION);//广播名
        receiver = new RefreshReceiver();
        registerReceiver(receiver, intentFilter);
    }

    /**
     * 取消广播
     */
    private void stopBroadcast() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    /**
     * 展示分散式菜单
     */
    private void showDispersed() {
        //设置动画时间
        int duration = 500;
        //动画距离,屏幕宽度的60%
        float distance = ScreenUtil.getScreenWidth(this) * 0.4f;
        //相邻ImageView运动角度式22.5度
        float angle1 = (float) (144 * Math.PI / 180);
        float angle2 = (float) (108 * Math.PI / 180);
        float angle3 = (float) (72 * Math.PI / 180);
        float angle4 = (float) (36 * Math.PI / 180);

        ObjectAnimator animator0, animator00, animator11, animator12, animator21, animator22, animator31, animator32, animator41, animator42;
        if (disperse1.getVisibility() == View.GONE) {
            animator0 = ObjectAnimator.ofFloat(dispersed, "alpha", 1.0f, 0.5f);
            animator00 = ObjectAnimator.ofFloat(dispersed, "rotation", 0f, 90f);
            disperse1.setVisibility(View.VISIBLE);
            disperse2.setVisibility(View.VISIBLE);
            disperse3.setVisibility(View.VISIBLE);
            disperse4.setVisibility(View.VISIBLE);
            animator0.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimation = false;
                }
            });
            animator11 = ObjectAnimator.ofFloat(disperse1, "translationX", (float) (distance * Math.cos(angle1)));
            animator12 = ObjectAnimator.ofFloat(disperse1, "translationY", -(float) (distance * Math.sin(angle1)));
            animator21 = ObjectAnimator.ofFloat(disperse2, "translationX", (float) (distance * Math.cos(angle2)));
            animator22 = ObjectAnimator.ofFloat(disperse2, "translationY", -(float) (distance * Math.sin(angle2)));
            animator31 = ObjectAnimator.ofFloat(disperse3, "translationX", (float) (distance * Math.cos(angle3)));
            animator32 = ObjectAnimator.ofFloat(disperse3, "translationY", -(float) (distance * Math.sin(angle3)));
            animator41 = ObjectAnimator.ofFloat(disperse4, "translationX", (float) (distance * Math.cos(angle4)));
            animator42 = ObjectAnimator.ofFloat(disperse4, "translationY", -(float) (distance * Math.sin(angle4)));
        } else {
            animator0 = ObjectAnimator.ofFloat(dispersed, "alpha", 0.5f, 1.0f);
            animator00 = ObjectAnimator.ofFloat(dispersed, "rotation", 90f, 0f);
            animator0.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    disperse1.setVisibility(View.GONE);
                    disperse2.setVisibility(View.GONE);
                    disperse3.setVisibility(View.GONE);
                    disperse4.setVisibility(View.GONE);
                    isAnimation = false;
                }
            });
            animator11 = ObjectAnimator.ofFloat(disperse1, "translationX", (float) (distance * Math.cos(angle1)), 0);
            animator12 = ObjectAnimator.ofFloat(disperse1, "translationY", -(float) (distance * Math.sin(angle1)), 0);
            animator21 = ObjectAnimator.ofFloat(disperse2, "translationX", (float) (distance * Math.cos(angle2)), 0);
            animator22 = ObjectAnimator.ofFloat(disperse2, "translationY", -(float) (distance * Math.sin(angle2)), 0);
            animator31 = ObjectAnimator.ofFloat(disperse3, "translationX", (float) (distance * Math.cos(angle3)), 0);
            animator32 = ObjectAnimator.ofFloat(disperse3, "translationY", -(float) (distance * Math.sin(angle3)), 0);
            animator41 = ObjectAnimator.ofFloat(disperse4, "translationX", (float) (distance * Math.cos(angle4)), 0);
            animator42 = ObjectAnimator.ofFloat(disperse4, "translationY", -(float) (distance * Math.sin(angle4)), 0);
        }
        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        //new BounceInterpolator()
        set.setInterpolator(new OvershootInterpolator());//动画效果
        set.playTogether(animator0, animator00, animator11, animator12, animator21, animator22, animator31, animator32, animator41, animator42);
        set.start();
        isAnimation = true;
    }

    //添加动态view
    private void addViewInRl() {
        int wRl = rl.getWidth();
        int hRl = rl.getHeight();
        int vRl = wRl / 24;
        int start = 2, end = 5;
        TextView tv = new TextView(this);
        tv.setLayoutParams(new RelativeLayout.LayoutParams((end - start) * vRl, hRl - 30));
        tv.setBackgroundColor(Color.parseColor("#3beff4"));
        tv.setPadding(0, 0, 0, 0);
        rl.addView(tv);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        TextView tv1 = (TextView) findViewById(R.id.main_rl_tv1);
        L.e("总宽" + wRl + ",数字宽：" + tv1.getHeight());
        params.setMargins(start * vRl + tv1.getHeight() / 2, 0, 0, 0);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
    }

    /**
     * 打开GPS设置
     */
    private void initGPS() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("为方便您的使用，请先打开GPS");
        dialog.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 跳转到手机设置界面，用户设置GPS
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1);
            }
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //gps打开返回结果
            L.e("我先");
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    textHeader.setText(Constants.loginUser.getNickname());
                    Glide.with(this).load(Constants.loginUser.getHeadPortrait()).error(R.mipmap.ic_header).into(imageHeader);
                    break;
                case 1002:
                    ArrayList<String> paths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    if (paths != null && paths.size() > 0) {
                        Glide.with(MainActivity.this)
                                .load(new File(paths.get(0)))
                                .placeholder(R.mipmap.ic_header)
                                .error(R.mipmap.ic_header)
                                .centerCrop()
                                .crossFade()
                                .into(imageHeader);
                    }
                    break;
            }
        }
    }

}
