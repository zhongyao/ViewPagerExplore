package com.hongri.viewpager.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hongri.viewpager.R;
import com.hongri.viewpager.adapter.RecyclerAdapter;
import com.hongri.viewpager.widget.FlexibleViewPager;

import java.util.ArrayList;

/**
 * ViewPager2 布局Fragment
 */
public class ItemFragment extends BaseFragment {

    private static final String TAG = "ItemFragment";
    private LinearLayout rootView;
    private TextView title;
    private RecyclerView rv;
    private ArrayList<String> data;
    private RecyclerAdapter adapter;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view.findViewById(R.id.rootView);
        title = view.findViewById(R.id.title);
        rv = view.findViewById(R.id.rv);

        title.setText("Fragment-" + position);
        adapter = new RecyclerAdapter(getActivity());
        adapter.setListData(data);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rv.setAdapter(adapter);
    }

    @Override
    public int getCurrentType() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public String getTabTitle() {
        return "";
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public int getSubTabHeight() {
        return 0;
    }

    @Override
    public void setData(ArrayList<String> data) {
        this.data = data;
        if (adapter != null) {
            adapter.setListData(data);
            adapter.notifyDataSetChanged();
        } else {
            Log.e(TAG, "adapter is null");
        }
    }

    @Override
    public void setFragmentForPosition(FlexibleViewPager flexibleViewPager, int position) {
        this.position = position;
        flexibleViewPager.setViewForPosition(this, position);
    }
}