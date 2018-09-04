package com.xl.android.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.xl.android.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点击展开的ListView
 */

public class ExpanableListViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanable_listview_layout);
        setTitle("ExpandableListView");
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_listview);
        expandableListView.setAdapter(new MyExpandableListViewAdapter(this));
    }

    private List<String> getDate(String str){

        List<String> mlist = new ArrayList<>();
        try {
            InputStream inputStream =  getResources().getAssets().open(str);
            InputStreamReader streamReader = new InputStreamReader(inputStream,"utf8");
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while ((line = reader.readLine())!=null){
                mlist.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mlist;
    }



    private class MyExpandableListViewAdapter extends BaseExpandableListAdapter{

        LayoutInflater inflater;
        List<String> title = getDate("title.txt");
        List<String> content = getDate("content.txt");
        Map<String,String> map = new HashMap<>();

        private MyExpandableListViewAdapter(Context context){
            inflater = LayoutInflater.from(context);

            for (int i = 0; i <title.size(); i++) {
                map.put(title.get(i),content.get(i));
            }
        }

        @Override
        public int getGroupCount() {
            return title.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return map.size();
        }

        @Override
        public Object getGroup(int i) {
            return title.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return map.get(title.get(i));
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
            // 获取主列表项视图
            GroupHolder mGroupHolder;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.expandable_group_item,null);
                mGroupHolder = new  GroupHolder();
                mGroupHolder.groupText = convertView.findViewById(R.id.group_id);
                convertView.setTag(mGroupHolder);
            }else{
                mGroupHolder = (GroupHolder) convertView.getTag();
            }
            mGroupHolder.groupText.setText(title.get(i));
            return convertView;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            // 获取子列表项视图
            ChildHolder childHolder;
            if(view == null){
                view = inflater.inflate(R.layout.expandable_child_item,null);
                childHolder = new ChildHolder();
                childHolder.childText = view.findViewById(R.id.child_id);
                view.setTag(childHolder);
            }else{
                childHolder = (ChildHolder) view.getTag();
            }
            childHolder.childText.setText(map.get(title.get(i)));
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    /**
     * ViewHolder机制
     *
     */
    private class GroupHolder
    {
        TextView groupText;
    }
    private class ChildHolder
    {
        TextView childText;
    }
}
