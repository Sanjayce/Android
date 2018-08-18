package com.xl.android.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.xl.android.R;


/**
 * Handler更新UI的方式，android UI更新的方式
 */

public class HandlerActivity extends AppCompatActivity implements OnClickListener{

	private Button mButton1;
	private Button mButton2;
	private Button mButton3;
	private Button mButton4;
	private Button mButton5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handler_main);

		setTitle("Handler");
		
		mButton1 = (Button) findViewById(R.id.first);
		mButton2 = (Button) findViewById(R.id.second);
		mButton3 = (Button) findViewById(R.id.three);
		mButton4 = (Button) findViewById(R.id.four);
		mButton5 = (Button) findViewById(R.id.five);
		
		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);
		mButton3.setOnClickListener(this);
		mButton4.setOnClickListener(this);
		mButton5.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.first:
			intent.setClass(HandlerActivity.this,HandlerOneActivity.class);
			startActivity(intent);
			break;
		case R.id.second:
			intent.setClass(HandlerActivity.this,HandlerTowActivity.class);
			startActivity(intent);
			break;
		case R.id.three:
			intent.setClass(HandlerActivity.this,HandlerThreeActivity.class);
			startActivity(intent);
			break;
		case R.id.four:
			intent.setClass(HandlerActivity.this,HandlerFourActivity.class);
			startActivity(intent);
			break;
		case R.id.five:
			intent.setClass(HandlerActivity.this,HandlerFiveActivity.class);
			startActivity(intent);
			break;
		}
		
	}

}
