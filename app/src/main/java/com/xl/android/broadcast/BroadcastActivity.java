package com.xl.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.xl.android.R;


/**
 *  广播接收与发送，注意注册方式和优先级
 */

public class BroadcastActivity extends AppCompatActivity {

    private BCR3 bc3 = null;
    private LocalBroadcastManager manager;
    private localBroadCast mlocalBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_layout);
        manager = LocalBroadcastManager.getInstance(this);
        mlocalBroadCast = new localBroadCast();
        bc3 = new BCR3();
        registerReceiver(bc3, new IntentFilter(BCR3.Action));
        manager.registerReceiver(mlocalBroadCast, new IntentFilter(localBroadCast.Actions));
    }

    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                Intent intent = new Intent(this, BCR1.class);
                intent.putExtra("msg", "标准广播");
                //发送标准广播
                sendBroadcast(intent);
                Toast.makeText(BroadcastActivity.this, "标准广播", Toast.LENGTH_SHORT).show();
                break;

            case R.id.send2:
                Intent intent2 = new Intent("abc");
                intent2.putExtra("msg", "有序广播");
                //发送有序广播，当中间有广播被截断时，后面的广播无法继续接收了
                sendOrderedBroadcast(intent2, null);
                Toast.makeText(BroadcastActivity.this, "有序广播", Toast.LENGTH_SHORT).show();
                break;

            case R.id.send3:

                Intent intent3 = new Intent(BCR3.Action);
                intent3.putExtra("msg", "异步广播");
                //发送异步广播，已弃用
                sendStickyBroadcast(intent3);
                Toast.makeText(BroadcastActivity.this, "异步广播", Toast.LENGTH_SHORT).show();

                break;
            case R.id.send4:

                Intent intent4 = new Intent(this, localBroadCast.class);
                intent4.putExtra("msg", "本地广播");
                //本地广播，自作用与当前程序，无法静态注册
                manager.sendBroadcast(intent4);
                Toast.makeText(BroadcastActivity.this, "本地广播", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sendBroadcast(new Intent(this, BroadcastReceiver.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bc3);
        manager.unregisterReceiver(mlocalBroadCast);
    }
}
