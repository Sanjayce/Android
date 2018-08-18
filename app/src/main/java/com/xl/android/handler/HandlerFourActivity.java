package com.xl.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.xl.android.R;

/**
 * HandlerThread
 */

public class HandlerFourActivity extends AppCompatActivity implements OnClickListener {


	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Message message = new Message();
			//System.out.println(Thread.currentThread());
			handlerThread.sendMessageDelayed(message, 1000);
			
		}
	};

	private Handler handlerThread;
    private HandlerThread thread;
	private Button mButton1;
	private Button mButton2;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.handler_four);
		setTitle("Handler");

		mTextView = (TextView) findViewById(R.id.fourtextView);
		mButton1 = (Button) findViewById(R.id.button1);
		mButton2 = (Button) findViewById(R.id.button2);
		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);

		thread = new HandlerThread("HandlerThread");
		thread.start();
        

		handlerThread = new Handler(thread.getLooper()) {
			@Override
			public void handleMessage(Message msg) {
				Message message = new Message();
				//System.out.println(Thread.currentThread());
				message.what = 0;
				handler.sendMessageDelayed(message, 1000);
			}
		};

	}
	


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button1:

			handler.sendEmptyMessage(1);
			mTextView.setText("Current background status:running...");
			break;

		case R.id.button2:

			handler.removeMessages(0);
			mTextView.setText("Current background status:stop");
			break;
		}

	}
}
