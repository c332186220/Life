package com.cxl.life.app.layout.graph;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxl.life.R;

/**
 *利用矩阵对画布做变换
 * 把 Matrix 应用到 Canvas 有两个方法： Canvas.setMatrix(matrix) 和 Canvas.concat(matrix)。
 Canvas.setMatrix(matrix)：用 Matrix 直接替换 Canvas 当前的变换矩阵，即抛弃 Canvas 当前的变换，改用 Matrix 的变换（注：根据下面评论里以及我在微信公众号中收到的反馈，不同的系统中  setMatrix(matrix) 的行为可能不一致，所以还是尽量用 concat(matrix) 吧）；
 Canvas.concat(matrix)：用 Canvas 当前的变换矩阵和 Matrix 相乘，即基于 Canvas 当前的变换，叠加上  Matrix 中的变换。
 */
public class Practice07MatrixTranslateView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Matrix matrix = new Matrix();
    Point point1 = new Point(200, 200);
    Point point2 = new Point(800, 200);
    Point point3 = new Point(200, 800);
    Point point4 = new Point(800, 800);

    public Practice07MatrixTranslateView(Context context) {
        super(context);
    }

    public Practice07MatrixTranslateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice07MatrixTranslateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.maps);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //移动
        canvas.save();
        matrix.postTranslate(100,0);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        matrix.reset();
        matrix.postScale(0.6f,1.6f,point2.x+width/2,point2.y+height/2);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();

        canvas.save();
        matrix.reset();
        matrix.postRotate(45,point3.x,point3.y);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point3.x, point3.y, paint);
        canvas.restore();

        canvas.save();
        matrix.reset();
        matrix.postSkew(0,0.3f,point4.x + width / 2, point4.y + height / 2);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point4.x, point4.y, paint);
        canvas.restore();
    }
}
