package com.hongri.viewpager.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.hongri.viewpager.R;
import com.hongri.viewpager.adapter.StableFragmentPagerAdapter;
import com.hongri.viewpager.fragment.ItemFragment;
import com.hongri.viewpager.util.DataUtil;
import com.hongri.viewpager.widget.FlexibleViewPager;

/**
 * ViewPager示例
 */
public class ViewPagerActivity extends AppCompatActivity {
    private FlexibleViewPager flexibleViewPager;
    private final List<ItemFragment> fragments = new ArrayList<>();
    private StableFragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        flexibleViewPager = findViewById(R.id.viewPager);
        /**
         * 添加内容content
         */
        for (int i = 0; i < 3; i++) {
            ItemFragment itemFragment = new ItemFragment();
            itemFragment.setData(DataUtil.getArrayData(i));
            itemFragment.setFragmentForPosition(flexibleViewPager, i);
            fragments.add(itemFragment);
        }

        fragmentPagerAdapter = new StableFragmentPagerAdapter(getSupportFragmentManager());
        fragmentPagerAdapter.setFragmentList(fragments);
        flexibleViewPager.setAdapter(fragmentPagerAdapter);
        flexibleViewPager.setOffscreenPageLimit(fragments.size() - 1);
        fragmentPagerAdapter.notifyDataSetChanged();
        //初始化第一个Item的高度
        flexibleViewPager.resetHeight(0);

        flexibleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //每次选中item的时候，重置该item的高度
                flexibleViewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
