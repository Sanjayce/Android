package com.xl.android.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ListView 的普通用法，以及下拉刷新，上拉加载
 */

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,GetPullToRefrlashDate {

    private ToRefrash lv;
    private SimpleAdapter sa;
    private List<Map<String, Object>> datalist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        setTitle("ListView");

        lv = (ToRefrash) findViewById(R.id.listView);

        datalist = new ArrayList<>();
        sa = new SimpleAdapter(
                this,
                getData(),
                R.layout.item,
                new String[]{"imageview1", "textview1", "textview2"},
                new int[]{R.id.iv_image,R.id.tv_title, R.id.tv_content});

        lv.setAdapter(sa);
        lv.setOnItemClickListener(this);
        registerForContextMenu(lv);
        lv.onUseInterface(this);
    }

    /**
     * 列表点击监听事件
     */

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Toast.makeText(ListViewActivity.this, "*****", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu,menu);
    }

    /**
     * 下拉刷新
     */

    @Override
    public void onRefrlash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onPullToRefrashData();
                lv.toppadding(lv.headerHeight);
            }
        }, 2000);
    }

    /**
     * 上划更新
     */

    @Override
    public void onScorlls() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onUpToScrollDoing();
                lv.footer.findViewById(R.id.laonding_layout).setVisibility(View.GONE);
            }
        }, 2000);
    }

    /**
     * 下拉刷新数据
     */

    private void onPullToRefrashData() {
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("imageview1", R.mipmap.ic_launcher);
            map.put("textview1", "刷新标题" + i);
            map.put("textview2", "刷新数据内容" + i);
            datalist.add(0, map);
            sa.notifyDataSetChanged();
        }
    }

    /**
     * 上划更新数据
     */

    private void onUpToScrollDoing() {
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("imageview1", R.mipmap.ic_launcher);
            map.put("textview1", "之前标题" + i);
            map.put("textview2", "之前数据内容" + i);
            datalist.add(map);
            sa.notifyDataSetChanged();
        }
    }

    /**
     * 列表原始数据
     */

    private List<Map<String, Object>> getData() {
        for (int i = 1; i <= 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("imageview1", R.mipmap.ic_launcher);
            map.put("textview1", "标题" + i);
            map.put("textview2", "数据内容" + i);
            datalist.add(map);
        }
        return datalist;
    }
}


class ToRefrash extends ListView implements AbsListView.OnScrollListener {

    public View header, footer;
    public int headerHeight, firstVisibleItem, scrollState, startY, state, lastVisibleItem, totalItemCount;
    public boolean isTop;
    public final int NONE = 0, PULL = 1, RELESE = 2, REFLASHING = 3;
    public GetPullToRefrlashDate gptr;

    public ToRefrash(Context context) {
        super(context);
        initView(context);
    }

    public ToRefrash(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ToRefrash(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * 获取头(尾)布局
     */
    public void initView(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.pull_to_refrash, null);
        footer = inflater.inflate(R.layout.up_to_laoding, null);

        onSetMeasure(header);// 设置头布局大小
        headerHeight = header.getMeasuredHeight();// 获取头布局高度
        toppadding(headerHeight);// 隐藏头布局

        footer.findViewById(R.id.laonding_layout).setVisibility(View.GONE);

        this.addHeaderView(header);//添加头视图
        this.addFooterView(footer);//添加尾视图

        this.setOnScrollListener(this);
    }

    /**
     * 设置头布局占布局文件大小
     */

    public void onSetMeasure(View view) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        int W = ViewGroup.LayoutParams.MATCH_PARENT;
        int H = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (params == null) {
            params = new ViewGroup.LayoutParams(W, H);
        }
        // 获取头布局宽
        int width = ViewGroup.getChildMeasureSpec(0, 0, params.width);
        // 获取头布局高
        int height;
        int tempHeight = params.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        // 设置头布局在布局文件里的占用大小
        view.measure(width, height);
    }

    /**
     * 隐藏/显示头布局
     */
    public void toppadding(int top) {
        int left = header.getPaddingLeft();
        int right = header.getPaddingRight();
        int bottom = header.getPaddingBottom();
        header.setPadding(left, -top, right, bottom);
    }

    /**
     * 列表滑动监听事件
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.lastVisibleItem = visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL && totalItemCount == firstVisibleItem + lastVisibleItem) {
            footer.findViewById(R.id.laonding_layout).setVisibility(View.VISIBLE);
            gptr.onScorlls();
        }
    }

    /**
     * 触摸事件监听器
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// 按下
                if (firstVisibleItem == 0) {
                    isTop = true;
                    startY = (int) event.getY();
                    state = NONE;
                }
                break;
            case MotionEvent.ACTION_MOVE:// 移动
                onRefrash(event);
                break;
            case MotionEvent.ACTION_UP:// 抬起
                if (state == REFLASHING) {
                    onReflashViewByState();
                    gptr.onRefrlash();
                } else if (state == PULL) {
                    state = NONE;
                    isTop = false;
                    onReflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 下拉刷新提示信息
     */

    private void onRefrash(MotionEvent event) {

        if (!isTop) {
            return;
        }
        int tempY = (int) event.getY();
        int space = tempY - startY;
        int toppading = space - headerHeight;//结果为负数

        switch (state) {
            case NONE:
                if (space > 0 && space < headerHeight - headerHeight / 2) {
                    state = PULL;
                    onReflashViewByState();
                }
                break;

            case PULL:
                if (space < headerHeight) {
                    toppadding(-toppading);
                    if (space < headerHeight && space > headerHeight - headerHeight / 2
                            && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                        state = PULL;
                        onReflashViewByState();
                    } else if (space > 0 && space < headerHeight - headerHeight / 2) {
                        state = NONE;
                        onReflashViewByState();
                    }
                } else if (space >= headerHeight) {
                    toppadding(-headerHeight);
                    state = RELESE;
                    onReflashViewByState();
                    state = REFLASHING;
                }
                break;
        }
    }

    /**
     * 下拉界面变更
     */

    private void onReflashViewByState() {
        ProgressBar pb = header.findViewById(R.id.refrash_pb);
        switch (state) {
            case NONE:
                toppadding(headerHeight);
                break;
            case PULL:
                pb.setVisibility(View.GONE);
                break;
            case RELESE:
                pb.setVisibility(View.GONE);
                break;
            case REFLASHING:
                toppadding(-headerHeight);
                pb.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 使用接口
     */

    public void onUseInterface(GetPullToRefrlashDate gptr) {
        this.gptr = gptr;
    }

}

/**
 * 接口类用于获取更新数据源
 */

interface GetPullToRefrlashDate {

    void onRefrlash();

    void onScorlls();
}
