package com.wenchao.customview.explosion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author wenchao
 * @date 2019/7/4.
 * @time 18:04
 * description：
 */
public class FallingParticle extends Particle {

    float radis = FallingParticleFactory.PART_WH;
    float alpha = 1.0f;
    Rect mBound;

    public FallingParticle(float cx, float cy, int color, Rect bound) {
        super(cx, cy, color);
        mBound = bound;
    }

    @Override
    protected void calculate(float factor) {
        cx = cx + factor * Utils.RANDOM.nextInt(mBound.width()) * (Utils.RANDOM.nextFloat() - 0.5f);
        cy = cy + factor * Utils.RANDOM.nextInt(mBound.height() / 2);

        radis = radis - factor * Utils.RANDOM.nextInt(2);
        alpha = (1f - factor) * (1 + Utils.RANDOM.nextFloat());
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setColor((int) (Color.alpha(color) * alpha));
        canvas.drawCircle(cx, cy, radis, paint);
    }
}
