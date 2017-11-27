package com.cxl.life.app.layout.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 文字间距
 */
public class Practice11GetFontSpacingView extends View {
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text = "Hello HenCoder";
    String text1 = "三个月内你胖了";
    String text2 = "4.5";
    String text3 = "公斤";
    String text4 = "A";
    String text5 = "a";
    String text6 = "J";
    String text7 = "j";
    String text8 = "Â";
    String text9 = "â";
    String[] texts = {"A", "a", "J", "j", "Â", "â"};
    int top = 400;
    int bottom = 600;

    public Practice11GetFontSpacingView(Context context) {
        super(context);
    }

    public Practice11GetFontSpacingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11GetFontSpacingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint1.setTextSize(60);
        paint2.setTextSize(80);
        paint2.setColor(Color.parseColor("#FF4081"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.getFontSpacing() 来获取推荐的行距

        canvas.drawText(text, 50, 100, paint1);

        float spacing = paint1.getFontSpacing();
        canvas.drawText(text, 50, 100 + spacing, paint1);

        canvas.drawText(text, 50, 100 + spacing * 2, paint1);


        // 使用 Paint.measureText 测量出文字宽度，让文字可以相邻绘制

        canvas.drawText(text1, 50, 330, paint1);
        float textWidth1 = paint1.measureText(text1);
        canvas.drawText(text2, 50 + textWidth1, 330, paint2);
        float textWidth2 = paint2.measureText(text2);
        canvas.drawText(text3, 50 + textWidth1 + textWidth2, 330, paint1);

        paint1.reset();
        paint2.reset();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20);
        paint1.setColor(Color.parseColor("#FF4081"));
        paint2.setTextSize(160);
        canvas.drawRect(50, top, getWidth() - 50, bottom, paint1);

        // 使用 Paint.getTextBounds() 计算出文字的显示区域
        // 然后计算出文字的绘制位置，从而让文字上下居中
        // 这种居中算法的优点是，可以让文字精准地居中，分毫不差
        int middle;
        Rect bounds = new Rect();
        paint2.getTextBounds(text4,0,text4.length(),bounds);
        middle = (top+bottom)/2-(bounds.top+bounds.bottom)/2;
        canvas.drawText(text4, 100, middle, paint2);

        paint2.getTextBounds(text5,0,text5.length(),bounds);
        middle = (top+bottom)/2-(bounds.top+bounds.bottom)/2;
        canvas.drawText(text5, 200, middle, paint2);

        paint2.getTextBounds(text6,0,text6.length(),bounds);
        middle = (top+bottom)/2-(bounds.top+bounds.bottom)/2;
        canvas.drawText(text6, 300, middle, paint2);

        paint2.getTextBounds(text7,0,text7.length(),bounds);
        middle = (top+bottom)/2-(bounds.top+bounds.bottom)/2;
        canvas.drawText(text7, 400, middle, paint2);

        paint2.getTextBounds(text8,0,text8.length(),bounds);
        middle = (top+bottom)/2-(bounds.top+bounds.bottom)/2;
        canvas.drawText(text8, 500, middle, paint2);

        paint2.getTextBounds(text9,0,text9.length(),bounds);
        middle = (top+bottom)/2-(bounds.top+bounds.bottom)/2;
        canvas.drawText(text9, 600, middle, paint2);

        top+=300;
        bottom+=300;
        canvas.drawRect(50, top, getWidth() - 50, bottom, paint1);
        // 使用 Paint.getFontMetrics() 计算出文字的显示区域
        // 然后计算出文字的绘制位置，从而让文字上下居中
        // 这种居中算法的优点是，可以让不同的文字的 baseline 对齐
        Paint.FontMetrics fontMetrics = paint2.getFontMetrics();
        middle = (int) ((top+bottom)/2-(fontMetrics.top+fontMetrics.bottom)/2);
        canvas.drawText(texts[0], 100, middle, paint2);
        canvas.drawText(texts[1], 200, middle, paint2);
        canvas.drawText(texts[2], 300, middle, paint2);
        canvas.drawText(texts[3], 400, middle, paint2);
        canvas.drawText(texts[4], 500, middle, paint2);
        canvas.drawText(texts[5], 600, middle, paint2);
    }
}
