package com.cxl.life.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cxl.life.bean.LinePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cxl on 2017/7/25.
 * 定时绘制点线
 */

public class LineView extends View {
    private Paint paint1, paint2;//画笔
    private float oldx = 0f, oldy = 0f, curx = 0f, cury = 0f;
    private List<LinePoint> list;//点的集合
    private Timer timer;//计时去获取点
    private TimerTask task;
    private OnResultListener listener;

    public LineView(Context context) {
        super(context);
        init();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint1 = new Paint();
        paint1.setAntiAlias(true);  //消除锯齿
        paint1.setStrokeWidth(8.0f);   //设置空心线宽
        paint1.setStyle(Paint.Style.STROKE);  //描边
        paint1.setColor(Color.RED);

        paint2 = new Paint();
        paint2.setAntiAlias(true);  //消除锯齿
        paint2.setStrokeWidth(5.0f);   //设置空心线宽
        paint2.setStyle(Paint.Style.FILL);  //填充
//        paint2.setColor(Color.GREEN);
//        paint2.setAlpha(100);
        paint2.setARGB(64, 0, 255, 0);

        list = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆：参数1、2：圆心的坐标     参数3：半径
//        canvas.drawCircle(oldx, oldy, 20, mPaint);
        //绘制矩形 参数1、2：起点的坐标 参数3、4：结点的坐标
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(100, 100, 200, 300, mPaint);
        // 画矩形
//        RectF r = new RectF(150, 500, 270, 600);
//        canvas.drawRect(r, mPaint);
        //绘制直线
//        canvas.drawLine(50, 50, 450, 50, mPaint);
        // 画圆
//        canvas.drawCircle(50, 500, 50, mPaint);
        // 画椭圆
//        RectF oval = new RectF(350, 500, 450, 700);
//        canvas.drawOval(oval, mPaint);
        // 画圆角矩形
//        RectF rect = new RectF(100, 700, 170, 800);
//        canvas.drawRoundRect(rect, 30, 20, mPaint);
        //绘制圆弧 绘制弧形
//        mPaint.setStyle(Paint.Style.FILL);//区域封闭
//        mPaint.setColor(Color.RED);
//        RectF re1 = new RectF(1000, 50, 1400, 200);
//        canvas.drawArc(re1, 10, 270, false, mPaint);
//        RectF re2 = new RectF(1000, 300, 1400, 500);
//        canvas.drawArc(re2, 10, 270, true, mPaint);
        //设置Path路径
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.GREEN);
//        mPaint.setStrokeWidth(3);
//        Path path = new Path();
//        path.moveTo(500, 100);
//        path.lineTo(920, 80);
//        path.lineTo(720, 200);
//        path.lineTo(600, 400);
//        path.close();
//        canvas.drawPath(path, mPaint);
        //绘制文字
//        mPaint.setTextSize(46);
//        canvas.drawTextOnPath("7qiuwoeruowoqjifasdkfjksjfiojio23ur8950", path, -20, -20, mPaint);

//        if (curx == 0 && cury == 0) {
//            //画点：参数1、2：点的坐标
//            canvas.drawPoint(oldx, oldy, paint1);
//        } else {
//            //画线 参数1、2：起点的坐标 参数3、4：结点的坐标
//            canvas.drawLine(oldx, oldy, curx, cury, paint2);
//            canvas.drawPoint(curx, cury, paint1);
//            curx = 0;
//            cury = 0;
//        }
        Path path = new Path();
        for (int i = 0; i < list.size(); i++) {
            LinePoint pl = list.get(i);
            if (i == 0) {
                path.moveTo(pl.getX(), pl.getY());
            } else {
                path.lineTo(pl.getX(), pl.getY());
                LinePoint plOld = list.get(i - 1);
                canvas.drawLine(plOld.getX(), plOld.getY(), pl.getX(), pl.getY(), paint1);
            }
        }
        path.close();
        canvas.drawPath(path, paint2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                curx = event.getX();
                cury = event.getY();
                oldx = curx;
                oldy = cury;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        double d = Math.sqrt((curx - oldx) * (curx - oldx) + (cury - oldy) * (cury - oldy));
                        if (d > 20) {
                            oldx = curx;
                            oldy = cury;
                            LinePoint lp = new LinePoint(oldx, oldy);
                            list.add(lp);
                        }
                    }
                };
                timer = new Timer();
                timer.schedule(task, 0, 100);
                LinePoint lp = new LinePoint(oldx, oldy);
                list.add(lp);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                curx = event.getX();
                cury = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                curx = event.getX();
                cury = event.getY();
                LinePoint lp = new LinePoint(curx, cury);
                list.add(lp);
                timer.cancel();
                task.cancel();
                this.setVisibility(View.GONE);
                listener.resultXYList(list);
                list.clear();
                break;
            }
        }
        invalidate();
        return true;
    }

    public void setOnResultListener(OnResultListener listener) {
        this.listener = listener;
    }

    public interface OnResultListener{
        void resultXYList(List<LinePoint> list);
    }

}
