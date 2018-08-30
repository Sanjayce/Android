package com.xl.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xl.android.content.PreferenceActivitys;
import com.xl.util.ActivityFragment;
import com.xl.util.BroadcastFragment;
import com.xl.util.ContentProvderFragment;
import com.xl.util.NetworkFragment;
import com.xl.util.SensorFragment;
import com.xl.util.ServersFragment;
import com.xl.util.ViewFragment;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment mainFragment,activity, broadcastreceiver, contentprovider, servers, view, sensor, network;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        manager = getFragmentManager();
        mainFragment = new MainFragment();
        FragmentTransaction transactions = manager.beginTransaction();
        transactions.add(R.id.fragment_frame_layout, mainFragment).commit();
    }

    private void hideFragment(FragmentTransaction transaction) {

        transaction.remove(mainFragment);

        if (activity != null) {
            transaction.hide(activity);
        }
        if (broadcastreceiver != null) {
            transaction.hide(broadcastreceiver);
        }
        if (contentprovider != null) {
            transaction.hide(contentprovider);
        }
        if (servers != null) {
            transaction.hide(servers);
        }
        if (view != null) {
            transaction.hide(view);
        }
        if (sensor != null) {
            transaction.hide(sensor);
        }
        if (network != null) {
            transaction.hide(network);
        }

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        findViewById(R.id.fragment_frame_layout);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setIconsVisible(menu, true);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 解决menu不显示图标问题
     * @param menu
     * @param flag
     */
    private void setIconsVisible(Menu menu, boolean flag) {
        //判断menu是否为空
        if(menu != null) {
            try {
                //如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                //暴力访问该方法
                method.setAccessible(true);
                //调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //preference_activity
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), PreferenceActivitys.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        transaction = manager.beginTransaction();
        hideFragment(transaction);
        if (id == R.id.nav_camera) {
            setTitle("Activity");
            if(activity == null){
                activity = new ActivityFragment();
                transaction.add(R.id.fragment_frame_layout,activity);
            }
            transaction.show(activity);

        } else if (id == R.id.nav_gallery) {
            setTitle("BroadcastReceiver");
            if (broadcastreceiver == null) {
                broadcastreceiver = new BroadcastFragment();
                transaction.add(R.id.fragment_frame_layout, broadcastreceiver);
            } else {
                transaction.show(broadcastreceiver);
            }


        } else if (id == R.id.nav_slideshow) {
            setTitle("ContentProvider");
            if (contentprovider == null) {
                contentprovider = new ContentProvderFragment();
                transaction.add(R.id.fragment_frame_layout, contentprovider);
            } else {
                transaction.show(contentprovider);
            }

        } else if (id == R.id.nav_manage) {
            setTitle("Servers");
            if (servers == null) {
                servers = new ServersFragment();
                transaction.add(R.id.fragment_frame_layout, servers);
            } else {
                transaction.show(servers);
            }

        } else if (id == R.id.nav_share) {
            setTitle("ViewUI");
            if (view == null) {
                view = new ViewFragment();
                transaction.add(R.id.fragment_frame_layout, view);
            } else {
                transaction.show(view);
            }

        } else if (id == R.id.nav_send) {
            setTitle("Sensor");
            if (sensor == null) {
                sensor = new SensorFragment();
                transaction.add(R.id.fragment_frame_layout, sensor);
            } else {
                transaction.show(sensor);
            }
        } else if (id == R.id.nav_net) {
            setTitle("Network");
            if (network == null) {
                network = new NetworkFragment();
                transaction.add(R.id.fragment_frame_layout, network);
            } else {
                transaction.show(network);
            }
        }

        transaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
