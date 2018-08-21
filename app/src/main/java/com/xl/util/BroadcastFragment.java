package com.xl.util;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xl.android.R;
import com.xl.android.broadcast.BCR1;
import com.xl.android.broadcast.BCR3;
import com.xl.android.broadcast.localBroadCast;

/**
 *  BroadCast界面
 *  广播接收与发送，注意注册方式和优先级
 */

public class BroadcastFragment extends Fragment implements View.OnClickListener{

    private BCR3 bc3 = null;
    private LocalBroadcastManager manager;
    private localBroadCast mlocalBroadCast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.broadcast_layout,container,false);

        manager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        mlocalBroadCast = new localBroadCast();
        bc3 = new BCR3();
        getActivity().registerReceiver(bc3, new IntentFilter(BCR3.Action));
        manager.registerReceiver(mlocalBroadCast, new IntentFilter(localBroadCast.Actions));

        view.findViewById(R.id.send).setOnClickListener(this);
        view.findViewById(R.id.send2).setOnClickListener(this);
        view.findViewById(R.id.send3).setOnClickListener(this);
        view.findViewById(R.id.send4).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.send:
                Intent intent = new Intent(getActivity().getApplicationContext(), BCR1.class);
                intent.putExtra("msg", "标准广播");
                //发送标准广播
                getActivity().sendBroadcast(intent);
                Toast.makeText(getActivity().getApplicationContext(), "标准广播", Toast.LENGTH_SHORT).show();
                break;

            case R.id.send2:
                Intent intent2 = new Intent("abc");
                intent2.putExtra("msg", "有序广播");
                //发送有序广播，当中间有广播被截断时，后面的广播无法继续接收了
                getActivity().sendOrderedBroadcast(intent2, null);
                Toast.makeText(getActivity().getApplicationContext(), "有序广播", Toast.LENGTH_SHORT).show();
                break;

            case R.id.send3:

                Intent intent3 = new Intent(BCR3.Action);
                intent3.putExtra("msg", "异步广播");
                //发送异步广播，已弃用
                getActivity().sendStickyBroadcast(intent3);
                Toast.makeText(getActivity().getApplicationContext(), "异步广播", Toast.LENGTH_SHORT).show();

                break;
            case R.id.send4:

                Intent intent4 = new Intent(getActivity().getApplicationContext(), localBroadCast.class);
                intent4.putExtra("msg", "本地广播");
                //本地广播，自作用与当前程序，无法静态注册
                manager.sendBroadcast(intent4);
                Toast.makeText(getActivity().getApplicationContext(), "本地广播", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().sendBroadcast(new Intent(getActivity().getApplicationContext(), BroadcastReceiver.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(bc3);
        manager.unregisterReceiver(mlocalBroadCast);
    }


}
