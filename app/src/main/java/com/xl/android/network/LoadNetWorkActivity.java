package com.xl.android.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xl.android.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过网络地址解析json数据，并显示到列表
 * 技术: json解析，AsyncTask异步加载，http网络连接，BaseAdapter适配器,Bean数据封装
 */

public class LoadNetWorkActivity extends AppCompatActivity {
    private ListView list_item;
    private ProgressBar load;
    private static final String web = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_network_layout);
        setTitle("Moon");
        initUI();
        MyAsyncTasks tasks =  new MyAsyncTasks();
        tasks.execute(web);
    }

    private void initUI() {
        list_item = (ListView) findViewById(R.id.load_net_listview);
        load = (ProgressBar) findViewById(R.id.load_net_progressBar);
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    /**
     * json解析
     */
    private List<ItemBean> getJsonData(String str){
        ItemBean  beans ;
        List<ItemBean> list = new ArrayList<>();
        try {
            URL url = new URL(str);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(1000*10);
            conn.setConnectTimeout(1000*10);
            conn.connect();
            String jsonData = toString(conn.getInputStream());
            //解析json
            JSONObject object = new JSONObject(jsonData);
            JSONArray array =  object.getJSONArray("data");
            for (int i = 0;i< array.length();i++){
                beans = new ItemBean();
                JSONObject subobject = (JSONObject) array.get(i);
                //网络数据传递到listview中
                beans.setIconid(toByteArray(subobject.getString("picSmall")));
                beans.setTitleid(subobject.getString("name"));
                beans.setContentid(subobject.getString("description"));
                list.add(beans);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }

    private Bitmap toByteArray(String str){
        Bitmap bitmap =null;
        HttpURLConnection huc ;
        BufferedInputStream bis;
        try {
            huc = (HttpURLConnection) new URL(str).openConnection();
            bis = new BufferedInputStream(huc.getInputStream());
            bitmap = BitmapFactory.decodeStream(bis); //返回bitmap类型的值
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private String toString (InputStream stream){
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(stream,"utf8");
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine())!= null){
                builder.append(line);
            }
            reader.close();
            isr.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * AsyncTask异步加载
     */

    private class MyAsyncTasks extends AsyncTask<String,Integer,List<ItemBean>>{

        @Override
        protected List<ItemBean> doInBackground(String... strings) {
            return getJsonData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<ItemBean> itemBeen) {
            super.onPostExecute(itemBeen);
            load.setVisibility(View.GONE);
            MyAdapter adapter = new MyAdapter(LoadNetWorkActivity.this,itemBeen);
            list_item.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            load.setProgress(values[0]);
        }
    }
}

/**
 * BaseAdapter适配器
 */

class MyAdapter extends BaseAdapter{

    private List<ItemBean> lists;
    private LayoutInflater mlayoutinflater;

    public MyAdapter(Context context,List<ItemBean> list){
        this.lists = list;
        mlayoutinflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            view = mlayoutinflater.inflate(R.layout.load_network_item_layout,viewGroup,false);
            viewHolder.title = view.findViewById(R.id.load_net_item_title);
            viewHolder.content = view.findViewById(R.id.load_net_item_content);
            viewHolder.pic = view.findViewById(R.id.load_net_item_pic);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.pic.setImageBitmap(lists.get(i).getIconid());
        viewHolder.title.setText(lists.get(i).getTitleid());
        viewHolder.content.setText(lists.get(i).getContentid());
        return view;
    }
}

/**
 * 缓存机制ViewHolder
 */
class ViewHolder{
    public TextView title,content;
    public ImageView pic;
}

/**
 * Bean数据封装
 */
class ItemBean{
    private Bitmap iconid;
    private String titleid;
    private String contentid;

    public ItemBean() {
    }

    public void setIconid(Bitmap iconid) {
        this.iconid = iconid;
    }

    public Bitmap getIconid() {
        return iconid;
    }


    public void setTitleid(String titleid) {
        this.titleid = titleid;
    }

    public String getTitleid() {
        return titleid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getContentid() {
        return contentid;
    }
}

