package com.xl.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

public class FirstActivity extends Activity {

    private static final int TIME = 2000, GO_HOME = 1000, Go_DAOHANG = 1001;
    private boolean isFirstIn = false;


    /**
     * 通过判断来进入对应的页面
     */

    private  Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    startActivity(new Intent(FirstActivity.this,MainActivity.class));
                    finish();
                    break;

                case Go_DAOHANG:
                    //startActivity(new Intent(FirstActivity.this,FirstViewPager.class));
                    Toast.makeText(FirstActivity.this,"暂无界面!",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        init();
    }

    private void init() {
        SharedPreferences preferences = getSharedPreferences("mode",MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn",false);
        if (!isFirstIn){
            handler.sendEmptyMessageDelayed(GO_HOME,TIME);
        }else {
            SharedPreferences.Editor editor =  preferences.edit();
            editor.putBoolean("isFirstIn",true);
            editor.apply();
            handler.sendEmptyMessageDelayed(Go_DAOHANG,TIME);
        }
    }
}
