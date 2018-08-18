package com.xl.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import com.xl.android.R;

/**
 * HandlerThread
 */

public class HandlerThreeActivity extends AppCompatActivity {
	private TextView mTextView;
	private HandlerThread mHandlerThread;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mTextView = new TextView(this);
		mTextView.setText(R.string.app_name);
		mTextView.setTextSize(18.0f);
		setContentView(mTextView);
		setTitle("HandlerThread");
		//HandlerThread
		mHandlerThread = new HandlerThread("handler thread");
		mHandlerThread.start();
		//mHandlerThread.getLooper()
		mHandler = new Handler(mHandlerThread.getLooper()) {
			public void handleMessage(android.os.Message msg) {
				Toast.makeText(HandlerThreeActivity.this,Thread.currentThread()+"",Toast.LENGTH_LONG).show();
			}
		};
		mHandler.sendEmptyMessage(1);
	}

}
