package com.cxl.life.app.layout.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 扫描渐变
 */
public class Practice03SweepGradientView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice03SweepGradientView(Context context) {
        super(context);
    }

    public Practice03SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice03SweepGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        // 用 Paint.setShader(shader) 设置一个 SweepGradient
        // SweepGradient 的参数：圆心坐标：(300, 300)；颜色：#E91E63 到 #2196F3

//        构造方法：
//        SweepGradient(float cx, float cy, int color0, int color1)
//        参数：
//        cx cy ：扫描的中心
//        color0：扫描的起始颜色
//        color1：扫描的终止颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Shader shader = new SweepGradient(300,300, Color.parseColor("#303F9F"),Color.parseColor("#FF4081"));
        paint.setShader(shader);
        canvas.drawCircle(300, 300, 200, paint);
    }
}
