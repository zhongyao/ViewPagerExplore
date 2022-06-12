package com.hongri.viewpager.widget;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hongri.viewpager.fragment.BaseFragment;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 高度自适应 ViewPager
 * Create by zhongyao on 2022/6/12
 *
 * 参考：https://cxybb.com/article/u014476720/81381728
 */
public class FlexibleViewPager extends ViewPager {

    private int currentIndex;
    private int height = 0;
    //保存View对应的索引
    private final HashMap<Integer, BaseFragment> mChildViews = new LinkedHashMap<>();

    public FlexibleViewPager(Context context) {
        super(context);
    }

    public FlexibleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildViews.size() > currentIndex) {
            BaseFragment child = mChildViews.get(currentIndex);
            if (child != null) {
                View rootView = child.getRootView();
                int subTabHeight = child.getSubTabHeight();
                if (rootView != null) {
                    rootView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    height = rootView.getMeasuredHeight();
                    height = height + subTabHeight;
                    Log.d("IndexViewPager", "height:" + height + " subTabHeight:" + subTabHeight);
                }
            }
        }
        if (mChildViews.size() != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int current) {
        currentIndex = current;
        if (mChildViews.size() > current) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            } else {
                layoutParams.height = height;
            }
            //此处是重点，会重新调用onMeasure方法
            setLayoutParams(layoutParams);
        }
    }

    public void setViewForPosition(BaseFragment fragment, int position) {
        mChildViews.put(position, fragment);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }
}
