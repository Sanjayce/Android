package com.xl.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BCR1 extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("receiver1" + intent.getStringExtra("msg"));
	}

}
