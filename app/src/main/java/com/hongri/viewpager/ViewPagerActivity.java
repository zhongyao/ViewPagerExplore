package com.hongri.viewpager;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hongri.viewpager.adapter.MyPagerAdapter;
import com.hongri.viewpager.util.DataUtil;
import com.hongri.viewpager.widget.CustomImageView;

/**
 * @author hongri
 */
public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private LinearLayout indicatorContainer;
    private ArrayList<View> dataLists = new ArrayList<>();
    private CustomImageView mImageView;
    private TextView mTextView;
    private final int IMAGE_SIZE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.viewPager);
        indicatorContainer = findViewById(R.id.indicatorContainer);

        /**
         * 添加图片资源
         */
        for (int i = 0; i < IMAGE_SIZE; i++) {
            mImageView = new CustomImageView(this);
            LayoutParams mImageParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mImageView.setLayoutParams(mImageParams);
            mImageView.setImageResource(DataUtil.getImageResource()[i]);

            dataLists.add(mImageView);
        }

        /**
         * 添加描述文案
         */
        mTextView = new TextView(this);
        LinearLayout.LayoutParams mTextParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);
        mTextView.setLayoutParams(mTextParams);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextColor(Color.GREEN);
        mTextView.setText(DataUtil.getDescriptions()[0]);
        indicatorContainer.addView(mTextView);


        adapter = new MyPagerAdapter(this, dataLists);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTextView.setText(DataUtil.getDescriptions()[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
