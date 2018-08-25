package com.xl.android.network;

import android.text.TextUtils;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpURLConnection使用例子
 */

public class HttpURLConncetion {

    private String url;

    public HttpURLConncetion(String url){
        this.url = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                useHttpURLConncetionGET();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                useHttpURLConncetionPOST();
            }
        }).start();
    }

    //创建HttpURLConnction
    private HttpURLConnection createHttpURLConnection(){
        HttpURLConnection connection = null;
        try {
            //获取URL对象，并指定连接地址
            URL url = new URL(this.url);
            //通过openConnection()获取HttpURLConnection对象
            connection = (HttpURLConnection) url.openConnection();
            //读取数据超时
            connection.setReadTimeout(6 * 1000);
            //设置请求超时
            connection.setConnectTimeout(6 * 1000);
            //添加Header
            connection.setRequestProperty("Connection","Keep-Alive");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    //GET
    private void useHttpURLConncetionGET(){
        try {
            HttpURLConnection connection = createHttpURLConnection();
            //设置请求方式 get/post/put/delete....
            connection.setRequestMethod("GET");
            //获取相应码
            if (connection.getResponseCode() != 200) {
                Log.e("HttpURLConncetion:","Warning........");
            }
            //获取链接输入流
            InputStream in =  connection.getInputStream();
            Log.e("HttpURLConncetion:",HttpActivity.toString(in));
            //关闭链接
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置请求报文
    private static void postParams(OutputStream output, List<NameValuePair> paramsList) throws IOException {
        StringBuilder mStringBuilder=new StringBuilder();

        for (NameValuePair pair:paramsList){
            if(!TextUtils.isEmpty(mStringBuilder)){
                mStringBuilder.append("&");
            }
            mStringBuilder.append(URLEncoder.encode(pair.getName(),"UTF-8"));
            mStringBuilder.append("=");
            mStringBuilder.append(URLEncoder.encode(pair.getValue(),"UTF-8"));
        }

        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(output,"UTF-8"));
        writer.write(mStringBuilder.toString());
        writer.flush();
        writer.close();
    }

    //POST
    private void useHttpURLConncetionPOST(){
        try {
            HttpURLConnection connection = createHttpURLConnection();
            //设置请求方式 get/post/put/delete....
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            //获取相应码
            if (connection.getResponseCode() != 200) {
                Log.e("HttpURLConnection","Warning........");
            }

            List<NameValuePair> postParams = new ArrayList<>();
            //要传递的参数
            postParams.add(new BasicNameValuePair("username", "moon"));
            postParams.add(new BasicNameValuePair("password", "123"));

            postParams(connection.getOutputStream(),postParams);
            connection.connect();

            //获取链接输入流
            InputStream in =  connection.getInputStream();
            Log.e("HttpURLConnection:",HttpActivity.toString(in));
            //关闭链接
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
