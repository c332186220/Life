package com.cxl.life.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cxl.life.R;
import com.cxl.life.app.about.AboutUsActivity;

import java.lang.reflect.Method;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.about_app).setOnClickListener(this);
        findViewById(R.id.help_and_feedback).setOnClickListener(this);
        findViewById(R.id.setting_currency).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(SettingActivity.this, "打开", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(SettingActivity.this, "关闭", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(SettingActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_add:
                Toast.makeText(SettingActivity.this, "长按试试", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(SettingActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
            default:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 重写了一个onMenuOpened()方法,通过返回反射的方法将MenuBuilder的setOptionalIconsVisible变量设置为true
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_app:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.help_and_feedback:

                break;
            case R.id.setting_currency:
                startActivity(new Intent(this, CurrencyActivity.class));
                break;
        }
    }
}
