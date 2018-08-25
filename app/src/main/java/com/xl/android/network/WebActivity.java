package com.xl.android.network;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xl.android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * WebView网页视图控件: html5 js
 */

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private EditText input;
    private String url = "http://www.hao123.com";
    private long endTime = 0;
   //private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        initUI();
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateUi();
            }
        }).start();

    }

    private void initUI() {
        webView = (WebView) findViewById(R.id.web_view);
        progressBar = (ProgressBar) findViewById(R.id.load_progress);
        input = (EditText) findViewById(R.id.input);

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = "http://www.hao123.com";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        updateUi();
                    }
                }).start();
            }
        });
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input.getText().toString().equals("")){
                    url = input.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            updateUi();
                        }
                    }).start();
                }
            }
        });
    }


    private void updateUi() {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               webView.loadUrl(url);

               //获取web属性
               webView.setWebChromeClient(new WebChromeClient() {

                   @Override
                   public void onReceivedTitle(WebView view, String title) {
                       setTitle(title);
                   }

                   @Override
                   public void onProgressChanged(WebView view, int newProgress) {
                       if (newProgress == progressBar.getMax()) {
                           progressBar.setVisibility(View.GONE);
                       }
                   }

                   //......
               });

               //加载web
               webView.setWebViewClient(new WebViewClient() {

                   @Override
                   public boolean shouldOverrideUrlLoading(WebView view, String url) {
                       view.loadUrl(url);
                       return super.shouldOverrideUrlLoading(view, url);
                   }

                   //......
               });

               //设置
               //webView.getSettings().setJavaScriptEnabled(true);
               webView.getSettings().setBuiltInZoomControls(true);
               //指定缓存类型
               webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
               // 开启DOM storage API 功能
               webView.getSettings().setDomStorageEnabled(true);
               // 开启database storage API功能
               webView.getSettings().setDatabaseEnabled(true);

               //指定缓存路径
               //String cachePath = getDataDir().getAbsolutePath()+APP_CACHE_DIRNAME;
               //设置缓存
               //webView.getSettings().setAppCachePath(cachePath);
               webView.getSettings().setAppCacheEnabled(true);

               //下载
               webView.setDownloadListener(new DownloadListener() {
                   @Override
                   public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                       //系统自带或三方浏览器下载
                       // Uri uri = Uri.parse(s);
                       //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                       //startActivity(intent);
                       DownLoadThread downLoadThread =   new DownLoadThread(s);
                       downLoadThread.start();
                   }
               });
           }
       });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if ((System.currentTimeMillis() - endTime) > 2000) {
                Toast.makeText(this, "双击退出...", Toast.LENGTH_SHORT).show();
                endTime = System.currentTimeMillis();
            }
        }
    }
}

/**
 * 自定义文件下载工具
 */

 class DownLoadThread extends Thread {

    private String url;
    private File downloadFile, sdFile;

    public DownLoadThread(String url){
        this.url = url;
    }

    @Override
    public void run() {

        InputStream in = null;
        FileOutputStream out = null;

        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            Log.i("DownLoadThread","DownLoad....");

            in = conn.getInputStream();
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                downloadFile = Environment.getExternalStorageDirectory();
                sdFile = new File(downloadFile,"android.apk");
                out = new FileOutputStream(sdFile);
            }
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1){
                out.write(buffer,0,len);
            }

            out.close();
            in.close();
            Log.i("DownLoadThread","Falish");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

