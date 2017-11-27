package com.cxl.life.app.layout.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

/**
 * 混合计算
 */
public class Practice08XfermodeView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap1;
    Bitmap bitmap2;

    public Practice08XfermodeView(Context context) {
        super(context);
    }

    public Practice08XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice08XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.paint_princess);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.paint_princess_crown);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        // 使用 paint.setXfermode() 设置不同的结合绘制效果
        /**
         * SRC ==>  源图像,logo
         * 目标图像,batman
         * 和ComposeShader的区别应该是
         * ComposeShader是先绘制出2个bitmap,然后再确定PorterDuff来确定叠加关系
         * Xfermode是先绘制一个bitmap,然后通过离屏缓存off-screen buffer,通过设置的Xfermode来确定叠加关系后,再将另外一个bitmap绘制在离屏缓存上,最后绘制在View上
         */

        // 别忘了用 canvas.saveLayer() 开启 off-screen buffer
        int save = canvas.saveLayer(null, paint, Canvas.ALL_SAVE_FLAG);
        Xfermode xfermode1 = new PorterDuffXfermode(PorterDuff.Mode.SRC);
        canvas.drawBitmap(bitmap1, 0, 0, paint);
        // 第一个：PorterDuff.Mode.SRC
        paint.setXfermode(xfermode1);
        canvas.drawBitmap(bitmap2, 0, 0, paint);
        paint.setXfermode(null);

        Xfermode xfermode2 = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        canvas.drawBitmap(bitmap1, bitmap1.getWidth() + 100, 0, paint);
        // 第二个：PorterDuff.Mode.DST_IN
        paint.setXfermode(xfermode2);
        canvas.drawBitmap(bitmap2, bitmap1.getWidth() + 100, 0, paint);
        paint.setXfermode(null);

        Xfermode xfermode3 = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        canvas.drawBitmap(bitmap1, 0, bitmap1.getHeight() + 100, paint);
        // 第三个：PorterDuff.Mode.DST_OUT
        paint.setXfermode(xfermode3);
        canvas.drawBitmap(bitmap2, 0, bitmap1.getHeight() + 100, paint);
        paint.setXfermode(null);

        // 用完之后使用 canvas.restore() 恢复 off-screen buffer
        canvas.restoreToCount(save);
    }
}
