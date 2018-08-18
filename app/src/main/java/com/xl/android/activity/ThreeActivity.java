package com.xl.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xl.android.R;

/**
 * 隐士intent 作为载体传参,action值与androidmanifest.xml一致
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
		Intent intent = getIntent();
		String str = intent.getStringExtra("data");
		tv.setText(str);

		mButton =  findViewById(R.id.but);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(ThreeActivity.this,Activitys.class));
				finish();
			}
		});
	}
}
