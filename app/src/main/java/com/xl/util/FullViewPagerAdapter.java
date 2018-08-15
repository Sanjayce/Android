package com.xl.util;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * FullViewPagerAdapter
 */

public class FullViewPagerAdapter extends PagerAdapter {

    private List<View> mlist;

    public FullViewPagerAdapter(List<View> mlist){
        this.mlist = mlist;
    }


    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mlist.get(position));
        return mlist.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(mlist.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
