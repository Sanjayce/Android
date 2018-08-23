package com.xl.android.view;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 自定义动画
 */

public class CommonAnimation extends Animation {
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        t.getMatrix().setTranslate(-5 * interpolatedTime, 5 * interpolatedTime);
        super.applyTransformation(interpolatedTime, t);
    }
}
