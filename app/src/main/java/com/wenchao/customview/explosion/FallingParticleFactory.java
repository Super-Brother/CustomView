package com.wenchao.customview.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * @author wenchao
 * @date 2019/7/4.
 * @time 18:05
 * description：
 */
public class FallingParticleFactory extends ParticleFactory {
    /**
     * 默认宽高
     */
    public static final int PART_WH = 8;

    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect bound) {
        int w = bound.width();
        int h = bound.height();

        //横向个数
        int partW_count = (w / PART_WH);
        //竖向个数
        int partH_count = (h / PART_WH);

        partW_count = partW_count > 0 ? partW_count : 1;
        partH_count = partH_count > 0 ? partH_count : 1;

        int bitmap_part_w = bitmap.getWidth() / partW_count;//列
        int bitmap_part_h = bitmap.getHeight() / partH_count;//行

        Particle[][] particles = new Particle[partH_count][partW_count];
        for (int row = 0; row < partH_count; row++) {
            for (int column = 0; column < partW_count; column++) {
                //取得当前粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmap_part_h);
                float x = bound.left + PART_WH * column;
                float y = bound.top + PART_WH * row;
                particles[row][column] = new FallingParticle(x, y, color, bound);
            }
        }
        return particles;
    }
}
