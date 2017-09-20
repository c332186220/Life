package com.cxl.life.widget;

import java.util.Random;

class SnowFlake {
    private static final float ANGLE_SEED = 20;//下落的角度范围
    private static final float INCREMENT_LOWER = 5f;//下落的斜角最小距离
    private static final float INCREMENT_UPPER = 9f;//下落的斜角最大距离
    private static final float FLAKE_SIZE_LOWER = 0.2f;//图的压缩大小-最小
    private static final float FLAKE_SIZE_UPPER = 1.0f;//图的压缩大小-最大

    private static final Random RANDOM = new Random();

    private int width;//布局宽
    private int height;//布局高
    public int x;//x位置
    public int y;//y位置
    public float angle;//下来角度
    public float increment;//下落位移
    public float flakeSize;//图片大小
    public float flakeScale;//图片伸缩
    public int alpha = 255;// 透明程度
    public int rotate = 0;//旋转角度

    public SnowFlake(int width, int height, int size) {
        this.width = width;
        this.height = height;
        this.x = getRandom(width);
        this.y = getRandom(height);
        this.angle = getRandom(ANGLE_SEED) * (RANDOM.nextInt(2) == 0 ? -1 : 1);
        this.increment = getRandom(INCREMENT_LOWER, INCREMENT_UPPER);
        this.flakeScale = getRandom(FLAKE_SIZE_LOWER, FLAKE_SIZE_UPPER);
        this.flakeSize = size;
        this.alpha = RANDOM.nextInt(155) + 100;
        this.rotate = RANDOM.nextInt(360);
    }

    public void move() {
        x += (int) (increment * Math.sin(angle * Math.PI / 180));
        y += (int) (increment * Math.cos(angle * Math.PI / 180));
        rotate += 1;
        //可每次微调角度
//        angle += getRandom(-ANGLE_SEED, ANGLE_SEED) / ANGLE_DIVISOR;

        if (isOutside()) {
            reset();
        }
    }

    private boolean isInside() {
        return x >= -flakeSize - 1 && x <= width && y >= -flakeSize - 1 && y < height;
    }

    //出了屏幕
    private boolean isOutside() {
        return x < -flakeSize - 1 || x > width / flakeScale || y > height / flakeScale;
    }

    //重置
    private void reset() {
        x = getRandom(width);
        y = (int) (-flakeSize - 1);
        angle = getRandom(ANGLE_SEED) * (RANDOM.nextInt(2) == 0 ? -1 : 1);
        increment = getRandom(INCREMENT_LOWER, INCREMENT_UPPER);
        flakeScale = getRandom(FLAKE_SIZE_LOWER, FLAKE_SIZE_UPPER);
        alpha = RANDOM.nextInt(155) + 100;
        this.rotate = RANDOM.nextInt(360);
    }

    private float getRandom(float lower, float upper) {
        float min = Math.min(lower, upper);
        float max = Math.max(lower, upper);
        return getRandom(max - min) + min;
    }

    private float getRandom(float upper) {
        return RANDOM.nextFloat() * upper;
    }

    private int getRandom(int upper) {
        return RANDOM.nextInt(upper);
    }

}
