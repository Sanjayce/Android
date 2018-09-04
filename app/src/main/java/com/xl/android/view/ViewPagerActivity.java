package com.xl.android.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xl.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager的基本用法
 */

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_layout);
        setTitle("ViewPager");

        ViewPager vp = (ViewPager) findViewById(R.id.pager);
        PagerTabStrip pts = (PagerTabStrip) findViewById(R.id.tap);

        //创建数据源
        List<View> list = new ArrayList<>();
        List<String> titelist = new ArrayList<>();

        //将Layout布局转换为View对象
        View view1 = View.inflate(this,R.layout.viewpager_itemview_layout,null);
        ((TextView)view1.findViewById(R.id.view_pager_text)).setText("通讯录");
        View view2 = View.inflate(this,R.layout.viewpager_itemview_layout,null);
        ((TextView)view2.findViewById(R.id.view_pager_text)).setText("好友列表");
        View view3 = View.inflate(this,R.layout.viewpager_itemview_layout,null);
        ((TextView)view3.findViewById(R.id.view_pager_text)).setText("朋友圈");
        View view4 = View.inflate(this,R.layout.viewpager_itemview_layout,null);
        ((TextView)view4.findViewById(R.id.view_pager_text)).setText("设置");
        View view5 = View.inflate(this,R.layout.viewpager_itemview_layout,null);
        ((TextView)view5.findViewById(R.id.view_pager_text)).setText("其他");

        //add() 布局
        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
        list.add(view5);

        //设置标题内容
        titelist.add("通讯录");
        titelist.add("好友列表");
        titelist.add("朋友圈");
        titelist.add("设置");
        titelist.add("其他");

        //设置背景色
        pts.setBackgroundColor(Color.blue(R.color.colorPrimary));
        //设置标题下的横线是否显示
        pts.setDrawFullUnderline(false);
        //设置文字颜色
        pts.setTextColor(Color.WHITE);
        //设置文字下的粗线颜色
        pts.setTabIndicatorColor(Color.RED);

        //设置适配器
        MyPagerAdapter adapter = new MyPagerAdapter(list,titelist);
        //加载适配器
        vp.setAdapter(adapter);

        //设置监听事件OnPageChangeListener
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private class MyPagerAdapter extends PagerAdapter {

        private List<View>list;
        private List<String>titelist;

        private MyPagerAdapter(List<View>list,List<String>titelist) {
            this.list=list;
            this.titelist =titelist;
        }
        //数据源大小
        @Override
        public int getCount() {
            return list.size();
        }
        //判断是否来至于View的一个对象
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        //实例化一个对象
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //将list的一个对象位置传给他的父类ViewGroup
            container.addView(list.get(position));
            //并且返回这个位置
            return list.get(position);
        }
        //销毁对象
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁list的一个对象位置
            container.removeView(list.get(position));
        }
        //设置标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titelist.get(position);
        }

    }

}
