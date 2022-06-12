package com.hongri.viewpager.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.hongri.viewpager.R;

/**
 * ViewPager示例
 */
public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<String> title;
    private List<View> content;
    private LayoutInflater inflater;
    private MyViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        content = new ArrayList<>();
        inflater = LayoutInflater.from(this);
        /**
         * 添加内容content
         */
        content.add(inflater.inflate(R.layout.f1, null));
        content.add(inflater.inflate(R.layout.f2, null));
        content.add(inflater.inflate(R.layout.f3, null));

        /**
         * 添加每个内容对应的标题title
         */
        title = new ArrayList<>();
        title.add("title1");
        title.add("title2");
        title.add("title3");

        adapter = new MyViewPagerAdapter(this, content, title);
        viewPager.setAdapter(adapter);

    }
}
