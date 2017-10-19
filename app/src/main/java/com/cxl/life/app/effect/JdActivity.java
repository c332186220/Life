package com.cxl.life.app.effect;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cxl.life.R;
import com.cxl.life.adapter.TextRecyclerAdapter;
import com.cxl.life.widget.JdRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class JdActivity extends AppCompatActivity {
    /**
     * 列表
     */
    RecyclerView mRecyclerView;
    /**
     * 布局管理器
     */
    RecyclerView.LayoutManager mManager;
    /**
     * 适配器
     */
    private TextRecyclerAdapter adapter;
    /**
     * 数据
     */
    private List<String> mDatas;
    /**
     * 下拉刷新
     */
    JdRefreshLayout mLayout;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jd);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.jd_toolbar);
        toolbar.setTitle("京东购物");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.jd_recycler_view);
        mManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManager);
        adapter = new TextRecyclerAdapter(this,mDatas);
        mRecyclerView.setAdapter(adapter);

        mLayout = (JdRefreshLayout) findViewById(R.id.jd_recycler_view_frame);
        mLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                doSth();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }
    //初始数据
    private void initData(){
        mDatas=new ArrayList<>();
        num = 9;
        for(int i=1;i<num;i++){
            mDatas.add("速度与激情"+i);
        }
    }

    /**
     * 异步刷新数据
     */
    private void doSth() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mDatas.add("速度与激情"+num);
                num++;
                adapter.notifyDataSetChanged();
                mLayout.refreshComplete();
            }
        }.execute();
    }
}
