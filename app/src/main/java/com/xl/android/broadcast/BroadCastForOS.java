package com.xl.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 获取系统广播
 */

public class BroadCastForOS extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("onReceive","桌面墙纸已变更!");
    }
}
