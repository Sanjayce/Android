package com.xl.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.xl.android.R;

/**
 * Android中几种更新UI的方式
 */

public class HandlerFiveActivity extends AppCompatActivity {

	private TextView mTextView1;
	private TextView mTextView2;
	private TextView mTextView3;
	private TextView mTextView4;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			mTextView2.setText("Handler.sendMessage()");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handler_five);
		setTitle("Android UI");
		mTextView1 = (TextView) findViewById(R.id.textView1);
		mTextView2 = (TextView) findViewById(R.id.textView2);
		mTextView3 = (TextView) findViewById(R.id.textView3);
		mTextView4 = (TextView) findViewById(R.id.textView4);

		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
					// Handler.post()
					post();
					// ͨHandler.sendMessage()
					sendMessage();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}
		}.start();

		// ͨActivity:runOnUiThread()
		updateUi();
		// ͨView.post()
		viewUi();

	}

	private void post() {
		mHandler.post(new Runnable() {
			public void run() {
				mTextView1.setText("Handler.post()");
			}
		});
	}

	private void sendMessage() {
		Message message = mHandler.obtainMessage();
		mHandler.sendMessage(message);
	}

	private void updateUi() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mTextView3.setText("Activity-->runOnUiThread()");
			}
		});
	}

	private void viewUi() {
		mTextView4.post(new Runnable() {
			@Override
			public void run() {
				mTextView4.setText("View.post()");
			}
		});
	}
}
