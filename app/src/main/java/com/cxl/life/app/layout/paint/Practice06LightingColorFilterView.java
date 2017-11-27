package com.cxl.life.app.layout.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

/**
 * 颜色过滤
 */
public class Practice06LightingColorFilterView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    public Practice06LightingColorFilterView(Context context) {
        super(context);
    }

    public Practice06LightingColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice06LightingColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.paint_princess);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setColorFilter() 来设置 LightingColorFilter
//        在 Paint 里设置 ColorFilter ，使用的是 Paint.setColorFilter(ColorFilter filter) 方法。 ColorFilter 并不直接使用，而是使用它的子类。它共有三个子类：LightingColorFilter PorterDuffColorFilter 和  ColorMatrixColorFilter。

        // 第一个 原图
        canvas.drawBitmap(bitmap, 0, 0, paint);

        // 第二个 LightingColorFilter：增强绿色部分
        paint.setColorFilter(new LightingColorFilter(0xFFFFFF,0x003000));
        canvas.drawBitmap(bitmap, bitmap.getWidth() + 100, 0, paint);

        // 第一个 LightingColorFilter：去掉红色部分   前一个是过滤，后一个是添加
        paint.setColorFilter(new LightingColorFilter(0x00FFFF,0x000000));
        canvas.drawBitmap(bitmap, 0, bitmap.getHeight()+100, paint);
    }
}
