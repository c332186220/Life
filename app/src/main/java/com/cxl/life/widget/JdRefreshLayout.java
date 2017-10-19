package com.cxl.life.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by cxl on 2017/9/20.
 * 京东下拉更新
 */

public class JdRefreshLayout extends PtrFrameLayout {
    /**
     * 头布局
     */
    JdRefreshHeader mHeaderView;

    public JdRefreshLayout(Context context) {
        super(context);
        initView();
    }

    public JdRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public JdRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mHeaderView = new JdRefreshHeader(getContext());
        setHeaderView(mHeaderView);
        addPtrUIHandler(mHeaderView);
    }
}
