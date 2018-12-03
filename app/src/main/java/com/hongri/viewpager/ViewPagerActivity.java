package com.hongri.viewpager;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hongri.viewpager.adapter.MyPagerAdapter;
import com.hongri.viewpager.util.DataUtil;
import com.hongri.viewpager.util.ToastUtil;
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
    private int mCurrentPosition = 0;

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

            /**
             * 添加手势事件
             */
            final GestureDetector gestureDetector = new GestureDetector(this, new SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    ToastUtil.showToast(ViewPagerActivity.this, "图片：" + mCurrentPosition + " 单击事件");
                    return super.onSingleTapConfirmed(e);
                }

                /**
                 * 双击时放大/缩小图片
                 * @param e
                 * @return
                 */
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    ToastUtil.showToast(ViewPagerActivity.this, "图片：" + mCurrentPosition + " 双击事件");
                    if (((ImageView)(dataLists.get(mCurrentPosition))).getScaleType() != ScaleType.CENTER_CROP) {
                        ((ImageView)(dataLists.get(mCurrentPosition))).setScaleType(ScaleType.CENTER_CROP);
                    } else {
                        ((ImageView)(dataLists.get(mCurrentPosition))).setScaleType(ScaleType.FIT_CENTER);
                    }
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    return super.onDoubleTapEvent(e);
                }
            });

            mImageView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });

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

            /**
             * 滑动时初始化原始图片大小
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                for (int i = 0; i < dataLists.size(); i++) {
                    if (((ImageView)(dataLists.get(i))).getScaleType() != ScaleType.FIT_CENTER) {
                        ((ImageView)(dataLists.get(i))).setScaleType(ScaleType.FIT_CENTER);
                    }
                }
                mTextView.setText(DataUtil.getDescriptions()[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
