package com.cxl.life.app.layout.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 文本绘制
 */
public class Practice01DrawTextView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    String text = "Hello HenCoder";
    String text1 = "Hello HenCoder  Hello HenCoder  Hello HenCoder  Hello HenCoder  Hello HenCoder  Hello HenCoder  Hello HenCoder  Hello HenCoder  Hello HenCoder  Hello HenCoder";
    String text2 = "Hello HenCoder\n Hello HenCoder\n  Hello HenCoder";

    public Practice01DrawTextView(Context context) {
        super(context);
    }

    public Practice01DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice01DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setTextSize(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 drawText() 来绘制文字
        // 文字坐标： (50, 100)
        //Canvas.drawText() 只能绘制单行的文字，而不能换行  \n会被识别为空格
        canvas.drawText(text1,10,50,paint);
        canvas.restore();//否则会被改变
        paint.setTextSize(36);//设置文字大小

        //StaticLayout 支持换行
        textPaint.setTextSize(36);
        StaticLayout staticLayout1 = new StaticLayout(text1, textPaint, 1000,
                Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true);
        StaticLayout staticLayout2 = new StaticLayout(text2, textPaint, 1000,
                Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true);
        canvas.save();
        canvas.translate(50,100);
        staticLayout1.draw(canvas);
        canvas.translate(0,200);
        staticLayout2.draw(canvas);
        canvas.restore();

        // 使用 Paint.setTypeface() 来设置不同的字体

        // 第二处：填入 Typeface.SERIF 来设置衬线字体（宋体）
        paint.setTypeface(Typeface.SERIF);
        canvas.drawText(text, 50, 400, paint);
        // 第三处：填入 typeface 对象来使用 assets 目录下的 "Satisfy-Regular.ttf" 文件
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Satisfy-Regular.ttf"));
        canvas.drawText(text, 50, 500, paint);
        // 第一处：填入 null 来设置默认字体
        paint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(text, 50, 300, paint);

        // 使用 Paint.setFakeBoldText() 来加粗文字
        paint.setFakeBoldText(true);
        canvas.drawText(text+"  加粗", 50, 600, paint);

        paint.reset();
        paint.setTextSize(48);
        // 使用 Paint.setStrikeThruText() 来设置删除线
        paint.setStrikeThruText(true);
        canvas.drawText(text+"  删除线", 50, 700, paint);

        paint.reset();
        paint.setTextSize(48);
        // 使用 Paint.setUnderlineText() 来设置下划线
        paint.setUnderlineText(true);
        canvas.drawText(text+"  下划线", 50, 800, paint);

        paint.reset();
        paint.setTextSize(48);
        // 使用 Paint.setTextSkewX() 来让文字倾斜
        paint.setTextSkewX(-0.5f);
        canvas.drawText(text, 50, 900, paint);

        paint.reset();
        paint.setTextSize(48);
        // 使用 Paint.setTextScaleX() 来改变文字宽度
        paint.setTextScaleX(1.2f);
        canvas.drawText(text+"  改变文字宽度", 50, 1000, paint);

        //使用Paint.setLetterSpacing(0.2f);  来调整字符间距

        paint.reset();
        paint.setTextSize(48);
        // 使用 Paint.setTextAlign() 来调整文字对齐方式

        // 第一处：使用 Paint.Align.LEFT
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, getWidth() / 2, 1100, paint);

        // 第二处：使用 Paint.Align.CENTER
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, getWidth() / 2, 1200, paint);

        // 第三处：使用 Paint.Align.RIGHT
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(text, getWidth() / 2, 1300, paint);

//        paint.setTextLocale(Locale.CHINA); // 简体中文
//        canvas.drawText(text, 150, 150, paint);
//        paint.setTextLocale(Locale.TAIWAN); // 繁体中文
//        canvas.drawText(text, 150, 150 + textHeight, paint);
//        paint.setTextLocale(Locale.JAPAN); // 日语
//        canvas.drawText(text, 150, 150 + textHeight * 2, paint);

    }
}
