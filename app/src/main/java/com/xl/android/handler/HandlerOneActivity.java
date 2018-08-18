package com.xl.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.xl.android.R;

/**
 * Android UI 更新，只能在UI线程中更新组件
 */

public class HandlerOneActivity extends AppCompatActivity {

    private TextView mtTextView;
    private TextView mtTextView2;
    private ImageView mImageView;
    private int index;
    private int[] res = {R.mipmap.background1, R.mipmap.background2,
            R.mipmap.background3, R.mipmap.background4,
            R.mipmap.background5};

    //handleMessage接收数据，并更新UI
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            //mtTextView2.setText("" + msg.arg1 + "/" + msg.arg2);
            mtTextView2.setText("" + msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_first);

        setTitle("Handler");

        mtTextView = (TextView) findViewById(R.id.text);
        mtTextView2 = (TextView) findViewById(R.id.text2);
        mImageView = (ImageView) findViewById(R.id.imageView);


        MyRunnable mRunnable = new MyRunnable();
        mHandler.postDelayed(mRunnable, 1000);

       //(handler.post)
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);

                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            mtTextView.setText("Data Updata...");
                        }
                    });

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }.start();

        //(handler.send)
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    /**
                     * 发送简单数据
                     */
                    // Message message = new Message();
                    //message.arg1 = 100;
                    //message.arg2 = 200;
                    /**
                     * 发送对象数据
                     */
                    Message message = mHandler.obtainMessage();
                    Prson prson = new Prson("XMAN", "MAN", 100);
                    message.obj = prson;
                    mHandler.sendMessage(message);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * javaBean数据封装
     */

    class Prson {

        private String name;
        private String sex;
        private int age;

        public Prson(String name, String sex, int age) {

            this.name = name;
            this.sex = sex;
            this.age = age;
        }
    }

    /**
     * Runnable接口调用
     */
    class MyRunnable implements Runnable {

        @Override
        public void run() {
            index++;
            index = index % 5;
            mImageView.setImageResource(res[index]);
            mHandler.postDelayed(this, 1000);
        }

    }

}
