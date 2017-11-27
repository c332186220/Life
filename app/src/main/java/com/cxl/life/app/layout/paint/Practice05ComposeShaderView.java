package com.cxl.life.app.layout.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

/**
 * 混合着色
 */
public class Practice05ComposeShaderView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice05ComposeShaderView(Context context) {
        super(context);
    }

    public Practice05ComposeShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice05ComposeShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setLayerType(LAYER_TYPE_SOFTWARE, null); // 硬件加速下 ComposeShader 不能使用两个同类型的 Shader

        // 用 Paint.setShader(shader) 设置一个 ComposeShader
        // Shader 1: BitmapShader 图片：R.drawable.batman
        // Shader 2: BitmapShader 图片：R.drawable.batman_logo

//        构造方法：ComposeShader(Shader shaderA, Shader shaderB, PorterDuff.Mode mode)
//        参数：
//        shaderA, shaderB：两个相继使用的 Shader
//        mode: 两个 Shader 的叠加模式，即 shaderA 和 shaderB 应该怎样共同绘制。它的类型是 PorterDuff.Mode 。
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.paint_princess);
        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.paint_princess_crown);
        Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //SRC_OVER 1+2效果
        //DST_OUT  1-2效果
        //DST_IN  1-2反效果
        //共17效果 官网地址https://developer.android.com/reference/android/graphics/PorterDuff.Mode.html
        Shader shader = new ComposeShader(shader1,shader2, PorterDuff.Mode.DST_IN);
        paint.setShader(shader);
        canvas.drawCircle(300, 300, 200, paint);
    }
}
