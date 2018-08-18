package com.xl.android.content;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xl.android.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ContentpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentp_layout);
        initView();
        readeRawData();
        readeAssetsData();
    }

    private void readeRawData() {

        try {
            InputStream is = getResources().openRawResource(R.raw.file);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                Toast.makeText(ContentpActivity.this,line,Toast.LENGTH_SHORT).show();
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readeAssetsData() {

        try {
            InputStream is = getResources().getAssets().open("file.txt");
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                Toast.makeText(ContentpActivity.this,line,Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        findViewById(R.id.Button00).setOnClickListener(this);
        findViewById(R.id.Button01).setOnClickListener(this);
        findViewById(R.id.Button02).setOnClickListener(this);
        findViewById(R.id.Button03).setOnClickListener(this);
        findViewById(R.id.Button04).setOnClickListener(this);
        findViewById(R.id.Button05).setOnClickListener(this);
        findViewById(R.id.Button06).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Button00:
                startActivity(new Intent(getApplicationContext(), SQLites.class));
                break;
            case R.id.Button01:
                startActivity(new Intent(getApplicationContext(), ContentValue.class));
                break;
            case R.id.Button02:
                startActivity(new Intent(getApplicationContext(), ContentProviders.class));
                break;
            case R.id.Button03:
                startActivity(new Intent(getApplicationContext(), SharedPreferencesActivity.class));
                break;
            case R.id.Button04:
                startActivity(new Intent(getApplicationContext(), AndroidFile.class));
                break;
            case R.id.Button05:
                startActivity(new Intent(getApplicationContext(), AndroidSDFile.class));
                break;
            case R.id.Button06:
                startActivity(new Intent(getApplicationContext(), SQLiteDatabases.class));
                break;
        }
    }
}
