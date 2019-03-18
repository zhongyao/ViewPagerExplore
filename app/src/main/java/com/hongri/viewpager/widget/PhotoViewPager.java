package com.hongri.viewpager.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import com.hongri.viewpager.MyApplication;
import com.hongri.viewpager.util.DisplayUtil;
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
        DisplayUtil.getPhoneInfo((Activity)mContext, MyApplication.appContext);
        Logger.d(TAG + "PhotoViewPager");
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
}
