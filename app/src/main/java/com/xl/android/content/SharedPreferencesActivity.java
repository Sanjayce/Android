package com.xl.android.content;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.xl.android.R;

public class SharedPreferencesActivity extends Activity {

	private EditText mEditText;
	private TextView mTextView;
	private SharedPreferences preferences;
	private Editor mEditor;
	public String kEY = "values";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreference);
		setTitle("SharedPreferences");
		
		preferences = getPreferences(Activity.MODE_PRIVATE);
		//getSharedPreferences("配置文件名", Context.MODE_PRIVATE);
		mEditor = preferences.edit();
		
		mEditText = (EditText) findViewById(R.id.shared_editText);
		mTextView = (TextView) findViewById(R.id.shared_tv);
		// write
		findViewById(R.id.shared_but1).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mEditor.putString(kEY, mEditText.getText().toString());
						if (mEditor.commit()) {
							Toast.makeText(getApplicationContext(), "数据写入成功！",Toast.LENGTH_SHORT).show();
							mEditText.setText(null);
						}
					}
				});

		// read
		findViewById(R.id.shared_but2).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						String string = preferences.getString(kEY, "null");
						mTextView.setText(string);
					}
				});
		
		//preference_activity
		findViewById(R.id.preference_activity_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), PreferenceActivitys.class));
			}
		});
	}

}
