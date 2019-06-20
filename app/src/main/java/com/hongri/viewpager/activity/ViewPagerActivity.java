package com.hongri.viewpager.activity;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hongri.viewpager.MyApplication;
import com.hongri.viewpager.R;
import com.hongri.viewpager.callback.STPhotoSaveCallBack;
import com.hongri.viewpager.adapter.MyPagerAdapter;
import com.hongri.viewpager.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import com.hongri.viewpager.photoview.PhotoViewAttacher.OnScrollListener;
import com.hongri.viewpager.photoview.PhotoViewAttacher.OnViewTapListener;
import com.hongri.viewpager.util.DataUtil;
import com.hongri.viewpager.util.DisplayUtil;
import com.hongri.viewpager.util.ImageUtil;
import com.hongri.viewpager.util.Logger;
import com.hongri.viewpager.util.ResHelper;
import com.hongri.viewpager.util.ToastUtil;
import com.hongri.viewpager.widget.CustomImageView;
import com.hongri.viewpager.widget.CustomPhotoView;
import com.hongri.viewpager.widget.PhotoViewPager;

/**
 * @author hongri
 * 参考：https://www.jb51.net/article/106272.htm
 *
 * 请参考大图模式：
 * https://github.com/davemorrissey/subsampling-scale-image-view
 *
 * ImageView重用：
 * https://blog.csdn.net/hqiong208/article/details/53908002
 */
public class ViewPagerActivity extends AppCompatActivity implements STPhotoSaveCallBack {

    private PhotoViewPager mViewPager;
    private MyPagerAdapter mAdapter;
    private LinearLayout mIndicatorContainer;
    private ArrayList<View> mViewLists = new ArrayList<>();
    private CustomPhotoView mImageView;
    private TextView mTextView;
    /**
     * 要加载的图片数量
     */
    private final int IMAGE_URL_COUNT = DataUtil.getImageUrls().length;

    /**
     * 使用的ImageView数量
     */
    public static final int IMAGE_VIEW_COUNT = 3;
    private int mCurrentPosition = 0;
    private static final String TAG = ViewPagerActivity.class.getSimpleName();
    private int mViewIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_view_pager);

        mViewPager = findViewById(R.id.viewPager);
        mIndicatorContainer = findViewById(R.id.indicatorContainer);

        /**
         * 生成ImageView
         */
        for (int i = 0; i < IMAGE_VIEW_COUNT; i++) {
            mImageView = new CustomPhotoView(this);
            LayoutParams mImageParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mImageView.setLayoutParams(mImageParams);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.BLACK);
            drawable.setAlpha(255);
            drawable.setShape(GradientDrawable.RECTANGLE);
            mImageView.setBackground(drawable);
            if (i == 2) {
                mImageView.setScaleType(ScaleType.FIT_CENTER);
            } else {
                mImageView.setScaleType(ScaleType.FIT_CENTER);
            }
            mImageView.setOnLongClickListener(new PhotoOnLongClickListener());

            mImageView.setOnViewTapListener(new PhotoOnViewTapListener());

            mImageView.setOnScrollListener(new PhotoOnScrollListener());

            mImageView.setOnMatrixChangeListener(new PhotoOnMatrixChangedListener());

            mViewLists.add(mImageView);
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
        mTextView.setText("描述");
        mTextView.setOnClickListener(new TextOnClickListener());
        GradientDrawable gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM,
            new int[] {ResHelper.getColor(R.integer.alpha_3, R.color.default_gray),
                ResHelper.getColor(R.integer.alpha_100, R.color.default_gray)});
        mIndicatorContainer.addView(mTextView);
        mIndicatorContainer.setBackgroundDrawable(gradientDrawable);
        //或使用xml文件定义
        //mIndicatorContainer.setBackground(getResources().getDrawable(R.drawable.indicator_bg));

        mAdapter = new MyPagerAdapter(this, mViewLists, IMAGE_URL_COUNT, IMAGE_VIEW_COUNT);

        mViewPager.setAdapter(mAdapter);
        //mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 滑动时初始化原始图片大小
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                mViewIndex = position % IMAGE_VIEW_COUNT;
                mCurrentPosition = mViewIndex;
                CustomPhotoView mImageView = (CustomPhotoView)mViewLists.get(mViewIndex);
                for (int i = 0; i < mViewLists.size(); i++) {
                    if (((ImageView)(mViewLists.get(i))).getScaleType() != ScaleType.FIT_CENTER) {
                        ((ImageView)(mViewLists.get(i))).setScaleType(ScaleType.FIT_CENTER);
                    }
                }
                mImageView.setOnLongClickListener(new PhotoOnLongClickListener());

                mImageView.setOnViewTapListener(new PhotoOnViewTapListener());

                mImageView.setOnScrollListener(new PhotoOnScrollListener());

                mImageView.setOnMatrixChangeListener(new PhotoOnMatrixChangedListener());

                mTextView.setText("点击text保存图片：" + position);

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
                ((CustomImageView)(mViewLists.get(mCurrentPosition))).mouseDown(event);
                break;
            //非第一个点按下
            case MotionEvent.ACTION_POINTER_DOWN:
                ((CustomImageView)(mViewLists.get(mCurrentPosition))).mousePointDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                ((CustomImageView)(mViewLists.get(mCurrentPosition))).mouseMove(event);
                break;
            case MotionEvent.ACTION_UP:
                ((CustomImageView)(mViewLists.get(mCurrentPosition))).mouseUp();
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void savePhotoSuccess() {
        ToastUtil.showToast(this, "保存成功");
    }

    @Override
    public void savePhotoFailure() {
        ToastUtil.showToast(this, "保存失败");
    }

    private class TextOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            if (mCurrentPosition != 3) {
                ImageUtil.saveBmp2Gallery(ViewPagerActivity.this, ImageUtil.drawableToBitmap(((ImageView)mViewLists.get(
                    mCurrentPosition))
                    .getDrawable()), DataUtil.getImageUrls()[mCurrentPosition], ViewPagerActivity.this);
            } else {
                ImageUtil.saveGif2Gallery(ViewPagerActivity.this, ImageUtil.drawableToBitmap(((ImageView)mViewLists.get(
                    mCurrentPosition))
                        .getDrawable()), ((CustomPhotoView)mViewLists.get(mCurrentPosition)).getBytes(),
                    DataUtil.getImageUrls()[mCurrentPosition], ViewPagerActivity.this);
            }
        }
    }

    private class PhotoOnLongClickListener implements OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            ToastUtil.showToast(ViewPagerActivity.this, "长按事件");
            return true;
        }
    }

    private class PhotoOnViewTapListener implements OnViewTapListener {
        @Override
        public void onViewTap(View view, float x, float y) {
            ToastUtil.showToast(ViewPagerActivity.this, "单击事件，点击图片之外区域也会触发");
            finish();
        }
    }

    private class PhotoOnScrollListener implements OnScrollListener {
        @Override
        public void onScrollUp(float distanceX, float distanceY) {
            Logger.d("upupupupup");
        }

        @Override
        public void onScrollDown(float distanceX, float distanceY) {
            Logger.d("downdowndown");
        }

        @Override
        public void onScrollExit() {
            Logger.d("onScrollExit");
            finish();
        }
    }

    private class PhotoOnMatrixChangedListener implements OnMatrixChangedListener {
        @Override
        public void onMatrixChanged(RectF rect) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        initDeviceInfo(this);
    }

    private void initDeviceInfo(ViewPagerActivity activity) {
        /**
         * 双击大长图没有撑满屏幕问题总结：
         * 1、在Demo中用不同方法获取的获取的高度一样。
         * 2、在应用中MOTO通过getResources方法获取的高度是2272 而通过其他方法获取的高度为2368
         *    而华为mate9 pro手机获取的宽高正常
         */
        //DisplayUtil.getPhoneInfo(activity, MyApplication.appContext);
        //DisplayUtil.getPhoneInfo((Activity)mContext, MyApplication.appContext);
        DisplayUtil.getPhoneInfo(activity, MyApplication.appContext);

    }
}
