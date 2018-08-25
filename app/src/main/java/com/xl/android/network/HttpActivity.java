package com.xl.android.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Http HttpClient  HttpURLConncetion
 */

public class HttpActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private WebView webView;
    private ImageView imageView;
    private TextView show;
    private String detail = "";
    private byte[] bytes;
    private final static String PIC_URL = "http://ww2.sinaimg.cn/large/7a8aed7bgw1evshgr5z3oj20hs0qo0vq.jpg";
    private final static String HTML_URL = "http://www.baidu.com";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                hideAllWidget();
                imageView.setVisibility(View.VISIBLE);
                if (bytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                }
            }
            if (msg.what == 2) {
                hideAllWidget();
                scrollView.setVisibility(View.VISIBLE);
                show.setText(detail);
            }
            if (msg.what == 3) {
                hideAllWidget();
                webView.setVisibility(View.VISIBLE);
                webView.loadDataWithBaseURL("", detail, "text/html", "UTF-8", "");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_layout);
        setTitle("Http网络协议");
        onHttpClient(HTML_URL);
        onHttpURLConncetion(HTML_URL);
        initUI();
    }

    private void initUI() {
        scrollView = (ScrollView) findViewById(R.id.scroll);
        webView = (WebView) findViewById(R.id.webView);
        imageView = (ImageView) findViewById(R.id.imgPic);
        TextView btn = (TextView) findViewById(R.id.txtMenu);
        show = (TextView) findViewById(R.id.txtshow);
        registerForContextMenu(btn);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.mains, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.load_pic) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bytes = getImage(PIC_URL);
                        handler.sendEmptyMessage(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        if (id == R.id.get_html) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        detail = getHtml(HTML_URL);
                        handler.sendEmptyMessage(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        if (id == R.id.html_to_web) {

            if (detail.equals("")) {
                Toast.makeText(HttpActivity.this, "先请求HTML!!!", Toast.LENGTH_SHORT).show();
            } else {
                handler.sendEmptyMessage(3);
            }
        }
        return true;
    }

    /**
     * HttpClient使用例子
     */

    private void onHttpClient(String url) {
        new HttpClient(url);
    }

    /**
     * HttpURLConncetion使用例子
     */

    private void onHttpURLConncetion(String url) {
        new HttpURLConncetion(url);
    }

    /**
     * 网络连接工具方法
     */

    //字节流转化为字节数组
    public static byte[] toByteArray(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; //指定缓存大小
        int lenght = 0;
        while ((lenght = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, lenght);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }

    //字节流转化为字符串
    public static String toString(InputStream inputStream) throws Exception {
        InputStreamReader reader = new InputStreamReader(inputStream, "utf8");
        BufferedReader buffer = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = buffer.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    // 定义一个获取网络图片数据的方法:
    public static byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        InputStream inStream = conn.getInputStream();
        byte[] bt = toByteArray(inStream);
        inStream.close();
        return bt;
    }

    // 获取网页的html源代码
    public static String getHtml(String path) throws Exception {
        URL url = new URL(path);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        InputStream in = conn.getInputStream();
        return toString(in);
    }

    /**
     * 定义一个隐藏所有控件的方法
     */
    private void hideAllWidget() {
        imageView.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
    }
}
