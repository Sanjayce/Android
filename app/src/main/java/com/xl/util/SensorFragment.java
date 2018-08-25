package com.xl.util;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xl.android.R;

import java.util.List;

/**
 *  传感器
 */

public class SensorFragment extends Fragment implements SensorEventListener {

    private TextView txt1,txt2,all;
    private SensorManager manager;
    private  List<Sensor> allSensors;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = (Bundle) msg.obj;
            float msgs = bundle.getFloat("data");
            txt1.setText("加速度:" + msgs);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        allSensors = manager.getSensorList(Sensor.TYPE_ALL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.senser_layout,container,false);
        txt1 =  view.findViewById(R.id.sensor_text);
        txt2 =  view.findViewById(R.id.sensor_text2);
        all =  view.findViewById(R.id.sensor_text3);
        showAllSensor();
        return view;
    }

    private void showAllSensor(){
        StringBuilder sb = new StringBuilder();
        sb.append("此手机有" + allSensors.size() + "个传感器，分别有：\n\n");
        for(Sensor s:allSensors){
            switch (s.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    sb.append(s.getType() + " 加速度传感器(Accelerometer sensor)" + "\n");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    sb.append(s.getType() + " 陀螺仪传感器(Gyroscope sensor)" + "\n");
                    break;
                case Sensor.TYPE_LIGHT:
                    sb.append(s.getType() + " 光线传感器(Light sensor)" + "\n");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    sb.append(s.getType() + " 磁场传感器(Magnetic field sensor)" + "\n");
                    break;
                case Sensor.TYPE_ORIENTATION:
                    sb.append(s.getType() + " 方向传感器(Orientation sensor)" + "\n");
                    break;
                case Sensor.TYPE_PRESSURE:
                    sb.append(s.getType() + " 气压传感器(Pressure sensor)" + "\n");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    sb.append(s.getType() + " 距离传感器(Proximity sensor)" + "\n");
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    sb.append(s.getType() + " 温度传感器(Temperature sensor)" + "\n");
                    break;
                default:
                    sb.append(s.getType() + " 其他传感器" + "\n");
                    break;
            }
            sb.append("设备名称：" + s.getName() + "\n 设备版本：" + s.getVersion() + "\n 供应商：" + s.getVendor() + "\n\n");
        }
        all.setText(sb.toString());
    }


    @Override
    public void onResume() {
        super.onResume();
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }




    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                final float X = sensorEvent.values[0];//x
                final float Y = (float) (sensorEvent.values[1] - 9.81);//y
                final float Z = sensorEvent.values[2];//z
                new Thread() {
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putFloat("data", X + Y + Z);
                        Message msg = new Message();
                        msg.obj = bundle;
                        mHandler.sendMessageDelayed(msg, 1000);
                    }
                }.start();
                break;
            case Sensor.TYPE_LIGHT:
               final float C = sensorEvent.values[0];
                txt2.post(new Runnable() {
                    @Override
                    public void run() {
                        txt2.setText("亮度:\n" + C + "\n");
                    }
                });
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
