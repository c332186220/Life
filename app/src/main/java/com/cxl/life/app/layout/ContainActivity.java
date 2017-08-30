package com.cxl.life.app.layout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.cxl.life.R;
import com.cxl.life.util.TestUtil;

/**
 * 加载一些布局写成的fragment
 */
public class ContainActivity extends AppCompatActivity {
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contain);

        initView();
    }

    private void initView() {
        String title = getIntent().getStringExtra("from");

        Toolbar toolbar = (Toolbar) findViewById(R.id.contain_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (TestUtil.layoutTitle.get(title)) {
            case "1":

                break;
            case "2":
                fragment = new KeyboardFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideSoftKeyboard();
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            View view = getWindow().peekDecorView();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
