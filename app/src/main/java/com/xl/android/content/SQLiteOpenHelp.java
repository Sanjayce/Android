package com.xl.android.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteOpenHelp extends SQLiteOpenHelper{

	public SQLiteOpenHelp(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table datestb(_id integer primary key autoincrement,name text not null,sex text not null,age text not null)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
