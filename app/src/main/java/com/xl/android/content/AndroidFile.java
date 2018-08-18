package com.xl.android.content;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.xl.android.R;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class AndroidFile extends AppCompatActivity implements OnClickListener {

    private EditText mEditText;
    private Button Reade, Write;
    private TextView mTextView;
    private String filename = "demo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_file);
        setTitle("AndroidFile");
        if (savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getString("data"));
        }
        initView();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.editText);
        Reade = (Button) findViewById(R.id.but2);
        Write = (Button) findViewById(R.id.but1);
        mTextView = (TextView) findViewById(R.id.tv);

        Reade.setOnClickListener(this);
        Write.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data", mEditText.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but1:
                onWriteFile();
                break;

            case R.id.but2:
                onReadFile();
                break;
        }

    }

    private void onReadFile() {
        try {
            StringBuilder sb = new StringBuilder();
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            char[] buffer = new char[fis.available()];
            int len;
            while ((len = isr.read(buffer)) > 0) {
                sb.append(buffer, 0, len);
            }
            mTextView.setText(sb.toString());
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void onWriteFile() {
        try {
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(mEditText.getText().toString());
            bw.flush();
            osw.flush();
            bw.close();
            osw.close();
            fos.close();
            Toast.makeText(getApplicationContext(), "文件写入成功", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
