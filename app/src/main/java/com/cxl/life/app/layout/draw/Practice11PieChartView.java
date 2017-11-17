package com.cxl.life.app.layout.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;
import com.cxl.life.bean.layout.CharData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 绘制饼状图
 */
public class Practice11PieChartView extends View {
    Paint paint;
    private String[] xKey = {"TOM", "BK", "CITY", "DEAR", "ERR", "FIRE"};
    private int[] colors = {Color.BLUE, Color.YELLOW, Color.RED, Color.DKGRAY, Color.CYAN, Color.GREEN};
    private List<CharData> list;
    private int offset = 30;
    private int circleX = 540, circleY = 540;//圆心坐标
    private int radius = 300;//半径
    int total = 0;
    private int max = 0;//取最大值

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        //初始化值
        list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < xKey.length; i++) {
            CharData cd = new CharData();
            cd.setName(xKey[i]);
            int value = random.nextInt(100) + 1;//随机1-100的数字
            cd.setNumber(value);
            total += value;
            max = Math.max(max, value);
            cd.setColor(colors[i]);
            list.add(cd);
        }

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        float startAngle = 0f; // 开始的角度
        float sweepAngle;      // 扫过的角度
        float centerAngle;//中间的角度
        float lineStartX = 0f; // 直线开始的X坐标
        float lineStartY = 0f; // 直线开始的Y坐标
        float lineEndX = 0f;        // 直线结束的X坐标
        float lineEndY = 0f;        // 直线结束的Y坐标
        float offsetX = 0f;//饼子偏移X
        float offsetY = 0f;//饼子偏移Y
        for (int i = 0; i < list.size(); i++) {
            CharData cd = list.get(i);
            sweepAngle = cd.getNumber() * 360f / total;//计算占的比例
            centerAngle = startAngle + sweepAngle / 2;
            paint.setColor(cd.getColor());
            lineStartX = circleX + radius * (float) Math.cos(centerAngle / 180 * Math.PI);
            lineStartY = circleY + radius * (float) Math.sin(centerAngle / 180 * Math.PI);
            lineEndX = circleX + (radius + offset) * (float) Math.cos(centerAngle / 180 * Math.PI);
            lineEndY = circleY + (radius + offset) * (float) Math.sin(centerAngle / 180 * Math.PI);
            if (max == cd.getNumber()) {
                offsetX = lineEndX - lineStartX;
                offsetY = lineEndY - lineStartY;
            } else {
                offsetX = 0f;
                offsetY = 0f;
            }
            //绘饼
            canvas.drawArc(circleX - radius + offsetX, circleY - radius + offsetY, circleX + radius + offsetX, circleY + radius + offsetY, startAngle, sweepAngle, true, paint); // 绘制扇形
            //绘线
            //便宜还可以通过移动画布来实现  保存原图，移动画布，绘制完成后记得重置画布
//            canvas.save();
//            canvas.translate(lineStartX * 0.1f, lineStartY * 0.1f);
//            canvas.restore();
            paint.setColor(Color.BLACK);
            canvas.drawLine(lineStartX+offsetX, lineStartY+offsetY, lineEndX+offsetX, lineEndY+offsetY, paint);
            if (centerAngle > 90 && centerAngle <= 270) {
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawLine(lineEndX + offsetX, lineEndY + offsetY, lineEndX - 60 + offsetX, lineEndY + offsetY, paint);
                canvas.drawText(cd.getName(), lineEndX - 60 - 10 + offsetX, lineEndY + 15 + offsetY, paint);
            } else {
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawLine(lineEndX + offsetX, lineEndY + offsetY, lineEndX + 60 + offsetX, lineEndY + offsetY, paint);
                canvas.drawText(cd.getName(), lineEndX + 60 + 10 + offsetX, lineEndY + 15 + offsetY, paint);
            }
            startAngle += sweepAngle;
        }
    }
}
