package com.xl.android.view;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.xl.android.R;

/**
 *  ProgressBar 不可拖动进度条：用于网络加载，下载，后台执行进度
 */

public class ProgressBarActivity extends AppCompatActivity{

    private ProgressBar progressBar;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar_layout);
        setTitle("ProgressBar");
        initUI();
    }

    private void initUI() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.incrementProgressBy(10);
                progressBar.incrementSecondaryProgressBy(20);
            }
        });
        findViewById(R.id.jian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.incrementProgressBy(-20);
                progressBar.incrementSecondaryProgressBy(-10);
            }
        });
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setProgress(0);
                progressBar.setSecondaryProgress(0);
            }
        });
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(ProgressBarActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setTitle("ProgressDialog...");

                ValueAnimator animator = ValueAnimator.ofInt(0, 100);//生产0到100的数值
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        //通过回掉值animator，将ValueAnimator产生的数值付给value
                        Integer value = (Integer) animator.getAnimatedValue();
                        dialog.setProgress(value);
                    }
                });
                animator.setDuration(10000);
                animator.start();

                dialog.setButton(ProgressDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
