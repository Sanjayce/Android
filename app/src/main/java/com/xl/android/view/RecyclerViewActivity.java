package com.xl.android.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.Toast;
import com.xl.android.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView可反复使用的列表控件，类似于ListView+GridView
 */

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = new RecyclerView(this);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(recyclerView);
            onSetRecyclerViewLayout(3);
        }if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(recyclerView);
            onSetRecyclerViewLayout(4);
        }

        setTitle("RecyclerView");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecyclerViewAdapter(this, getData("item.json"));
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "OnClick:"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnLongClick(View view, int position) {
                adapter.moveItem(position,position+1);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_listview:
                onSetRecyclerViewLayout(3);
                break;
            case R.id.action_v_gridview:
                onSetRecyclerViewLayout(4);
                break;
            case R.id.action_h_gridview:
                onSetRecyclerViewLayout(5);
                break;
            case R.id.action_add:
                adapter.addItem(0);
                break;
            case R.id.action_delete:
                adapter.deleteItem(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 设置RecyclerView布局样式
    public void onSetRecyclerViewLayout(int idex) {
        if (idex == 3) {
            // List布局
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            // 设置Item间的间距
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.top = 2;
                }
            });
        } else if (idex == 4) {
            // Grid布局
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL, false));
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(2, 1, 2, 1);
                }
            });
        }else if(idex == 5){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.HORIZONTAL, false));
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(2, 1, 2, 1);
                }
            });
        }

    }

    //通过json数据格式，保存数据
    private List<String> getData(String filename){
        List<String> mlist = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        try {
            InputStream stream = getResources().getAssets().open(filename);
            InputStreamReader streamReader = new InputStreamReader(stream,"utf8");
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while ((line = reader.readLine())!= null){
                builder.append(line);
            }
            String str = builder.toString();
            JSONObject object = new JSONObject(str);
            JSONArray array = object.getJSONArray("json");
            for (int i=0;i<array.length();i++) {
                mlist.add(array.get(i).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mlist;
    }
}

class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>{

    private LayoutInflater inflater;
    private List<String> mlist;
    private OnItemClickListener onItemClickListener;

    //定义recycler view点击事件监听器接口
    public interface OnItemClickListener{

        void OnClick(View view ,int position);
        void OnLongClick(View view ,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClick){

        this.onItemClickListener = onItemClick;
    }

    public RecyclerViewAdapter(Context context,List<String> mlist){
        this.mlist = mlist;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recycler_item_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int position) {
        MyViewHolder myViewHolder = (MyViewHolder)viewHolder;
        myViewHolder.title.setText(mlist.get(position));
        //给自定义的接口绑定视图点击事件监听器
        if(onItemClickListener != null){
            //单击
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onItemClickListener.OnClick(viewHolder.itemView, position);
                }
            });
            //长按
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.OnLongClick(viewHolder.itemView, position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    //添加一个列表项
    public void addItem(int pos){
        mlist.add("添加数据");
        notifyItemInserted(pos);
    }
    //删除一个列表项
    public void deleteItem(int pos){
        for (int i = 0; i < mlist.size(); i++) {

            if(i == pos){
                mlist.remove(pos);
            }
        }
        notifyItemRemoved(pos);
    }
    //移动列表项
    public void moveItem(int fromPosition,int toPosition){
        notifyItemMoved(fromPosition, toPosition);
    }

    private class MyViewHolder extends ViewHolder {

        public Button title;

        private MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }
    }
}


