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
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.xl.util.ActivityFragment;
import com.xl.util.BroadcastFragment;
import com.xl.util.ContentProvderFragment;
import com.xl.util.NetworkFragment;
import com.xl.util.SensorFragment;
import com.xl.util.ServersFragment;
import com.xl.util.ViewFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment activity, broadcast, contentprovder, servers, view, sensor, network;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Activity");
        initView();
        manager = getFragmentManager();
        activity = new ActivityFragment();
        FragmentTransaction transactions = manager.beginTransaction();
        transactions.add(R.id.fragment_frame_layout, activity).commit();
    }

    private void hideFragment(FragmentTransaction transaction) {

        if (activity != null) {
            transaction.hide(activity);
        }
        if (broadcast != null) {
            transaction.hide(broadcast);
        }
        if (contentprovder != null) {
            transaction.hide(contentprovder);
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
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
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
            transaction.show(activity);

        } else if (id == R.id.nav_gallery) {
            setTitle("BroadcastReceiver");
            if (broadcast == null) {
                broadcast = new BroadcastFragment();
                transaction.add(R.id.fragment_frame_layout, broadcast);
            } else {
                transaction.show(broadcast);
            }


        } else if (id == R.id.nav_slideshow) {
            setTitle("ContentProvder");
            if (contentprovder == null) {
                contentprovder = new ContentProvderFragment();
                transaction.add(R.id.fragment_frame_layout, contentprovder);
            } else {
                transaction.show(contentprovder);
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
            setTitle("View");
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

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
