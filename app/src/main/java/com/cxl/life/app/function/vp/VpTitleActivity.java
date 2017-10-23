package com.cxl.life.app.function.vp;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cxl.life.R;
import com.cxl.life.adapter.PageFragmentAdapter;
import com.cxl.life.app.BaseTitleActivity;
import com.cxl.life.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 跟随viewpager滑动的标题
 */
public class VpTitleActivity extends BaseTitleActivity {
    private HorizontalScrollView hsChannel;//水平滑动
    private RadioGroup rgChannel;
    private ViewPager viewPager;
    private String[] channelList = {"标题一", "标题二", "标题三", "标题四", "标题五", "标题六", "标题七", "标题八", "标题九", "标题十"};

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_vp_title);
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "viewpager标题", true);

        hsChannel = (HorizontalScrollView) findViewById(R.id.vp_title_hs);
        viewPager = (ViewPager) findViewById(R.id.vp_title_vp);
        rgChannel = (RadioGroup) findViewById(R.id.vp_title_rg);
        rgChannel
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        viewPager.setCurrentItem(checkedId);
                    }
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initTab();
        initViewPager();
    }

    // 向RadioGroup动态添加RadioButton
    private void initTab() {
        for (int i = 0; i < channelList.length; i++) {
            RadioButton rb = (RadioButton) LayoutInflater.from(this)
                    .inflate(R.layout.vp_title_tab, null);
            rb.setId(i);
            rb.setText(channelList[i]);
            if (i == 0) {
                rb.setChecked(true);
            }
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ScreenUtil.dpTopx(
                    this, 100), ScreenUtil.dpTopx(this, 40));
            rgChannel.addView(rb, params);
        }
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < channelList.length; i++) {
            VpFragment vpFragment = new VpFragment();
            fragmentList.add(vpFragment);
        }
        //在fragment里面用getChildFragmentManager()
        PageFragmentAdapter adapter = new PageFragmentAdapter(getSupportFragmentManager(),
                fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);// 指定加载的页数
    }

    /**
     * 滑动ViewPager时调整ScroollView的位置以便显示按钮
     */
    private void setTab(int idx) {
        RadioButton rb = (RadioButton) rgChannel.getChildAt(idx);
        rb.setChecked(true);
        int left = rb.getLeft();
        int width = rb.getMeasuredWidth();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int len = left + width / 2 - screenWidth / 2;
        hsChannel.smoothScrollTo(len, 0);
    }

}
