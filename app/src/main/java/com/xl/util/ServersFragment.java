package com.xl.util;

import android.app.Fragment;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;
import com.xl.android.server.MyBindServer;
import com.xl.android.server.MyStartServer;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

/**
 *  BroadCast界面
 */

public class ServersFragment extends Fragment implements View.OnClickListener{

    private TextView showVoice;
    private Intent intent1;
    private int max;
    private MyBindServer server;

    /**
     * Handler机制，实时更新UI
     */
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (msg.obj != null && max > 0){
                showVoice.setText("VOICE" + max + "："+ msg.getData().getInt("voice"));
            }
            showVoice.setText("********");
        }
    };

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            server = ((MyBindServer.MyBind) iBinder).getServer();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("TAG","onServiceDisconnected...");
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.server_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        showVoice = view.findViewById(R.id.text_view);

        view.findViewById(R.id.start).setOnClickListener(this);
        view.findViewById(R.id.stop).setOnClickListener(this);
        view.findViewById(R.id.bind).setOnClickListener(this);
        view.findViewById(R.id.unbind).setOnClickListener(this);

        view.findViewById(R.id.play).setOnClickListener(this);
        view.findViewById(R.id.pause).setOnClickListener(this);
        view.findViewById(R.id.provious).setOnClickListener(this);
        view.findViewById(R.id.next).setOnClickListener(this);

        view.findViewById(R.id.network).setOnClickListener(this);
        view.findViewById(R.id.wifi).setOnClickListener(this);
        view.findViewById(R.id.voice).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                intent1 = new Intent(getActivity(), MyStartServer.class);
                getActivity().startService(intent1);
                Toast.makeText(getActivity().getApplicationContext(),"serviceIsRunning...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.stop:
                getActivity().stopService(intent1);
                Toast.makeText(getActivity().getApplicationContext(),"serviceNotRunning...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bind:
                Intent  intent2 = new Intent(getActivity(), MyBindServer.class);
                getActivity(). bindService(intent2, conn, Service.BIND_AUTO_CREATE);
                Toast.makeText(getActivity().getApplicationContext(),"serviceIsRunning...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.unbind:
                getActivity().unbindService(conn);
                Toast.makeText(getActivity().getApplicationContext(),"serviceNotRunning...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.play:
                server.play();
                break;
            case R.id.pause:
                server.pause();
                break;
            case R.id.provious:
                server.provious();
                break;
            case R.id.next:
                server.next();
                break;
            case R.id.network:
                if (getNetWorkForState(getActivity())) {
                    Toast.makeText(getActivity().getApplicationContext(), "NetWork is Open", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "NetWork is Close", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.wifi:
                setWifiOnAndOff();
                break;
            case R.id.voice:
                setSystemVoice();
                break;
        }
    }

    /**
     * ConnectivityManager:获取网络状态
     */
    public boolean getNetWorkForState(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * AudioManager:获取系统音量
     */
    public void setSystemVoice() {
        AudioManager mAudioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        int change = mAudioManager.getStreamVolume(AudioManager.STREAM_RING);
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putInt("voice", change);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    /**
     * WifiManager:获取WIFI状态
     */
    public void setWifiOnAndOff() {
        WifiManager mWifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
            Toast.makeText(getActivity().getApplicationContext(), "Wifi off", Toast.LENGTH_SHORT).show();
        } else {
            mWifiManager.setWifiEnabled(true);
            Toast.makeText(getActivity().getApplicationContext(), "Wifi on", Toast.LENGTH_SHORT).show();
        }
    }

}
