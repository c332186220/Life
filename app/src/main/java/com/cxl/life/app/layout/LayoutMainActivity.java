package com.cxl.life.app.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cxl.life.R;
import com.cxl.life.adapter.LayoutItemAdapter;
import com.cxl.life.app.drift.DriftActivity;
import com.cxl.life.app.face.MyItemClickListener;
import com.cxl.life.util.ScreenUtil;
import com.cxl.life.util.TestUtil;

import java.util.List;

public class LayoutMainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LayoutItemAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_main);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.layout_toolbar);
        toolbar.setTitle(R.string.activity_layout);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.layout_rlv);
        list = TestUtil.getLayoutTitle();
        int height = ScreenUtil.getScreenWidth(this) / 3 - ScreenUtil.dp2px(this, 6 * 2);
        adapter = new LayoutItemAdapter(this, list, height);
        //设置gridview的样式
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter.setMyItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toEveryPlace(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * 跳转到对应的地方
     */
    private void toEveryPlace(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, DriftActivity.class));
                break;
            case 1:
                Intent intent = new Intent(this, ContainActivity.class);
                intent.putExtra("from", list.get(position));
                startActivity(intent);
                break;
        }
    }
}
