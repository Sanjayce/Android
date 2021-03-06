package com.xl.util;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.xl.android.R;
import com.xl.android.view.AnimationsActivity;
import com.xl.android.view.DataTimeActivity;
import com.xl.android.view.ExpanableListViewActivity;
import com.xl.android.view.FragmentActivity;
import com.xl.android.view.ListViewActivity;
import com.xl.android.view.MediaPlayerActivity;
import com.xl.android.view.ProgressBarActivity;
import com.xl.android.view.RecyclerViewActivity;
import com.xl.android.view.SeekBarActivity;
import com.xl.android.view.SimpleUIActivity;
import com.xl.android.view.SurfaceViewActivity;
import com.xl.android.view.ViewPagerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BroadCast界面
 */

public class ViewFragment extends Fragment implements AdapterView.OnItemClickListener {

    private List<Map<String, Object>> datalist;

    // 设置数据源
    private int[] icon = {R.mipmap.ic_launcher};
    private String[] str = {
            "Animations", "MediaPlayer",
            "SurfaceView","RecyclerView",
            "SimpleUI", "Data/Time",
            "ProgressBar", "SeekBar",
            "Fragment", "ViewPager",
            "ListView", "ExpandableLV"};

    public ViewFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_layout, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        GridView grid = view.findViewById(R.id.view_layout);
        datalist = new ArrayList<>();
        SimpleAdapter simpleadapter= new SimpleAdapter(
                getActivity().getApplication().getApplicationContext(),
                getData(),
                R.layout.simple_adapter,
                new String[]{"imageView", "textView"},
                new int[]{R.id.imageView, R.id.textView});
        grid.setAdapter(simpleadapter);
        grid.setOnItemClickListener(this);
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < str.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("imageView", icon[0]);
            map.put("textView", str[i]);
            datalist.add(map);
        }
        return datalist;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent[] activity = {
                new Intent(getActivity(), AnimationsActivity.class),
                new Intent(getActivity(), MediaPlayerActivity.class),
                new Intent(getActivity(), SurfaceViewActivity.class),
                new Intent(getActivity(), RecyclerViewActivity.class),
                new Intent(getActivity(), SimpleUIActivity.class),
                new Intent(getActivity(), DataTimeActivity.class),
                new Intent(getActivity(), ProgressBarActivity.class),
                new Intent(getActivity(), SeekBarActivity.class),
                new Intent(getActivity(), FragmentActivity.class),
                new Intent(getActivity(), ViewPagerActivity.class),
                new Intent(getActivity(), ListViewActivity.class),
                new Intent(getActivity(), ExpanableListViewActivity.class)
        };

        for (int j = 0; j < activity.length; j++) {
            if (i == j) {
                startActivity(activity[j]);
            }
        }
    }
}
