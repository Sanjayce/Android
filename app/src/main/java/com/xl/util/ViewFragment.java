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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  BroadCast界面
 */

public class ViewFragment extends Fragment implements AdapterView.OnItemClickListener{


    private GridView grid;
    private SimpleAdapter simplea;
    private List<Map<String, Object>> datalist;

    // 设置数据源
    private int[] icon = { R.mipmap.ic_launcher };
    private String[] str = {"ListView", "Data/Time", "Spinner",
            "ProgressBar", "LognLayout", "Muil/Auto","Fragment", "ViewPager", "ViewFlipper", "ScrollView",
            "Gallery/ISW", "SeekBar","ToastMessage", "AlertDialog", "Notification",
            "OptionsMenu", "ContextMenu", "SubMenu", "Animation", "Animator",
            "SlidingMenu", "ToolBar", "SurfaceView", "DrawerLayout","ExpandableListView","RecyclerView"};

    public ViewFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_layout,container,false);
        init(view);
        return view;
    }

    private void init(View view) {

        grid = view.findViewById(R.id.view_layout);
        datalist = new ArrayList<>();
        simplea = new SimpleAdapter(
                getActivity().getApplication().getApplicationContext(),
                getData(),
                R.layout.simple_adapter,
                new String[] { "imageView", "textView" },
                new int[] {R.id.imageView, R.id.textView });
        grid.setAdapter(simplea);
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
        /**Intent[] activity = {
                new Intent(getActivity(), ListViews.class),
                new Intent(getActivity(), DataTimePinkers.class),
                new Intent(getActivity(), ProgassBars.class),
                new Intent(getActivity(), Fragments.class),
                new Intent(getActivity(), ViewPagers.class),
                new Intent(getActivity(), ViewFlippers.class),
                new Intent(getActivity(), ScorllViews.class),
                new Intent(getActivity(), GalleryImageSwitcher.class),
                new Intent(getActivity(), SeekBars.class),
                new Intent(getActivity(), Toasts.class),
                new Intent(getActivity(), AlertDialogs.class),
                new Intent(getActivity(), Notificetions.class),
                new Intent(getActivity(), OptionsMenus.class),
                new Intent(getActivity(), ContextMenus.class),
                new Intent(getActivity(), SubMenus.class),
                new Intent(getActivity(), Animations.class),
                new Intent(getActivity(), Animators.class),
                new Intent(getActivity(), SlidingMenus.class),
                new Intent(getActivity(), ToolBarActivity.class),
                new Intent(getActivity(), SurfaceActivity.class),
                new Intent(getActivity(), DrawerActivity.class),
                new Intent(getActivity(), ExpandListViews.class),
                new Intent(getActivity(), RecyclerViews.class)};

        for (int j = 0; j < activity.length; j++) {
            if (i == j) {
                startActivity(activity[j]);
            }
        }
         */
    }
}
