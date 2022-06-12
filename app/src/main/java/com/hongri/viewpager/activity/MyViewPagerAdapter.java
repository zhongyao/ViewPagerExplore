package com.hongri.viewpager.activity;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * ViewPager适配器
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<View> content;
    private List<String> title;

    public MyViewPagerAdapter(Context context, List<View> content,
                              List<String> title) {
        this.context = context;
        this.content = content;
        this.title = title;
    }

    /**
     * 初始化
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(content.get(position));
        return content.get(position);
    }

    /**
     * title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 销毁
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(content.get(position));
    }
}
