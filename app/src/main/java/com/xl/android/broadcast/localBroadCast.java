package com.xl.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 本地广播，作用于当前程序内部，不能静态启动
 */

public class localBroadCast extends BroadcastReceiver {

    public static final String Actions = "android.content.BroadcastReceiver.intent.actions";
    @Override
    public void onReceive(Context context, Intent intent) {
       System.out.println(intent.getStringExtra("msg"));
    }
}
