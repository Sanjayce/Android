package com.xl.android.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.xl.android.R;

import java.lang.ref.WeakReference;

/**
 * SurfaceView 自定义View -->绘图工具类 Canvas(画布)，Paint(画笔)，Path(路径)
 */

public class SurfaceViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private CircleImageView img_meizi;
    private SeekBar sb_hue;
    private SeekBar sb_saturation;
    private SeekBar sb_lum;
    private SeekBar sb_mul_add;
    private final static int MAX_VALUE = 255;
    private final static int MID_VALUE = 127;
    private float mHue = 0.0f;
    private float mStauration = 1.0f;
    private float mLum = 1.0f;
    private int mul = 0;
    private int add = 0;
    private Bitmap mBitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.surface_view_layout);
            setTitle("SurfaceView:Canvas/Paint/Path");
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic);
            bindViews();
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(new MyView(this));
            setTitle("View:Canvas/Paint/Path");
        }
    }

    private void bindViews() {
        img_meizi = (CircleImageView) findViewById(R.id.img_meizi);
        sb_hue = (SeekBar) findViewById(R.id.sb_hue);
        sb_saturation = (SeekBar) findViewById(R.id.sb_saturation);
        sb_lum = (SeekBar) findViewById(R.id.sb_lum);
        sb_mul_add = (SeekBar) findViewById(R.id.sb_mul_add);

        img_meizi.setImageBitmap(mBitmap);
        sb_hue.setMax(MAX_VALUE);
        sb_hue.setProgress(MID_VALUE);
        sb_saturation.setMax(MAX_VALUE);
        sb_saturation.setProgress(MID_VALUE);
        sb_lum.setMax(MAX_VALUE);
        sb_lum.setProgress(MID_VALUE);
        sb_mul_add.setMax(MAX_VALUE);
        sb_mul_add.setProgress(0);

        sb_hue.setOnSeekBarChangeListener(this);
        sb_saturation.setOnSeekBarChangeListener(this);
        sb_lum.setOnSeekBarChangeListener(this);

    }

    /**
     * 该方法用来处理图像，根据光照来调节
     * @param bp 图片
     * @param mul
     * @param add
     * @return Bitmap
     */
    private Bitmap ProcessImage(Bitmap bp,int mul,int add){
        Bitmap bitmap = Bitmap.createBitmap(bp.getWidth(),bp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new LightingColorFilter(mul,add));
        canvas.drawBitmap(bp,0,0,paint);
        return bitmap;
    }

    /**
     * 该方法用来处理图像，根据色调，饱和度，亮度来调节
     * @param bm:要处理的图像
     * @param hue:色调
     * @param saturation:饱和度
     * @param lum:亮度
     * @return Bitmap
     */
    private Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //颜色矩阵hue:色调
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);    //0代表R，红色
        hueMatrix.setRotate(1, hue);    //1代表G，绿色
        hueMatrix.setRotate(2, hue);    //2代表B，蓝色
        //颜色矩阵saturation:饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);
        //颜色矩阵lum:亮度
        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);
        //设置矩阵属性
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);

        return bmp;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_hue:
                mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE * 180;
                break;
            case R.id.sb_saturation:
                mStauration = progress * 1.0F / MID_VALUE;
                break;
            case R.id.sb_lum:
                mLum = progress * 1.0F / MID_VALUE;
                break;
            case R.id.sb_mul_add:
                mul = progress * -1;
                add = progress;
                break;
        }
        if(seekBar.getId() == R.id.sb_mul_add){
            img_meizi.setImageBitmap(ProcessImage(mBitmap,mul,add));
        }
        img_meizi.setImageBitmap(handleImageEffect(mBitmap, mHue, mStauration, mLum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}

/**
 * SurfaceView 注意：SurfaceView可以结合MediaPlayer进行视频播放，效果等同于VideoView
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

/**
 *  Paint--> setXfermode(PorterDuffXfermode)//对自定义视图的圆角/圆形
 */
class CircleImageView extends  android.support.v7.widget.AppCompatImageView {

    private Paint mPaint;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;
    private WeakReference<Bitmap> mWeakBitmap; //缓存机制

    //图片相关的属性
    private int type;//类型，圆形或者圆角
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;
    private static final int BODER_RADIUS_DEFAULT = 10;//圆角默认大小值
    private int mBorderRadius;//圆角大小


    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //取出attrs中我们为View设置的相关值
        TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        mBorderRadius = tArray.getDimensionPixelSize(R.styleable.CircleImageView_Radius, BODER_RADIUS_DEFAULT);
        type = tArray.getInt(R.styleable.CircleImageView_type, TYPE_CIRCLE);
        tArray.recycle();
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == TYPE_CIRCLE) {
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(width, width);    //设置当前View的大小
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //在缓存中取出bitmap
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
        if (bitmap == null || bitmap.isRecycled()) {
            //获取图片宽高
            Drawable drawable = getDrawable();
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();

            if (drawable != null) {
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                Canvas drawCanvas = new Canvas(bitmap);
                float scale;
                if (type == TYPE_ROUND) {
                    scale = Math.max(getWidth() * 1.0f / width, getHeight() * 1.0f / height);
                } else {
                    scale = getWidth() * 1.0F / Math.min(width, height);
                }
                //根据缩放比例，设置bounds，相当于缩放图片了
                drawable.setBounds(0, 0, (int) (scale * width), (int) (scale * height));

                drawable.draw(drawCanvas);
                if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                    mMaskBitmap = getBitmap();
                }

                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);

                //绘制形状
                drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);

                //bitmap缓存起来，避免每次调用onDraw，分配内存
                mWeakBitmap = new WeakReference<>(bitmap);

                //绘制图片
                canvas.drawBitmap(bitmap, 0, 0, null);
                mPaint.setXfermode(null);

            }
        }
        if (bitmap != null) {
            mPaint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
        }
    }

    //缓存Bitmap，避免每次OnDraw都重新分配内存与绘图
    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mWeakBitmap != null) {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        super.invalidate();
    }

    //定义一个绘制形状的方法

    private Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿
        paint.setColor(Color.BLACK);
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mBorderRadius, mBorderRadius, paint);
        } else {
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
        }
        return bitmap;
    }
}

