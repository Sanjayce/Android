package com.xl.android.content;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ContentValue extends AppCompatActivity {
	private TextView mTextView;
	private String sql="create table if not exists date2tb (_id integer primary key autoincrement, name text not null , age integer not null , sex text not null )";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTextView = new TextView(this);
		setContentView(mTextView);
		setTitle("ContentValue");
		SQLiteDatabase db = openOrCreateDatabase("date2.db", MODE_PRIVATE, null);
		db.execSQL(sql);

		ContentValues values = new ContentValues();
		values.put("name", "stenfan");
		values.put("sex", "man");
		values.put("age", 22);

		db.insert("date2tb", null, values);
		Cursor c = db.query("date2tb", null, null, null, null, null, null);
		if (c != null) {
			while (c.moveToNext()) {
				mTextView.setText("id"+c.getInt(c.getColumnIndex("_id"))+"name:"
						+ c.getString(c.getColumnIndex("name")) + "age:"
						+ c.getInt(c.getColumnIndex("age")) + "sex:"
						+ c.getString(c.getColumnIndex("sex")));
			}
			c.close();
		}
		db.close();
	}
}
