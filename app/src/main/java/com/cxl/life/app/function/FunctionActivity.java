package com.cxl.life.app.function;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cxl.life.R;
import com.cxl.life.adapter.MenuAdapter;
import com.cxl.life.app.function.custom.CustomActivity;
import com.cxl.life.app.function.download.DownloadActivity;
import com.cxl.life.app.function.location.LocationActivity;
import com.cxl.life.app.function.permission.PermissionActivity;
import com.cxl.life.app.function.popup.PopupWindowActivity;
import com.cxl.life.app.function.vp.VpTitleActivity;
import com.cxl.life.app.voice.VoiceRecordActivity;
import com.cxl.life.bean.function.MenuData;

import java.util.ArrayList;
import java.util.List;

public class FunctionActivity extends AppCompatActivity {
    private GridView menuGv;//模块
    private List<MenuData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.layout_toolbar);
        toolbar.setTitle(R.string.activity_function);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        menuGv = (GridView) findViewById(R.id.function_menu);
        list = new ArrayList<>();
        list.add(new MenuData(R.mipmap.function_record, "语音录制", "1"));
        list.add(new MenuData(R.mipmap.function_step, "记步", "2"));
        list.add(new MenuData(R.mipmap.function_popup, "弹框", "3"));
        list.add(new MenuData(R.mipmap.function_vp_title, "滑动切换标题", "4"));
        list.add(new MenuData(R.mipmap.function_download, "多线程下载", "5"));
        list.add(new MenuData(R.mipmap.function_permission, "权限管理", "6"));
        list.add(new MenuData(R.mipmap.function_fill_blank, "填空题", "7"));
        list.add(new MenuData(R.mipmap.function_custom, "方图", "8"));
        list.add(new MenuData(R.mipmap.function_location, "原生定位", "9"));
        list.add(new MenuData(R.mipmap.function_video, "播放视频", "10"));

        MenuAdapter adapter = new MenuAdapter(this, list);
        menuGv.setAdapter(adapter);
        menuGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View l, int position, long id) {
                switch (list.get(position).getEvent()) {
                    case "1"://语音录制
                        startActivity(new Intent(FunctionActivity.this, VoiceRecordActivity.class));
                        break;
                    case "2"://记步
//                        startActivity(new Intent(FunctionActivity.this, VoiceRecordActivity.class));
                        break;
                    case "3"://弹框
                        startActivity(new Intent(FunctionActivity.this, PopupWindowActivity.class));
                        break;
                    case "4"://页签联动切换
                        startActivity(new Intent(FunctionActivity.this, VpTitleActivity.class));
                        break;
                    case "5"://下载
                        startActivity(new Intent(FunctionActivity.this, DownloadActivity.class));
                        break;
                    case "6"://权限
                        startActivity(new Intent(FunctionActivity.this, PermissionActivity.class));
                        break;
                    case "7"://填空
                        startActivity(new Intent(FunctionActivity.this, FillBlankActivity.class));
                        break;
                    case "8"://方图
                        startActivity(new Intent(FunctionActivity.this, CustomActivity.class));
                        break;
                    case "9"://原生定位
                        startActivity(new Intent(FunctionActivity.this, LocationActivity.class));
                        break;
                    case "10"://播放视频
                        startActivity(new Intent(FunctionActivity.this, VideoActivity.class));
                        break;
                }
            }
        });
    }
}
