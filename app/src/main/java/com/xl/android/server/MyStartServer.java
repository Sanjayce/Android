package com.xl.android.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 普通服务对象
 */

public class MyStartServer extends Service {

    private boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("LOG","onCreate");
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("LOG","onStartCommand....");
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

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("LOG","onDestroy");
        isRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("LOG","onBind");
        return null;
    }
}
