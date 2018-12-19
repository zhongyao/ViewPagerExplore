package com.hongri.viewpager.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.hongri.viewpager.photoview.PhotoView;
import com.hongri.viewpager.util.DataUtil;

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
        return (mDataLists != null && mDataLists.size() > 0) ? mDataLists.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Glide.with(mContext).load(DataUtil.getImageUrls()[position])/*.placeholder(DataUtil.getImageResource()[i])*/.override(800, 800).into((PhotoView)mDataLists.get(position));
        container.addView(mDataLists.get(position));
        return mDataLists.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "哈哈" + position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mDataLists.get(position));
    }
}
