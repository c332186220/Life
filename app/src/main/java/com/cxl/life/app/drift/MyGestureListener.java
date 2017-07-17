package com.cxl.life.app.drift;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by cxl on 2017/7/12.
 * 滑动监听处理view的隐藏与显示
 */

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    private View view;
    private boolean mIsAnimatingOut = false;
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public MyGestureListener(View view) {
        this.view = view;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        if (Math.abs(distanceY) > Math.abs(distanceX)) {//判断是否竖直滑动
            //是否向下滑动
            boolean isScrollDown = e1.getRawY() < e2.getRawY();

            if (isScrollDown && view.getVisibility() == View.GONE) {
                //下滑上移Button
                animateIn(view);
            } else if (!isScrollDown && view.getVisibility() == View.VISIBLE) {
                //上滑下移Button
                animateOut(view);
            }
        }

        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    //退出动画
    private void animateOut(final View button) {
        button.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button).translationY(button.getHeight()).setInterpolator(INTERPOLATOR).withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        public void onAnimationStart(View view) {
                            MyGestureListener.this.mIsAnimatingOut = true;
                        }

                        public void onAnimationCancel(View view) {
                            MyGestureListener.this.mIsAnimatingOut = false;
                        }

                        public void onAnimationEnd(View view) {
                            MyGestureListener.this.mIsAnimatingOut = false;
                            view.setVisibility(View.GONE);
                        }
                    }).start();
        } else {

        }
    }

    // 进入动画
    private void animateIn(View button) {
        button.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button).translationY(0)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                    .start();
        } else {

        }
    }
}
