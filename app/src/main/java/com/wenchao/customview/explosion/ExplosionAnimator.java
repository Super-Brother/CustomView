package com.wenchao.customview.explosion;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * @author wenchao
 * @date 2019/7/4.
 * @time 17:50
 * description：
 */
public class ExplosionAnimator extends ValueAnimator {

    public static int default_duration = 1500;
    private Particle[][] mParticles;
    private ParticleFactory mParticleFactory;
    private View mContainer;
    private Paint mPaint;

    public ExplosionAnimator(View view, Bitmap bitmap, Rect rect, ParticleFactory particleFactory) {
        this.mContainer = view;
        this.mParticleFactory = particleFactory;
        this.mPaint = new Paint();
        setFloatValues(0.0f, 1.0f);
        setDuration(default_duration);
        mParticles = mParticleFactory.generateParticles(bitmap, rect);
    }

    public void draw(Canvas canvas) {
        if (!isStarted()) {
            //动画结束停止
            return;
        }
        //所有粒子开始运动
        for (Particle[] mParticle : mParticles) {
            for (Particle particle : mParticle) {
                particle.advance(canvas, mPaint, (Float) getAnimatedValue());
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
