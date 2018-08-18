package com.xl.android.content;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.xl.android.R;


public class SQLiteDatabases extends Activity {

	private SimpleCursorAdapter mAdapter;
	private ListView mView;
	private Button mButton;
	private EditText mEditName, mEditAge, mEditSex;
	private SQLiteOpenHelper helper;
	private SQLiteDatabase dbread, dbwrite;
	private Cursor c;
	private ContentValues values;
	private LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqllite_database);
		helper = new SQLiteOpenHelp(getApplicationContext(), "dates.db",null,1);
		dbread = helper.getReadableDatabase();
		dbwrite = helper.getWritableDatabase();
		values = new ContentValues();

		initView();
		this.registerForContextMenu(mView);
		onQueryDataFromSystem();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("菜单");
		menu.add(1, 1, 1, "更新数据");
		menu.add(1, 2, 1, "删除数据");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			onUpdataDataToSystem();
			break;
		case 2:
			onDeleteDataFromSystem();
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void initView() {
		mView = (ListView) findViewById(R.id.sqlite_list);
		mEditName = (EditText) findViewById(R.id.sqlite_name_edit);
		mEditAge = (EditText) findViewById(R.id.sqlite_age_edit);
		mEditSex = (EditText) findViewById(R.id.sqlite_sex_edit);
		mButton = (Button) findViewById(R.id.sqlite_btu);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onInsertDataToSystem();
				onQueryDataFromSystem();
			}
		});
	}

	//数据库查询
	private void onQueryDataFromSystem() {
		c = dbread.query("datestb", null, null, null, null, null, null);
		mAdapter = new SimpleCursorAdapter(this,
				R.layout.sqlite_database_list_layout, c, new String[] { "name",
						"age", "sex" }, new int[] {
						R.id.sqlite_database_textView1,
						R.id.sqlite_database_textView2,
						R.id.sqlite_database_textView3 });
		mView.setAdapter(mAdapter);
	}

	//数据库添加数据
	private void onInsertDataToSystem() {
		String Ename = mEditName.getText().toString();
		String Esex = mEditSex.getText().toString();
		String Eage = mEditAge.getText().toString();
		if (Ename.equals("") || Esex.equals("") || Eage.equals("")) {
			Toast.makeText(getApplicationContext(), "数据输入不完整!", Toast.LENGTH_SHORT).show();
			return;
		}
		values.put("name", Ename);
		values.put("sex", Esex);
		values.put("age", Eage);
		dbwrite.insert("datestb", null, values);
		values.clear();
		mEditName.setText(null);
		mEditSex.setText(null);
		mEditAge.setText(null);
		Toast.makeText(getApplicationContext(), "数据添加成功!", Toast.LENGTH_SHORT).show();
	}

	//数据库变更数据
	private void onUpdataDataToSystem() {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		mBuilder.setTitle("数据修改");
		//自定义提示框
		View view = mInflater.from(this).inflate(R.layout.sqlite_updata, null);
		final EditText nameEditText = (EditText)view.findViewById(R.id.sqlite_updata_name_edit);
		final EditText sexEditText = (EditText)view.findViewById(R.id.sqlite_updata_sex_edit);
		final EditText ageEditText = (EditText)view.findViewById(R.id.sqlite_updata_age_edit);
		mBuilder.setView(view);

		mBuilder.setPositiveButton("变更", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				values.put("name",nameEditText.getText().toString());
				values.put("sex", sexEditText.getText().toString());
				values.put("age",ageEditText.getText().toString());
				dbwrite.update("datestb", values, null, null);
				values.clear();
				dialog.dismiss();
				onQueryDataFromSystem();
			}
		});
		mBuilder.setNegativeButton("取消", null);
		mBuilder.show();
		mBuilder.setInverseBackgroundForced(true);
	}

	//数据库删除数据
	private void onDeleteDataFromSystem() {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		mBuilder.setTitle("警告");
		mBuilder.setMessage("是否删除当前数据");
		mBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int id = c.getInt(c.getColumnIndex("_id"));
				dbwrite.delete("datestb", "_id = ?", new String[] { "" + id });
				dialog.dismiss();
				onQueryDataFromSystem();
			}
		});
		mBuilder.setNegativeButton("否", null);
		mBuilder.show();
		mBuilder.setInverseBackgroundForced(true);
	}

}
