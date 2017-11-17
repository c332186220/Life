package com.cxl.life.app.layout.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

public class Practice2DrawCircleView extends View {

    Paint paint = new Paint();

    public Practice2DrawCircleView(Context context) {
        super(context);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawCircle() 方法画圆
//        一共四个圆：1.实心圆 2.空心圆 3.蓝色实心圆 4.线宽为 20 的空心圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        paint.setAntiAlias(true);
        //x坐标  y坐标   半径  画笔
        canvas.drawCircle(300, 300, 200, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(780, 300, 200, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        canvas.drawCircle(780, 780, 190, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawCircle(300, 780, 200, paint);

    }
}
