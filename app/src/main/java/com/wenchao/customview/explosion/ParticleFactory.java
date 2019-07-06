package com.wenchao.customview.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * @author wenchao
 * @date 2019/7/4.
 * @time 17:47
 * description：粒子工厂
 */
public abstract class ParticleFactory {

    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);
}
