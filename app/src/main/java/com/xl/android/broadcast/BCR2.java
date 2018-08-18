package com.xl.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BCR2 extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("receiver2" + intent.getStringExtra("msg"));
		abortBroadcast();//截断广播
	}

}
