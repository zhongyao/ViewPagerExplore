package com.hongri.viewpager.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.hongri.viewpager.R;
import com.hongri.viewpager.adapter.StableFragmentPagerAdapter;
import com.hongri.viewpager.fragment.ItemFragment;
import com.hongri.viewpager.util.DataUtil;
import com.hongri.viewpager.widget.FlexibleViewPager;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING;

/**
 * ViewPager示例
 */
public class ViewPagerActivity extends AppCompatActivity {
    private static final String TAG = "ViewPagerActivity";
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

                /**
                 * 从前往后滑：
                 * 滑动页面从始至终一直会调用，滑动到最后才会 position + 1 ； positionOffset从小变大
                 *
                 * 从后往前滑：
                 * 滑动页面从始至终一直会调用，从一开始就会调用 position - 1 ； positionOffset从大变小
                 */
                Log.d(TAG, "onPageScrolled --- position:" + position + " positionOffset:" + positionOffset + " positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

                /**
                 * 一般情况下当onPageScrolled中的值positionOffset滑动0.5左右的时候【即滑动一半的时候】，onPageSelected方法会触发。
                 */

                Log.d(TAG, "onPageSelected ----- position:" + position);
                //每次选中item的时候，重置该item的高度
                flexibleViewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged ------- state:" + state);
                if (state == SCROLL_STATE_IDLE) {
                    //停止状态
                } else if (state == SCROLL_STATE_DRAGGING) {
                    //正在拖拽
                } else if (state == SCROLL_STATE_SETTLING) {
                    //正在惯性滑动
                }
            }
        });

    }
}
