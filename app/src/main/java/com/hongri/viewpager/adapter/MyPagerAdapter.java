package com.hongri.viewpager.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zhongyao
 * @date 2018/12/2
 */

public class MyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<View> mDataLists;

    public MyPagerAdapter(Context context, ArrayList<View> dataLists) {
        mContext = context;
        mDataLists = dataLists;
    }

    @Override
    public int getCount() {
        return mDataLists.size() > 0 ? mDataLists.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(mDataLists.get(position));
        return mDataLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mDataLists.get(position));
    }
}
