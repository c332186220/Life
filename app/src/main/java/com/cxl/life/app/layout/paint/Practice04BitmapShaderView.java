package com.cxl.life.app.layout.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

/**
 * Bitmap着色
 */
public class Practice04BitmapShaderView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice04BitmapShaderView(Context context) {
        super(context);
    }

    public Practice04BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice04BitmapShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        // 用 Paint.setShader(shader) 设置一个 BitmapShader
        // Bitmap: R.drawable.batman

//        构造方法：
//        BitmapShader(Bitmap bitmap, Shader.TileMode tileX, Shader.TileMode tileY)
//        参数：
//        bitmap：用来做模板的 Bitmap 对象
//        tileX：横向的 TileMode
//        tileY：纵向的 TileMode。
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.paint_princess);
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(300, 300, 200, paint);
    }
}
