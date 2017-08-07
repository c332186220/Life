package com.cxl.life.bean;

/**
 * Created by cxl on 2017/7/25.
 * 绘图的点
 */

public class LinePoint {
    private float x;//x轴坐标
    private float y;//y轴坐标

    public LinePoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
