package com.cxl.life.app.layout.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

/**
 * 绘制圆角矩形
 */
public class Practice7DrawRoundRectView extends View {

    Paint paint = new Paint();

    public Practice7DrawRoundRectView(Context context) {
        super(context);
    }

    public Practice7DrawRoundRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice7DrawRoundRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawRoundRect() 方法画圆角矩形
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        //左 上 右 下边距  横向圆角半径  纵向圆角半径  画笔
        canvas.drawRoundRect(100, 100, 500, 300, 50, 50, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawRoundRect(100, 400, 500, 600, 50, 50, paint);
    }
}
