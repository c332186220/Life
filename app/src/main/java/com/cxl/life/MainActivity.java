package com.cxl.life;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.life.Util.ScreenUtil;
import com.cxl.life.app.JournalActivity;
import com.cxl.life.app.drift.DriftActivity;
import com.cxl.life.app.king.KingGloryActivity;
import com.cxl.life.app.SettingActivity;
import com.cxl.life.app.voice.VoiceRecordActivity;
import com.cxl.life.app.wechat.WeChatActivity;
import com.cxl.life.login.LoginActivity;
import com.cxl.life.service.MainService;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout drawer;//抽屉
    private TextView show;//展示一些更新信息
    private ImageView dispersed, disperse1, disperse2, disperse3, disperse4;//分散式菜单

    private RefreshReceiver receiver;//通知性广播
    private boolean isAnimation = false;

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
        ImageView imageHeader = (ImageView) navHeaderView.findViewById(R.id.nav_image_header);
        TextView textHeader = (TextView) navHeaderView.findViewById(R.id.nav_name_header);
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
    }

    //监听抽屉里的菜单按钮
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu1:
                startActivity(new Intent(this, KingGloryActivity.class));
                break;
            case R.id.nav_menu2:
                startActivity(new Intent(this, VoiceRecordActivity.class));
                break;
            case R.id.nav_menu3:
                startActivity(new Intent(this, JournalActivity.class));
                break;
            case R.id.nav_menu4:
                startActivity(new Intent(this, DriftActivity.class));
                break;
            case R.id.nav_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
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
            case R.id.nav_name_header:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.main_dispersed:
                showDispersed();
                break;
            case R.id.main_disperse1:
                Toast.makeText(this, "电话", Toast.LENGTH_SHORT).show();
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
            Bundle bundle = intent.getExtras();
            refresh(bundle.getString("message"));
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
        intentFilter.addAction("com.cxl.life.REFRESH_CONTENT");
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
        int duration = 1000;
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
}
