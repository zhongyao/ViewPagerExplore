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
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hongri.viewpager.adapter.MyPagerAdapter;
import com.hongri.viewpager.photoview.PhotoViewAttacher.OnPhotoTapListener;
import com.hongri.viewpager.util.DataUtil;
import com.hongri.viewpager.util.Logger;
import com.hongri.viewpager.util.ToastUtil;
import com.hongri.viewpager.widget.CustomImageView;
import com.hongri.viewpager.widget.CustomPhotoView;

/**
 * @author hongri
 */
public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private LinearLayout indicatorContainer;
    private ArrayList<View> dataLists = new ArrayList<>();
    private CustomPhotoView mImageView;
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
            mImageView = new CustomPhotoView(this);
            LayoutParams mImageParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mImageView.setLayoutParams(mImageParams);
            mImageView.setImageResource(DataUtil.getImageResource()[i]);
            mImageView.setScaleType(ScaleType.FIT_CENTER);
            mImageView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToastUtil.showToast(ViewPagerActivity.this, "长按事件");
                    return true;
                }
            });

            mImageView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    ToastUtil.showToast(ViewPagerActivity.this, "点击事件，真实项目中可关闭activity");
                }
            });

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
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    Logger.d("distanceX:" + distanceX + ";distanceY:" + distanceY);
                    if (Math.abs(distanceY) > Math.abs(distanceX)) {
                        //正在上下滑动
                        if (distanceY > 0) {
                            //正在向上滑动
                            Logger.d("正在向---上---滑动");
                        } else {
                            //正在向下滑动
                            Logger.d("正在向---下---滑动");
                        }
                    } else {
                        //正在左右滑动
                    }
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    return super.onDoubleTapEvent(e);
                }
            });

            //mImageView.setOnTouchListener(new OnTouchListener() {
            //    @Override
            //    public boolean onTouch(View v, MotionEvent event) {
            //        gestureDetector.onTouchEvent(event);
            //        return true;
            //    }
            //});

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                ((CustomImageView)(dataLists.get(mCurrentPosition))).mouseDown(event);
                break;
            //非第一个点按下
            case MotionEvent.ACTION_POINTER_DOWN:
                ((CustomImageView)(dataLists.get(mCurrentPosition))).mousePointDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                ((CustomImageView)(dataLists.get(mCurrentPosition))).mouseMove(event);
                break;
            case MotionEvent.ACTION_UP:
                ((CustomImageView)(dataLists.get(mCurrentPosition))).mouseUp();
                break;

            default:
                break;
        }
        return true/*super.onTouchEvent(event)*/;
    }
}
