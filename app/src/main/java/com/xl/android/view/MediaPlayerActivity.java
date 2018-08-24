package com.xl.android.view;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.xl.android.R;

/**
 * MediaPlayer音频，视频基本用法
 * 使用MediaPlayer + SurfaceView(SurfaceHolder -> getHolder   SurfaceHolder.Call)播放视频
 * 使用VideoView播放视频
 */

public class MediaPlayerActivity extends AppCompatActivity
        implements View.OnClickListener, SurfaceHolder.Callback {

    private MediaPlayer mediaPlayer = null;
    private MediaPlayer mPlayer = null;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private VideoView videoView;

    private boolean isRelease = true;   //判断是否MediaPlayer是否释放的标志

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_player_layout);
        setTitle("MediaPlayer");
        initView();
    }

    private void initView() {

        surfaceView = (SurfaceView) findViewById(R.id.sfv_view);
        videoView = (VideoView) findViewById(R.id.video_view);

        findViewById(R.id.media_play).setOnClickListener(this);
        findViewById(R.id.media_puse).setOnClickListener(this);
        findViewById(R.id.media_stop).setOnClickListener(this);

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);

        findViewById(R.id.video_start).setOnClickListener(this);
        findViewById(R.id.video_pause).setOnClickListener(this);
        findViewById(R.id.video_stop).setOnClickListener(this);

        //初始化SurfaceHolder类，SurfaceView的控制器
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFixedSize(320, 220);   //显示的分辨率,不设置为视频默认

        //根据文件路径播放
        //if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        //    videoView.setVideoPath(Environment.getExternalStorageDirectory() + "/lesson.mp4");
        //}

        //读取放在raw目录下的文件
        videoView.setVideoURI(Uri.parse("android.resource://com.xl.android/" + R.raw.lesson));
        videoView.setMinimumHeight(220);
        videoView.setMinimumWidth(320);
        videoView.setMediaController(new MediaController(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.media_play:
                if (isRelease) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.music);
                    isRelease = false;
                }
                mediaPlayer.start();   //开始播放
                break;
            case R.id.media_puse:
                mediaPlayer.pause();     //停止播放
                break;
            case R.id.media_stop:
                mediaPlayer.reset();     //重置MediaPlayer
                mediaPlayer.release();   //释放MediaPlayer
                isRelease = true;
                break;
            case R.id.btn_start:
                mPlayer.start();
                break;
            case R.id.btn_pause:
                mPlayer.pause();
                break;
            case R.id.btn_stop:
                mPlayer.stop();
                break;
            case R.id.video_start:
                videoView.start();
                break;
            case R.id.video_pause:
                videoView.pause();
                break;
            case R.id.video_stop:
                videoView.stopPlayback();
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mPlayer = MediaPlayer.create(MediaPlayerActivity.this, R.raw.lesson);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setDisplay(surfaceHolder);    //设置显示视频显示在SurfaceView上
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.release();
    }
}
