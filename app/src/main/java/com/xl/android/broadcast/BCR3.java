package com.xl.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BCR3 extends BroadcastReceiver {

	public static final String Action = "android.content.BroadcastReceiver.intent.action";
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("receiver3:"+intent.getStringExtra("msg"));
	}

}
