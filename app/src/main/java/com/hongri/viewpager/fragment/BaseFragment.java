package com.hongri.viewpager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.hongri.viewpager.R;
import com.hongri.viewpager.widget.FlexibleViewPager;

import java.util.ArrayList;

/**
 * BaseFragment
 */
public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    public abstract int getCurrentType();

    public abstract int getCurrentPosition();

    public abstract String getTabTitle();

    /**
     * 获取子布局根View
     *
     * @return
     */
    public abstract View getRootView();

    /**
     * 获取二级tab高度【没有则是0】
     *
     * @return
     */
    public abstract int getSubTabHeight();


    public abstract void setData(ArrayList<String> data);

    public abstract void setFragmentForPosition(FlexibleViewPager flexibleViewPager, int position);
}