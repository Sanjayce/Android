package com.xl.android.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;

/**
 * Fragment基础用法
 */

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private MyFragment1 fragment1;
    private MyFragment2 fragment2;
    private MyFragment3 fragment3;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        initUI();
        manager = getFragmentManager();

    }

    /**
     * 初始化布局控件
     */

    private void initUI() {
        findViewById(R.id.fragment_button1).setOnClickListener(this);
        findViewById(R.id.fragment_button2).setOnClickListener(this);
        findViewById(R.id.fragment_button3).setOnClickListener(this);
    }


    /**
     * 隐藏Fragment
     */

    private void hitiAllFragment(FragmentTransaction transaction) {

        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
        if (fragment3 != null) {
            transaction.hide(fragment3);
        }
    }



    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = manager.beginTransaction();
        hitiAllFragment(transaction);
        switch (view.getId()) {
            case R.id.fragment_button1:
                if(fragment1 == null){
                    fragment1 = new MyFragment1();
                    transaction.add(R.id.fragment_frame,fragment1);
                }else {
                    transaction.show(fragment1);
                }

                break;
            case R.id.fragment_button2:
                if(fragment2 == null){
                    fragment2 = new MyFragment2();
                    transaction.add(R.id.fragment_frame,fragment2);
                }else {
                    transaction.show(fragment2);
                }
                break;
            case R.id.fragment_button3:
                if(fragment3 == null){
                    fragment3 = new MyFragment3();
                    transaction.add(R.id.fragment_frame,fragment3);
                }else {
                    transaction.show(fragment3);
                }
                break;
        }
        transaction.commit();
    }


    public static class MyFragment1 extends Fragment {

        private View view;

        public MyFragment1() {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_item_layout, container, false);
            setView(view);
            return view;
        }

        public void setView(View view) {
            this.view = view;
            ((TextView) view.findViewById(R.id.fragment_tv)).setText("Fragment1");
        }
    }

    public static class MyFragment2 extends Fragment {

        private View view;

        public MyFragment2() {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_item_layout, container, false);
            setView(view);
            return view;
        }

        public void setView(View view) {
            this.view = view;
            ((TextView) view.findViewById(R.id.fragment_tv)).setText("Fragment2");
        }
    }

    public static class MyFragment3 extends Fragment {

        private View view;

        public MyFragment3() {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_item_layout, container, false);
            setView(view);
            return view;
        }

        public void setView(View view) {
            this.view = view;
            ((TextView) view.findViewById(R.id.fragment_tv)).setText("Fragment3");
        }
    }
}
