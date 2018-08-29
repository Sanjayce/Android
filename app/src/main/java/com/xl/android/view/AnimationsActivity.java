package com.xl.android.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Android动画  帧动画  补间动画  属性动画
 */

public class AnimationsActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    private ImageView imageView1, imageView2;
    //获取所有控件id
    private int[] res = {R.id.animator_a, R.id.animator_b, R.id.animator_c, R.id.animator_d,
                         R.id.animator_e, R.id.animator_f, R.id.animator_g, R.id.animator_h};
    //创建一个ImageView的列表
    private List<ImageView> iamgeviewlist = new ArrayList<>();
    //创建一个boolean变量
    private Boolean bool = true;

    private ImageView image1, image2, image3, image4, image5, image6, image7, image8;
    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        setContentView(R.layout.animation_layout);
        setTitle("Animation");
        init();
        //获取图片资源
        for (int i = 0; i < res.length; i++) {
            ImageView image = (ImageView) findViewById(res[i]);
            image.setOnClickListener(this);
            iamgeviewlist.add(image);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private void init() {
        text = (TextView) findViewById(R.id.vlaue_animator_text);
        imageView1 = (ImageView) findViewById(R.id.animation_img1);
        imageView2 = (ImageView) findViewById(R.id.animation_img2);

        findViewById(R.id.frame_button).setOnClickListener(this);
        findViewById(R.id.alpha_button).setOnClickListener(this);
        findViewById(R.id.scale_button).setOnClickListener(this);
        findViewById(R.id.translet_button).setOnClickListener(this);
        findViewById(R.id.rotate_button).setOnClickListener(this);
        findViewById(R.id.table_but1).setOnClickListener(this);
        findViewById(R.id.table_but2).setOnClickListener(this);
        findViewById(R.id.table_but3).setOnClickListener(this);
        findViewById(R.id.table_but4).setOnClickListener(this);
        findViewById(R.id.vlaue_animator_btn).setOnClickListener(this);

        image1 = (ImageView) findViewById(R.id.animator_a1);
        image2 = (ImageView) findViewById(R.id.animator_b1);
        image3 = (ImageView) findViewById(R.id.animator_c1);
        image4 = (ImageView) findViewById(R.id.animator_d1);
        image5 = (ImageView) findViewById(R.id.animator_e1);
        image6 = (ImageView) findViewById(R.id.animator_f1);
        image7 = (ImageView) findViewById(R.id.animator_g1);
        image8 = (ImageView) findViewById(R.id.animator_h1);

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        image6.setOnClickListener(this);
        image7.setOnClickListener(this);
        image8.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.frame_button) {
            showFrameAnimation();
        }
        if (id == R.id.alpha_button) {
            showTweenAnimation(1);
        }
        if (id == R.id.scale_button) {
            showTweenAnimation(2);
        }
        if (id == R.id.translet_button) {
            showTweenAnimation(3);
        }
        if (id == R.id.rotate_button) {
            showTweenAnimation(4);
        }
        if (id == R.id.table_but1) {
            showTweenAnimation(5);
        }
        if (id == R.id.table_but2) {
            showTweenAnimation(6);
        }
        if (id == R.id.table_but3) {
            showTweenAnimation(7);
        }
        if (id == R.id.table_but4) {
            showTweenAnimation(8);
        }
        if (id == R.id.vlaue_animator_btn) {
            onEvalouatol();
        }
        if (id == R.id.animator_a) {
            if (bool) {
                animatorshow();
            } else {
                animatorcolse();
            }
        }
        if (id == R.id.animator_a1) {
            if (bool) {
                animatorshow1();
            } else {
                animatorcolse1();
            }
        }

    }

    /**
     * 帧动画 FrameAnimation
     */

    private void showFrameAnimation() {
        AnimationDrawable anim = (AnimationDrawable) imageView2.getBackground();
        anim.start();
    }

    /**
     * 补间动画 TweenAnimation : alpha,scale,translate,rotate,set
     */
    private void showTweenAnimation(int id) {

        if (id == 1) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
            imageView1.startAnimation(animation);
            animation.setAnimationListener(this);
        }
        if (id == 2) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
            imageView1.startAnimation(animation);
            animation.setAnimationListener(this);
        }
        if (id == 3) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
            imageView1.startAnimation(animation);
            animation.setAnimationListener(this);
        }
        if (id == 4) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            imageView1.startAnimation(animation);
            animation.setAnimationListener(this);
        }

        /**
         * 多个动画集合使用  AnimationSet()
         */
        if (id == 5) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.set_one);
            imageView1.startAnimation(animation);
            animation.setAnimationListener(this);
        }
        if (id == 6) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.set_two);
            imageView1.startAnimation(animation);
            animation.setAnimationListener(this);
        }

        /**
         * 通过代码动态设置动画
         */
        if (id == 7) {
            Animation animation = new TranslateAnimation(10.0f, 100.0f, 10.0f, 100.0f);
            animation.setDuration(2000);
            animation.setRepeatCount(1);
            animation.setRepeatMode(Animation.RESTART);// 设置顺序 倒序重复REVERSE 正序重复RESTART
            animation.setAnimationListener(this);
            imageView1.startAnimation(animation);
        }

        /**
         * 自定义动画
         */
        if (id == 8) {
            CommonAnimation cAnimation = new CommonAnimation();
            cAnimation.setDuration(500);
            cAnimation.setRepeatCount(10);
            cAnimation.setRepeatMode(Animation.RESTART);
            cAnimation.setAnimationListener(this);
            imageView1.startAnimation(cAnimation);
        }
    }

    /**
     * 属性动画,计时器
     */

    public void onEvalouatol() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);//生产0到100的数值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //通过回掉值animator，将ValueAnimator产生的数值付给value
                Integer value = (Integer) animator.getAnimatedValue();
                text.setText(String.valueOf(value));
            }
        });
        animator.setDuration(5000);
        animator.start();
    }

    /**
     * 属性动画 PropertyAnimation
     */

    //平移展开动画
    private void animatorshow() {

        for (int j = 1; j < res.length; j++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(iamgeviewlist.get(j), "translationX", 0f, j * 90f);
            animator.setDuration(500);
            animator.setInterpolator(new DecelerateInterpolator());//设置插值器，使动画产生加速减速和其他效果
            animator.setStartDelay(j * 50);//设置播放延时
            animator.start();
            bool = false;
        }
    }

    //发散展开动画
    private void animatorshow1() {

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(image2, "translationX", 0f, 480f);
        animator1.setDuration(500);
        animator1.start();
        //使用AnimatorSet实现
        ObjectAnimator animator2X = ObjectAnimator.ofFloat(image3, "translationX", 0, 466f);
        ObjectAnimator animator2Y = ObjectAnimator.ofFloat(image3, "translationY", 0, 125f);
        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(animator2X, animator2Y);//同时播放
        set2.setDuration(500);
        set2.start();

        ObjectAnimator animator3X = ObjectAnimator.ofFloat(image4, "translationX", 0, 416f);
        ObjectAnimator animator3Y = ObjectAnimator.ofFloat(image4, "translationY", 0, 240f);
        AnimatorSet set3 = new AnimatorSet();
        set3.playTogether(animator3X, animator3Y);//同时播放
        set3.setDuration(500);
        set3.start();

        ObjectAnimator animator4X = ObjectAnimator.ofFloat(image5, "translationX", 0, 339f);
        ObjectAnimator animator4Y = ObjectAnimator.ofFloat(image5, "translationY", 0, 339f);
        AnimatorSet set4 = new AnimatorSet();
        set4.playTogether(animator4X, animator4Y);//同时播放
        set4.setDuration(500);
        set4.start();

        ObjectAnimator animator5X = ObjectAnimator.ofFloat(image6, "translationX", 0, 240f);
        ObjectAnimator animator5Y = ObjectAnimator.ofFloat(image6, "translationY", 0, 416f);
        AnimatorSet set5 = new AnimatorSet();
        set5.playTogether(animator5X, animator5Y);//同时播放
        set5.setDuration(500);
        set5.start();

        ObjectAnimator animator6X = ObjectAnimator.ofFloat(image7, "translationX", 0, 125f);
        ObjectAnimator animator6Y = ObjectAnimator.ofFloat(image7, "translationY", 0, 466f);
        AnimatorSet set6 = new AnimatorSet();
        set6.playTogether(animator6X, animator6Y);//同时播放
        set6.setDuration(500);
        set6.start();

        ObjectAnimator animator7 = ObjectAnimator.ofFloat(image8, "translationY", 0f, 480f);
        animator7.setDuration(500);
        animator7.start();
        bool = false;
    }


    //平移收回动画
    private void animatorcolse() {

        for (int j = 1; j < res.length; j++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(iamgeviewlist.get(j), "translationX", j * 90f, 0f);
            animator.setDuration(500);
            animator.setInterpolator(new DecelerateInterpolator());//设置插值器，使动画产生加速减速和其他效果
            animator.setStartDelay(j * 50);//设置播放延时
            animator.start();
            bool = true;
        }
    }

    //发散收回动画
    private void animatorcolse1() {

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(image2, "translationX", 480f, 0f);
        animator1.setDuration(500);
        animator1.start();

        //使用AnimatorSet实现
        ObjectAnimator animator2X = ObjectAnimator.ofFloat(image3, "translationX", 466f, 0f);
        ObjectAnimator animator2Y = ObjectAnimator.ofFloat(image3, "translationY", 125f, 0f);
        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(animator2X, animator2Y);//同时播放
        set2.setDuration(500);
        set2.start();

        ObjectAnimator animator3X = ObjectAnimator.ofFloat(image4, "translationX", 416f, 0f);
        ObjectAnimator animator3Y = ObjectAnimator.ofFloat(image4, "translationY", 240f, 0f);
        AnimatorSet set3 = new AnimatorSet();
        set3.playTogether(animator3X, animator3Y);//同时播放
        set3.setDuration(500);
        set3.start();

        ObjectAnimator animator4X = ObjectAnimator.ofFloat(image5, "translationX", 339f, 0f);
        ObjectAnimator animator4Y = ObjectAnimator.ofFloat(image5, "translationY", 339f, 0f);
        AnimatorSet set4 = new AnimatorSet();
        set4.playTogether(animator4X, animator4Y);//同时播放
        set4.setDuration(500);
        set4.start();

        ObjectAnimator animator5X = ObjectAnimator.ofFloat(image6, "translationX", 240f, 0f);
        ObjectAnimator animator5Y = ObjectAnimator.ofFloat(image6, "translationY", 416f, 0f);
        AnimatorSet set5 = new AnimatorSet();
        set5.playTogether(animator5X, animator5Y);//同时播放
        set5.setDuration(500);
        set5.start();

        ObjectAnimator animator6X = ObjectAnimator.ofFloat(image7, "translationX", 125f, 0f);
        ObjectAnimator animator6Y = ObjectAnimator.ofFloat(image7, "translationY", 466f, 0f);
        AnimatorSet set6 = new AnimatorSet();
        set6.playTogether(animator6X, animator6Y);//同时播放
        set6.setDuration(500);
        set6.start();

        ObjectAnimator animator7 = ObjectAnimator.ofFloat(image8, "translationY", 480f, 0f);
        animator7.setDuration(500);
        animator7.start();
        bool = true;

    }


    /**
     * Android动画监听事件
     *
     * @param animation
     */

    @Override
    public void onAnimationStart(Animation animation) {
        Toast.makeText(this, "启动", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast.makeText(this, "结束", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        Toast.makeText(this, "重复", Toast.LENGTH_SHORT).show();
    }
}

/**
 * 自定义动画
 */

class CommonAnimation extends Animation {
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