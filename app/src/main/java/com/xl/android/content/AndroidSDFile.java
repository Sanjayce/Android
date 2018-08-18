package com.xl.android.content;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class AndroidSDFile extends AppCompatActivity implements OnClickListener {

    private EditText mEditText;
    private TextView mTextView;
    private Button read, write;
    private String str = Environment.getExternalStorageState();//获取SD卡的加载状态
    private File sdPath = Environment.getExternalStorageDirectory();//获取SD卡路劲
    private File file = new File(sdPath, "infor.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_sd_file);
        setTitle("AndroidSDFile");
        init();

    }

    private void init() {
        mEditText = (EditText) findViewById(R.id.sd_editText);
        mTextView = (TextView) findViewById(R.id.sd_tv);
        write = (Button) findViewById(R.id.sd_but1);
        read = (Button) findViewById(R.id.sd_but2);
        read.setOnClickListener(this);
        write.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sd_but1:

                writeSD();
                break;
            case R.id.sd_but2:
                readSD();
                break;
        }
    }

    private void readSD() {

        if (!str.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "SD卡尚未加载!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!sdPath.exists()) {
            Toast.makeText(getApplicationContext(), "无法找到SD中指定目录!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (file.exists()) {
            try {
                StringBuilder sb = new StringBuilder();
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                char[] buffer = new char[fis.available()];
                int len;
                while ((len = isr.read(buffer)) > 0){
                     sb.append(buffer,0,len);
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
        } else {
            Toast.makeText(getApplicationContext(), "无法找到SD中指定文件!", Toast.LENGTH_SHORT).show();
        }
    }


    private void writeSD() {

        if (!str.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "SD卡尚未加载!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!sdPath.exists()) {
            Toast.makeText(getApplicationContext(), "文件已存在!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            boolean isOK = file.createNewFile();
            if(isOK){
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                osw.write(mEditText.getText().toString());
                osw.flush();
                osw.close();
                fos.close();
                Toast.makeText(getApplicationContext(), "数据写入SD卡成功!", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
