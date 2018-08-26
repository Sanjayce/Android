package com.xl.android.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * tcp/ip --> socket网络通讯
 */

public class TcpAndSocketActivity extends AppCompatActivity {

    private TextView title,show;
    private EditText input;
    private static final String SERVER_ADRESS = "169.254.30.49";
    private Socket socket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcp_socket_layout);
        setTitle("tcp/ip --> socket");
        initUI();
        new Thread(new Runnable() {
            @Override
            public void run() {
                acceptServer(8888);
                sendRequesteMassge("请求通讯连接!");
                recoverRessponseMasge();
            }
        }).start();

    }

    private void initUI() {
        title = (TextView) findViewById(R.id.locanl_title);
        show = (TextView) findViewById(R.id.server_content);
        input = (EditText) findViewById(R.id.socket_input);
        findViewById(R.id.send_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder builder = new StringBuilder();
                        String str =  input.getText().toString();
                        if(!str.equals("")){
                            show.setText(builder.append(str+"\n").toString());
                            input.setText("");
                        }
                        Toast.makeText(TcpAndSocketActivity.this,"不能发送空消息",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 与服务器连接通讯
     * @param port
     */
    private void acceptServer(int port){
        try {
            InetAddress address =   InetAddress.getLocalHost();//获取本机地址
            String ip = address.getHostAddress();//获取本机ip
            title.setText(SERVER_ADRESS+":"+port+"//"+ip);
            socket = new Socket(SERVER_ADRESS,port); //连接服务器
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送请求消息
     */

    private void sendRequesteMassge(String str){
        try {
            OutputStream os =  socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(str);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收响应消息
     */
    private void recoverRessponseMasge(){
        final StringBuilder builder = new StringBuilder();
        try {
            InputStream is =  socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            String line = dis.readUTF();
            builder.append(line).append("\n");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    show.setText(builder.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
