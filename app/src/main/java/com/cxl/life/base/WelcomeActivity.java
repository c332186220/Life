package com.cxl.life.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cxl.life.MainActivity;
import com.cxl.life.R;
import com.cxl.life.util.TimeUtil;
import com.cxl.life.widget.SnowView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private SnowView snow;//雪花
    private RelativeLayout welcome;//背景
    private TextView start;//启动

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(WelcomeActivity.this, "访问失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(WelcomeActivity.this, "访问成功", Toast.LENGTH_SHORT).show();
                    initNetWork();
                    break;
                case 3:
                    start.setBackgroundResource(R.drawable.item_top_title_press);
                    start.setTextColor(ContextCompat.getColor(WelcomeActivity.this, R.color.white));
                    start.setText("点我启动");
                    start.setOnClickListener(WelcomeActivity.this);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_welcome);

//        checkNetWork();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                handler.sendEmptyMessage(3);
            }
        }, 2000);
        welcome = (RelativeLayout) findViewById(R.id.activity_welcome);
        snow = (SnowView) findViewById(R.id.welcome_snow);
        start = (TextView) findViewById(R.id.welcome_start);
        switch (TimeUtil.getCurrentMonth()) {
            case 11:
            case 12:
            case 1:
                welcome.setBackgroundResource(R.mipmap.welcome_winter_bg);
                snow.loadFlower(R.drawable.ic_flow_winter);
                break;
            case 2:
            case 3:
            case 4:
                welcome.setBackgroundResource(R.mipmap.welcome_spring_bg);
                snow.loadFlower(R.drawable.ic_flow_spring);
                break;
            case 5:
            case 6:
            case 7:
                welcome.setBackgroundResource(R.mipmap.welcome_summer_bg);
                snow.loadFlower(R.drawable.ic_flow_summer);
                break;
            case 8:
            case 9:
            case 10:
                welcome.setBackgroundResource(R.mipmap.welcome_autumn_bg);
                snow.loadFlower(R.drawable.ic_flow_autumn);
                break;
        }
    }

    /**
     * 检查网络是否可以正常工作
     */
    private void checkNetWork() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.sendEmptyMessage(2);
            }
        });
    }

    /**
     * 打开网络设置
     */
    private void initNetWork() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("检测到内网可通，是否切换到内网？");
        dialog.setPositiveButton("切换", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 完成切换工作
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                WelcomeActivity.this.finish();
            }
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcome_start:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(snow!=null){
            snow.recycle();
        }
    }
}
