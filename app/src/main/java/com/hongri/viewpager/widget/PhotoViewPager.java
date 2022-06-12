package com.hongri.viewpager.widget;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

import com.hongri.viewpager.util.Logger;

/**
 * @author zhongyao
 * @date 2019/3/14
 */

public class PhotoViewPager extends ViewPager {

    private final String TAG = PhotoViewPager.class.getSimpleName() + "--";

    private Context mContext;
    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        Logger.d(TAG + "PhotoViewPager");
        //修改ViewPager的mTouchSlop值
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.d(TAG + "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Logger.d(TAG + "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.d(TAG + "onDraw");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Logger.d(TAG + "onWindowFocusChanged");
    }

    private void init() {
        try {
            Field field = ViewPager.class.getDeclaredField("mTouchSlop");
            field.setAccessible(true);
            field.setInt(this,10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
