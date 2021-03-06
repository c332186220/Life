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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.cxl.life.R;
import com.cxl.life.app.layout.animal.CustomAnimalFragment;
import com.cxl.life.app.layout.draw.CustomDrawFragment;
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
        TestUtil.initLayoutTitle();
        switch (TestUtil.layoutTitle.get(title)) {
            case "1":

                break;
            case "2":
                fragment = new KeyboardFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "3":
                fragment = new ChineseNameFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "4":
                fragment = new RichTextFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "5":
                fragment = new EditDataFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "6":
                fragment = new ExcelFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "7":
                fragment = new FlowLayoutFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "8":
                fragment = new ProgressFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "9":
                fragment = new HeadAndFootFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "10":
                fragment = new MailListFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "11":
                fragment = new CustomDrawFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
            case "12":
                fragment = new CustomAnimalFragment();
                transaction.replace(R.id.contain_fl, fragment);
                transaction.commit();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        hideSoftKeyboard();
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
