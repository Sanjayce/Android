package com.xl.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 *
 */
public class ThreeActivity extends Activity {

	private TextView tv;
	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therr_activity);
		
		tv =  findViewById(R.id.text3);
		mButton =  findViewById(R.id.but);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(ThreeActivity.this,Activitys.class));
				finish();
			}
		});
		Intent intent = getIntent();
		String str = intent.getStringExtra("data");
		tv.setText(str);
	}
}
