package com.hongri.viewpager.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.hongri.viewpager.R;
import com.hongri.viewpager.fragment.ItemFragment;

import java.util.ArrayList;

/**
 * ViewPager2示例：
 *
 */
public class ViewPager2Activity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ArrayList<ItemFragment> itemFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);

        initView();

        initPager();
    }

    private void initView() {
        viewPager2 = findViewById(R.id.viewPager2);
    }

    private void initPager() {

        for (int i = 0; i < 3; i++) {
            ItemFragment itemFragment = new ItemFragment();
            itemFragments.add(itemFragment);
        }

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}