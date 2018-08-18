package com.xl.android.content;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.xl.android.R;
import java.util.ArrayList;
import java.util.List;

public class ContentProviders extends AppCompatActivity {

	private List<String> mlList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_privder);
		setTitle("ContentProvider");
		initView();
        readContact();
	}

	private void initView() {
        ListView mlListView = (ListView) findViewById(R.id.content_list);
		mlList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mlList);
        mlListView.setAdapter(adapter);
	}

	private void readContact(){
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
           if(cursor!=null){
               while (cursor.moveToNext()){
                   String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                   String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                   mlList.add(name+"\n"+number);
               }
           }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }
}
