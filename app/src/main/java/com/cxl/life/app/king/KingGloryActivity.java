package com.cxl.life.app.king;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cxl.life.R;
import com.cxl.life.Util.TestUtil;
import com.cxl.life.adapter.KingRecyclerAdapter;
import com.cxl.life.adapter.KingRecyclerFallsAdapter;
import com.cxl.life.adapter.KingRecyclerGridAdapter;
import com.cxl.life.app.BaseActivity;
import com.cxl.life.bean.KingGlory;

import java.util.List;

public class KingGloryActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private KingRecyclerAdapter adapter;
    private List<KingGlory> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_king_glory);

        initView();
        initData();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.king_glory_rlv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.king_glory_toolbar);
        toolbar.setTitle(R.string.activity_king_glory);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        list = TestUtil.getKingGlory();
        initListStyle();
        //添加与删除操作的更新方式
//        adapter.notifyItemInserted(position)与notifyItemRemoved(position)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.king_glory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_menu:
                initListStyle();
                break;
            case R.id.action_grid_menu:
                initGridStyle();
                break;
            case R.id.action_falls_menu:
                initFallsStyle();
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 列表样式
     */
    private void initListStyle() {
        adapter = new KingRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);
        //设置水平listview的样式
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加间隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        //设置动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new KingRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toBrowser(list.get(position).getDescribe());
            }
        });
    }

    /**
     * 网格样式
     */
    private void initGridStyle() {
        KingRecyclerGridAdapter adapter = new KingRecyclerGridAdapter(this, list);
        recyclerView.setAdapter(adapter);
        //设置gridview的样式
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        //设置动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 瀑布样式
     */
    private void initFallsStyle() {
        KingRecyclerFallsAdapter adapter = new KingRecyclerFallsAdapter(this, list);
        recyclerView.setAdapter(adapter);
        //设置瀑布流的样式
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));//设置水平垂直
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        //设置动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 跳转到系统浏览器，并打开网页
     */
    private void toBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }
}
