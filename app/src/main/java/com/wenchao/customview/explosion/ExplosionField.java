package com.wenchao.customview.explosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

import static com.wenchao.customview.explosion.Utils.RANDOM;

/**
 * @author wenchao
 * @date 2019/7/4.
 * @time 17:36
 * description：
 */
public class ExplosionField extends View {

    private ArrayList<ExplosionAnimator> explosionAnimators;
    private ParticleFactory mParticleFactory;
    private OnClickListener onClickListener;

    public ExplosionField(Context context, ParticleFactory particleFactory) {
        super(context);
        explosionAnimators = new ArrayList<>();
        mParticleFactory = particleFactory;
        //将动画区域添加到整个界面上
        attach2Activity((Activity) context);
    }

    private void attach2Activity(Activity context) {
        final ViewGroup rootView = context.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(this, lp);
    }

    /**
     * @param view 需要有粒子的特效的区域
     */
    public void addListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int viewCount = viewGroup.getChildCount();
            for (int i = 0; i < viewCount; i++) {
                addListener(viewGroup.getChildAt(i));
            }
        } else {
            view.setClickable(true);
            view.setOnClickListener(getOnclickListener());
        }
    }

    private OnClickListener getOnclickListener() {
        if (onClickListener == null) {
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExplosionField.this.explode(v);
                }
            };
        }
        return onClickListener;
    }

    /**
     * 爆炸
     *
     * @param view
     */
    private void explode(final View view) {
        //先获取view的区域位置
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);//获取view相对月整个屏幕的位置
        //标题栏高度
        int contentTop = ((ViewGroup) getParent()).getTop();
        //状态栏高度
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        rect.offset(0, -contentTop - statusBarHeight);
        if (rect.width() == 0 || rect.height() == 0) {
            return;
        }

        //震动动画
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                view.setTranslationX((RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((RANDOM.nextFloat() - 0.5f) * view.getHeight() * 0.05f);


            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                explode(view, rect);
            }
        });
        animator.start();
    }

    private void explode(final View view, Rect bound) {
        ExplosionAnimator animator = new ExplosionAnimator(this, Utils.createBitmapFromView(view), bound, mParticleFactory);
        explosionAnimators.add(animator);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                view.setClickable(false);
                //缩小,透明动画
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(150).start();
                view.setClickable(true);
                //动画结束时从动画集中移除
                explosionAnimators.remove(animation);
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ExplosionAnimator explosionAnimator : explosionAnimators) {
            explosionAnimator.draw(canvas);
        }
    }
}
