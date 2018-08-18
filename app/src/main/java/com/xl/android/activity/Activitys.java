package com.xl.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xl.android.R;

public class Activitys extends AppCompatActivity implements View.OnClickListener {

    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitys_layout);

        bt1 = (Button) findViewById(R.id.button1);
        bt2 = (Button) findViewById(R.id.button2);
        bt3 = (Button) findViewById(R.id.button3);
        bt4 = (Button) findViewById(R.id.button4);
        tv = (TextView) findViewById(R.id.textView1);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);

    }

    //接收界面切换时的回传参数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            tv.setText(data.getStringExtra("data"));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1://显示intent 无参数 启动activity
                startActivity(new Intent(Activitys.this, ScondeActivity.class));
                break;

            case R.id.button2://显示intent 有返回参数 启动activity
                Intent intent2 = new Intent(Activitys.this, ScondeActivity.class);
                startActivityForResult(intent2, 1);
                break;

            case R.id.button3://intent加载系统浏览器
                Intent intent3 = new Intent();
                Uri uri = Uri.parse("http://www.baidu.com");
                intent3.setAction(Intent.ACTION_VIEW);
                intent3.setData(uri);
                startActivity(intent3);
                break;

            case R.id.button4://隐士intent 作为载体传参,action值与androidmanifest.xml一致
                Intent intent4 = new Intent("com.example.activity.intent.ThreeActivity");
                intent4.putExtra("data", "intent carrie");
                startActivity(intent4);
                break;

        }
    }
}
