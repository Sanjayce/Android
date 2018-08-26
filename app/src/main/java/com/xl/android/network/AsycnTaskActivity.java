package com.xl.android.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.xl.android.R;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 异步网络加载AsyncTask<void,void,void>
 */

public class AsycnTaskActivity extends AppCompatActivity {

    private ImageView pic;
    private ProgressBar progress;
    private TextView text;

    private static final String url = "http://ww2.sinaimg.cn/large/7a8aed7bgw1evshgr5z3oj20hs0qo0vq.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asycn_task_layout);
        setTitle("AsyncTask");
        pic = (ImageView) findViewById(R.id.asycn_image);
        progress = (ProgressBar) findViewById(R.id.asycn_progress);
        text = (TextView) findViewById(R.id.asycn_text);
        MyAsycnTask asycnTask = new MyAsycnTask();
        asycnTask.execute(url);
    }

   private class MyAsycnTask extends AsyncTask<String,Integer,Bitmap>{


        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            String web = strings[0];
            publishProgress(100);
            try {
                URL url = new URL(web);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(6000);
                connection.setReadTimeout(6000);
                connection.connect();
                InputStream inputStream =  connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                Thread.sleep(100);
                bufferedInputStream.close();
                inputStream.close();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(progress.getProgress() == progress.getMax()){
                progress.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                pic.setImageBitmap(bitmap);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
            text.setText(values[0]+"%");
        }
    }
}


