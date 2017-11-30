package com.cxl.life.app.layout.animal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static com.cxl.life.util.ScreenUtil.dpToPixel;

/**
 * 做一个点的自定义移动
 */
public class Practice11OfObjectView extends View {
    public static final float RADIUS = dpToPixel(20);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    PointF position = new PointF();

    public Practice11OfObjectView(Context context) {
        super(context);
    }

    public Practice11OfObjectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11OfObjectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setColor(Color.parseColor("#FF4081"));
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        if (position != null) {
            this.position.set(position);
            invalidate();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float innerPaddingLeft = RADIUS * 1;
        float innterPaddingRight = RADIUS * 1;
        float innterPaddingTop = RADIUS * 1;
        float innterPaddingBottom = RADIUS * 3;
        float width = getWidth() - innerPaddingLeft - innterPaddingRight - RADIUS * 2;//实际移动距离，也就是圆中心的移动
        float height = getHeight() - innterPaddingTop - innterPaddingBottom - RADIUS * 2;
        //position.x  这个x从0-1，也就是移动总距离的一个百分比。
        canvas.drawCircle(innerPaddingLeft + RADIUS + width * position.x, innterPaddingTop + RADIUS + height * position.y, RADIUS, paint);
    }
}
