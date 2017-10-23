package com.cxl.life.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.cxl.life.R;
import com.cxl.life.util.ToastUtil;

/**
 * Created by cxl on 2017/7/13.
 * 基类(单纯一个标题)
 */

public class BaseTitleActivity extends BaseActivity{
    private boolean isShowRight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
    }

    public void setContentView() {
    }

    public void initView() {
    }

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp) {
        initToolBar(toolbar, name, showHomeAsUp, false);
    }

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp, boolean isShowRight) {
        this.isShowRight = isShowRight;
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isShowRight) {
            getMenuInflater().inflate(R.menu.toolbar_right, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_icon:
                add();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void add() {
    }

    /**
     * @param str 弹出的文字
     */
    public void toast(String str) {
        ToastUtil.showToast(str);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

}
