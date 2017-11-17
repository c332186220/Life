package com.cxl.life.app.layout.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

import java.util.Random;

/**
 * 绘制--柱状图实现
 */
public class Practice10HistogramView extends View {
    Paint paint1,paint2;
    float[] points = {100,100,100,500,100,500,800,500};//起点是100，100
    private String[] xKey = {"TOM","BK","CITY","DEAR","ERR","F","GO"};
    private int[] xValue;//随机值
    private int textSize = 30;//文字大小
    private int columnWidth = 70;//柱子宽度
    private int columnSpace = 20;//柱子间隔

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(1);
        paint1.setColor(getResources().getColor(R.color.colorPrimaryDark));
        paint1.setTextSize(textSize);
//        paint1.setTextAlign(Paint.Align.CENTER);//设置居中绘制

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeWidth(1);
        paint2.setColor(getResources().getColor(R.color.colorAccent));

        xValue = new int[xKey.length];
        Random random = new Random();
        for (int i = 0; i < xValue.length; i++) {
            xValue[i] = random.nextInt(400)+1;//随机400以内的整数作为柱子高度
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        canvas.drawColor(getResources().getColor(R.color.body_background));
        //第一步 画坐标线  两种实现方式
        canvas.drawLines(points,paint1);
//        canvas.drawLine(100,100,100,500,paint1);
//        canvas.drawLine(800,500,100,500,paint1);
        canvas.drawText("0",90,503+textSize/2,paint1);//画原点
        for (int i = 0; i < xKey.length; i++) {
            int textWidth = (int) paint1.measureText(xKey[i]);//文字宽度
            canvas.drawText(xKey[i],100+columnSpace+columnWidth/2-textWidth/2+i*(columnSpace+columnWidth),500+3+textSize/2,paint1);//画x方向坐标

            canvas.drawRect(100+columnSpace+i*(columnSpace+columnWidth),500-xValue[i],100+(columnSpace+columnWidth)*(i+1),500,paint2);//画矩形
        }

        paint1.setTextSize(55);
        int textWidth = (int) paint1.measureText("直方图");//文字宽度
        canvas.drawText("直方图",450-textWidth/2,500+100,paint1);//画描述
    }
}
