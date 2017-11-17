package com.cxl.life.app.layout.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

/**
 * 绘制路径---爱心
 */
public class Practice9DrawPathView extends View {

    Paint paint = new Paint();
    Path path = new Path(); // 初始化 Path 对象
    Path path1 = new Path(); // 初始化 Path 对象

    {
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);

        path1.addArc(200, 600, 400, 800, -225, 225);
        path1.arcTo(400, 600, 600, 800, -180, 225, false);
        path1.lineTo(400, 942);
        path1.close();//闭合
    }

    public Practice9DrawPathView(Context context) {
        super(context);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawPath() 方法画心形
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawPath(path1, paint); // 绘制出 path 描述的图形（心形），大功告成
    }
}
