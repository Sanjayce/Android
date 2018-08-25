package com.xl.android.network;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpClient使用例子
 * 注意android6.0 以后 HttpClient库被Google废弃掉;
 * 若要使用，请在对应的model下
 * android {useLibrary 'org.apache.http.legacy'}
 * 即可使用
 */

public class HttpClient {

    private String url;

    public HttpClient(String url){
        this.url = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                useHttpClientGet();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                useHttpClientPost();
            }
        }).start();
    }

    //创建HttpClient
    private static org.apache.http.client.HttpClient createHttpClient() {
        HttpParams mDefaultHttpParams = new BasicHttpParams();
        //设置连接超时
        HttpConnectionParams.setConnectionTimeout(mDefaultHttpParams, 15000);
        //设置请求超时
        HttpConnectionParams.setSoTimeout(mDefaultHttpParams, 15000);
        HttpConnectionParams.setTcpNoDelay(mDefaultHttpParams, true);
        HttpProtocolParams.setVersion(mDefaultHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(mDefaultHttpParams, HTTP.UTF_8);
        //持续握手
        HttpProtocolParams.setUseExpectContinue(mDefaultHttpParams, true);
        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient(mDefaultHttpParams);
        return httpClient;

    }

    //GET
    private void useHttpClientGet() {
        HttpGet mHttpGet = new HttpGet(this.url);
        mHttpGet.addHeader("Connection", "Keep-Alive");
        try {
            org.apache.http.client.HttpClient mHttpClient = createHttpClient();
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
            HttpEntity mHttpEntity = mHttpResponse.getEntity();
            int code = mHttpResponse.getStatusLine().getStatusCode();
            if (null != mHttpEntity) {
                InputStream mInputStream = mHttpEntity.getContent();
                String respose = HttpActivity.toString(mInputStream);
                Log.e("HttpClient", "请求状态码:" + code + "\n请求结果:\n" + respose);
                mInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //POST
    private void useHttpClientPost() {
        HttpPost mHttpPost = new HttpPost(this.url);
        mHttpPost.addHeader("Connection", "Keep-Alive");
        try {
            org.apache.http.client.HttpClient mHttpClient = createHttpClient();
            List<NameValuePair> postParams = new ArrayList<>();
            //要传递的参数
            postParams.add(new BasicNameValuePair("username", "moon"));
            postParams.add(new BasicNameValuePair("password", "123"));
            mHttpPost.setEntity(new UrlEncodedFormEntity(postParams));
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpPost);
            HttpEntity mHttpEntity = mHttpResponse.getEntity();
            int code = mHttpResponse.getStatusLine().getStatusCode();
            if (null != mHttpEntity) {
                InputStream mInputStream = mHttpEntity.getContent();
                String respose = HttpActivity.toString(mInputStream);
                Log.e("HttpClient", "请求状态码:" + code + "\n请求结果:\n" + respose);
                mInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
