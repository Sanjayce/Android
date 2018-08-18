package com.xl.android.content;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class SQLites extends Activity {

	private TextView mTextView;
	private String sql = "create table if not exists datetb (id integer primary key autoincrement, name text not null , age integer not null , sex text not null )";
	private String sql1 = "insert into datetb(name,sex,age) values('sanjay','men',18)";
	private Cursor c;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTextView = new TextView(this);
		setContentView(mTextView);
		initData();
	}

	private void initData() {
		db = openOrCreateDatabase("date.db", MODE_PRIVATE, null);
		db.execSQL(sql);
		db.execSQL(sql1);
		c = db.query("datetb", null, null, null, null, null, null);
		if (c != null) {
			while (c.moveToNext()) {
				mTextView.setText(
						"name:" + c.getString(c.getColumnIndex("name")) + "\n"+
								"age:" + c.getInt(c.getColumnIndex("age")) + "\n"+
								"sex:" + c.getString(c.getColumnIndex("sex")));
			}
			c.close();
		}
		db.close();
	}
}
