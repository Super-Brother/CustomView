package com.wenchao.customview.explosion;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author wenchao
 * @date 2019/7/4.
 * @time 17:38
 * description：
 */
public abstract class Particle {

    float cx;
    float cy;
    int color;

    public Particle(float cx, float cy, int color) {
        this.cx = cx;
        this.cy = cy;
        this.color = color;
    }

    /**
     * 计算
     */
    protected abstract void calculate(float factor);

    /**
     * 绘制
     *
     * @param canvas
     * @param paint
     */
    protected abstract void draw(Canvas canvas, Paint paint);

    /**
     * 逐步绘制
     *
     * @param canvas
     * @param paint
     * @param factor
     */
    public void advance(Canvas canvas, Paint paint, float factor) {
        calculate(factor);
        draw(canvas, paint);
    }
}
