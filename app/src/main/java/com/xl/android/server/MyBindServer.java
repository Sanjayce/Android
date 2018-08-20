package com.xl.android.server;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xl.android.R;

/**
 * bind服务对象
 */

public class MyBindServer extends Service {

    private int index;
    private int[] raw = { R.raw.music, R.raw.can, R.raw.good };
    private MediaPlayer mPlayer;

    private boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("LOG","onCreate");
        isRunning = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("LOG","onBind");
        onStartRunning();
        return new MyBind();
    }


    public void onStartRunning() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                while(isRunning){
                    try {
                        sleep(500);
                        System.out.println("isRunning.....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    public void Music() {
        index++;
        index = index % 3;
        mPlayer = MediaPlayer.create(getApplicationContext(), raw[index]);
    }

    public void play() {
        Log.i("service", "play");
        Music();
        mPlayer.start();
    }

    public void pause() {
        Log.i("service", "pause");
        mPlayer.pause();
    }

    public void provious() {
        Log.i("service", "provious");
        mPlayer.stop();
        Music();
        mPlayer.start();
    }

    public void next() {
        Log.i("service", "next");
        mPlayer.stop();
        Music();
        mPlayer.start();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("LOG","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("LOG","onDestroy");
        isRunning = false;
    }

    public class MyBind extends Binder{

        public MyBindServer getServer(){

            return  MyBindServer.this;
        }
    }

}


