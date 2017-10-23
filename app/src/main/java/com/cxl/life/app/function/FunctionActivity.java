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
import com.cxl.life.app.function.popup.PopupWindowActivity;
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

                }
            }
        });
    }
}
