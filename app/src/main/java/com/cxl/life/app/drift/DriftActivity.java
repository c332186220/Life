package com.cxl.life.app.drift;

import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.cxl.life.R;
import com.cxl.life.adapter.TextRecyclerAdapter;
import com.cxl.life.app.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class DriftActivity extends BaseActivity {
    private LinearLayout bottom;
    private RecyclerView recyclerView;
    private GestureDetectorCompat mDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drift);

        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.king_glory_rlv);
        List<String> list = new ArrayList<>();
        for(int i=0;i<30;i++){
            list.add("文件"+i);
        }
        TextRecyclerAdapter adapter = new TextRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);
        //设置水平listview的样式
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加间隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        //问题 设置这个能解决滑动冲突，但是上滑不能隐藏底部
//        recyclerView.setNestedScrollingEnabled(false);

        bottom = (LinearLayout) findViewById(R.id.drift_bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDetectorCompat = new GestureDetectorCompat(DriftActivity.this, new MyGestureListener(bottom));
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        mDetectorCompat.onTouchEvent(event);
                        return false;
                    }
                });
            }
        }, 100);
    }
}
