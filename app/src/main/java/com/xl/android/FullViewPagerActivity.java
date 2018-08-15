package com.xl.android;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xl.util.FullViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI
 */
public class FullViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private List<View> mlist;
    private TextView textView1, textView2, textView3, textView4;
    private FullViewPagerAdapter adapter;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private int count = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view_pager);
        viewPager = (ViewPager) findViewById(R.id.full_view_pager);
        initSubView();
        adapter = new FullViewPagerAdapter(mlist);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                textView1.setText("1");
                break;
            case 1:
                textView2.setText("2");
                break;
            case 2:
                textView3.setText("3");
                break;
            case 3:
                textView4.setText("4");
                timeUI();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    private void initSubView() {
        mlist = new ArrayList<>();

        View view1 = View.inflate(this, R.layout.full_view__pager_sub_view, null);
        textView1 = view1.findViewById(R.id.full_view_pager_text);
        View view2 = View.inflate(this, R.layout.full_view__pager_sub_view, null);
        textView2 = view2.findViewById(R.id.full_view_pager_text);
        View view3 = View.inflate(this, R.layout.full_view__pager_sub_view, null);
        textView3 = view3.findViewById(R.id.full_view_pager_text);
        View view4 = View.inflate(this, R.layout.full_view__pager_sub_view, null);
        textView4 = view4.findViewById(R.id.full_view_pager_text);

        mlist.add(view1);
        mlist.add(view2);
        mlist.add(view3);
        mlist.add(view4);
    }

    private void timeUI(){
        mTimer = new Timer();
        mTimerTask = new TimerTask() {

            @Override
            public void run() {
                updateUI();
                count--;
            }
        };
        mTimer.schedule(mTimerTask,1000);
    }


    private void updateUI() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (count<1){
                    startActivity(new Intent(FullViewPagerActivity.this, MainActivity.class));
                    finish();
                }
                textView4.setText(count + "");
            }
        });
    }
}
