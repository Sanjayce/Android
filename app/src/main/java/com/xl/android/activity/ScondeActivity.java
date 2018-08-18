package com.xl.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xl.android.R;

/**
 * Activity回传参数
 */
public class ScondeActivity extends Activity {

    private Button bt;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scond_activity);
        bt =  findViewById(R.id.button1);
        text =  findViewById(R.id.editText1);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent data = new Intent();
                data.putExtra("data", text.getText().toString());
                setResult(2, data);
                finish();
            }
        });
    }
}
