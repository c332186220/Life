package com.cxl.life.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

/**
 * Created by cxl on 2017/9/20.
 * 雪花飞舞效果
 */

public class SnowView extends View {
    private static final int NUM_SNOWFLAKES = 150;//雪花数量
    private static final int DELAY = 5;
    Bitmap mFlowers = null;//雪花图片
    private SnowFlake[] snowflakes;//雪花集合
    public Matrix m = new Matrix();// 矩阵
    Paint paint = new Paint();

    public SnowView(Context context) {
        super(context);
    }

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 加载图片
    public void loadFlower(@DrawableRes int res) {
        mFlowers = BitmapFactory.decodeResource(this.getContext().getResources(), res);
    }


    // 释放资源
    public void recycle() {
        if (mFlowers != null && !mFlowers.isRecycled()) {
            mFlowers.recycle();
        }
    }

    //初始化
    protected void resize(int width, int height) {
        snowflakes = new SnowFlake[NUM_SNOWFLAKES];
        if (mFlowers == null) {
            loadFlower(R.drawable.ic_flow_spring);
        }
        for (int i = 0; i < NUM_SNOWFLAKES; i++) {
            snowflakes[i] = new SnowFlake(width, height, mFlowers.getWidth());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            resize(w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (SnowFlake snowFlake : snowflakes) {
            draw(snowFlake, canvas);
        }
        getHandler().postDelayed(runnable, DELAY);
    }

    //绘制
    public void draw(SnowFlake snowFlake, Canvas canvas) {
        snowFlake.move();
        m.reset();
        m.setScale(snowFlake.flakeScale, snowFlake.flakeScale);
//        m.setRotate(snowFlake.rotate);//旋转是以屏幕为基准的，效果不好，待优化
        canvas.setMatrix(m);
        paint.setAlpha(snowFlake.alpha);
        canvas.drawBitmap(mFlowers, snowFlake.x, snowFlake.y, paint);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

}
