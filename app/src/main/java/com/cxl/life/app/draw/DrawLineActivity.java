package com.cxl.life.app.draw;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cxl.life.R;
import com.cxl.life.bean.LinePoint;
import com.cxl.life.widget.LineView;

import java.util.List;

public class DrawLineActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView dContent;//展示点
    private FloatingActionButton fab;
    private LineView line;//画点线布局
    private FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_line);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.draw_line_toolbar);
        toolbar.setTitle(R.string.activity_draw_line);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dContent = (TextView) findViewById(R.id.draw_content);
        fab = (FloatingActionButton) findViewById(R.id.draw_line_fab);
        fab.setOnClickListener(this);
        layout = (FrameLayout) findViewById(R.id.draw_line_fl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.draw_line_fab:
                goDraw();
                break;
        }
    }
    //调出绘制界面
    private void goDraw() {
        if (line == null) {
            line = new LineView(this);
            line.setOnResultListener(new LineView.OnResultListener() {
                @Override
                public void resultXYList(List<LinePoint> list) {
                    if(list!=null && list.size()>2) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                            sb.append("X " + list.get(i).getX() + "   Y " + list.get(i).getY());
                            sb.append("\n");
                        }
                        dContent.setText(sb.toString());
                    }
                }
            });
            layout.addView(line);
        }
        line.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);
    }
}
