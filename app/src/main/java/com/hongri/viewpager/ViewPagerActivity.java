package com.hongri.viewpager;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hongri.viewpager.Interface.STPhotoSaveCallBack;
import com.hongri.viewpager.adapter.MyPagerAdapter;
import com.hongri.viewpager.photoview.PhotoViewAttacher.OnScrollUpDownListener;
import com.hongri.viewpager.photoview.PhotoViewAttacher.OnViewTapListener;
import com.hongri.viewpager.util.DataUtil;
import com.hongri.viewpager.util.ImageUtil;
import com.hongri.viewpager.util.Logger;
import com.hongri.viewpager.util.ResHelper;
import com.hongri.viewpager.util.ToastUtil;
import com.hongri.viewpager.widget.CustomImageView;
import com.hongri.viewpager.widget.CustomPhotoView;

/**
 * @author hongri
 *         参考：https://www.jb51.net/article/106272.htm
 */
public class ViewPagerActivity extends AppCompatActivity implements STPhotoSaveCallBack {

    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private LinearLayout indicatorContainer;
    private ArrayList<View> dataLists = new ArrayList<>();
    private CustomPhotoView mImageView;
    private TextView mTextView;
    private final int IMAGE_SIZE = 5;
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
            if (i == 2) {
                mImageView.setScaleType(ScaleType.CENTER_CROP);
            } else {
                mImageView.setScaleType(ScaleType.FIT_CENTER);
            }
            mImageView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToastUtil.showToast(ViewPagerActivity.this, "长按事件");
                    return true;
                }
            });

            mImageView.setOnViewTapListener(new OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    ToastUtil.showToast(ViewPagerActivity.this, "单击事件，点击图片之外区域也会触发");
                }
            });

            mImageView.setOnScrollListener(new OnScrollUpDownListener() {
                @Override
                public void onScrollUp(float distanceX, float distanceY) {
                    Logger.d("upupupupup");
                }

                @Override
                public void onScrollDown(float distanceX, float distanceY) {
                    Logger.d("downdowndown");
                }
            });

            dataLists.add(mImageView);
        }

        /**
         * 添加描述文案
         */
        mTextView = new TextView(this);
        final LinearLayout.LayoutParams mTextParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);
        mTextParams.gravity = Gravity.BOTTOM;
        mTextView.setLayoutParams(mTextParams);
        mTextView.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        mTextView.setTextColor(Color.GREEN);
        mTextView.setText(DataUtil.getDescriptions()[0]);
        mTextView.setOnClickListener(new TextOnClickListener());
        GradientDrawable gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM,
            new int[] {ResHelper.getColor(R.integer.alpha_3, R.color.default_gray),
                ResHelper.getColor(R.integer.alpha_100, R.color.default_gray)});
        indicatorContainer.addView(mTextView);
        indicatorContainer.setBackgroundDrawable(gradientDrawable);
        //或使用xml文件定义
        //indicatorContainer.setBackground(getResources().getDrawable(R.drawable.indicator_bg));

        adapter = new MyPagerAdapter(this, dataLists);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
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
                mTextView.setText(DataUtil.getDescriptions()[position] + " 点击text保持图片");
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
        return true;
    }

    @Override
    public void savePhotoSuccess() {
        ToastUtil.showToast(this, "保持成功");
    }

    @Override
    public void savePhotoFailure() {
        ToastUtil.showToast(this, "保存失败");
    }

    private class TextOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            if (mCurrentPosition != 3) {
                ImageUtil.saveBmp2Gallery(ViewPagerActivity.this, ImageUtil.drawableToBitmap(((ImageView)dataLists.get(
                    mCurrentPosition))
                    .getDrawable()), DataUtil.getImageUrls()[mCurrentPosition], ViewPagerActivity.this);
            } else {
                ImageUtil.saveGif2Gallery(ViewPagerActivity.this, ImageUtil.drawableToBitmap(((ImageView)dataLists.get(
                    mCurrentPosition))
                    .getDrawable()),((CustomPhotoView)dataLists.get(mCurrentPosition)).getBytes(), DataUtil.getImageUrls()[mCurrentPosition], ViewPagerActivity.this);
            }
        }
    }

}
