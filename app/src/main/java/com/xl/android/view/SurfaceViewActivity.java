package com.xl.android.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.xl.android.R;

/**
 * SurfaceView 自定义View -->绘图工具类 Canvas(画布)，Paint(画笔)，Path(路径)
 */

public class SurfaceViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(new MyView(this));
            setTitle("View:Canvas/Paint/Path");
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(new MySurfaceView(this));
            setTitle("SurfaceView:Canvas/Paint/Path");

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

/**
 * SurfaceView 自定义View
 */

class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private Context context;

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        //回调函数
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Toast.makeText(context,"***surfaceCreated***",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Toast.makeText(context,"***surfaceChanged***",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Toast.makeText(context,"***surfaceDestroyed***",Toast.LENGTH_SHORT).show();
    }
}


/**
 *  自定义View -->绘图工具类 Canvas(画布)，Paint(画笔)，Path(路径)
 */
class MyView extends View{

    private Paint mPaint = null; //新建画笔
    private Path path = null; //新建路径
    private int angle = 0; //角度 0~360
    private int mLastX;
    private int mLastY;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        path = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);        //画笔颜色
        mPaint.setAntiAlias(true);          //抗锯齿
        mPaint.setStyle(Paint.Style.FILL);  //画笔风格
        mPaint.setTextSize(36);             //绘制文字大小，单位px
        mPaint.setStrokeWidth(5);           //画笔粗细
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);  //设置画布背景
        canvas.save();                  //保存画布
        //canvas.translate(getWidth()/2, getHeight()/2); //位移
        //canvas.scale(2.0f,2.0f); //缩放
        canvas.rotate(angle,getWidth()/2, getHeight()/2); //旋转
        angle++;

        canvas.drawRect(new RectF(30,40,30,40),mPaint);
        canvas.drawCircle(getWidth()/2,getHeight()/2-200,30,mPaint);
        canvas.drawCircle(getWidth()/2, getHeight()/2, 120, mPaint);   //画实心圆

        //绘制一个图片
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), getWidth()/2, getHeight()/2, mPaint);
        //canvas.drawArc(new RectF(0, 0, 100, 100),0,90,true,mPaint);  //绘制弧形区域
        //canvas.drawRoundRect(new RectF(10,10,210,110),15,15,mPaint); //画圆角矩形
        //canvas.drawOval(new RectF(0,0,200,300),mPaint); //画椭圆
        //canvas.drawPath(path,mPaint);  ///绘制指定路劲图像
        //canvas.drawText("view",50, 50,mPaint); //绘制文字
        //canvas.drawTextOnPath("view", path, 50, 50, mPaint); //绘制指定路劲文字

        canvas.restore();
        invalidate();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                path.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);
                if (dx > 3 || dy > 3){
                    path.lineTo(x, y);
                }
                mLastX = x;
                mLastY = y;
                break;
        }
        return true;
    }
}
