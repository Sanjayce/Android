package com.xl.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;

/**
 * 主线程与子线程UI更新区别
 */

public class HandlerTowActivity extends AppCompatActivity {

	//UI更新
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Toast.makeText(HandlerTowActivity.this,"UIThread:" + Thread.currentThread(),Toast.LENGTH_LONG).show();
		}
	};

	//子线程
	class MyThread extends Thread {

		private Handler handler;

		@Override
		public void run() {
			Looper.prepare();// 获取Looper对象

			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					Toast.makeText(HandlerTowActivity.this,"currentThread:" + Thread.currentThread(),Toast.LENGTH_LONG).show();
				}
			};
			Looper.loop(); //开启消息列
		}
	}

	// 主线程
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView mTextView = new TextView(this);
		mTextView.setText("");
		setContentView(mTextView);
		setTitle("Handler");
		mHandler.sendEmptyMessage(1);//发送消息到主线程
		MyThread thread = new MyThread();
		thread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread.handler.sendEmptyMessage(1);//发送消息到子线程

	}

}
