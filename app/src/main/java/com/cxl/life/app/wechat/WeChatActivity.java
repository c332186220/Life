package com.cxl.life.app.wechat;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.cxl.life.R;
import com.cxl.life.app.BaseActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WeChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        setOverflowShowingAlways();

        initView();
    }

    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.wechat_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 加载main.xml文件
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(WeChatActivity.this, "打开", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(WeChatActivity.this, "关闭", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(WeChatActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_plus:
                Toast.makeText(WeChatActivity.this, "长按试试", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(WeChatActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
            default:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 让隐藏在overflow当中的Action按钮的图标显示出来
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {

                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 屏蔽掉物理Menu键,不然在有物理Menu键的手机上，overflow按钮会显示不出来
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
