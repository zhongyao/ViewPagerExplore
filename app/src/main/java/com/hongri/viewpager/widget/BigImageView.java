package com.hongri.viewpager.widget;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

import com.hongri.viewpager.longimage.MoveGestureDetector;

/**
 * @author zhongyao
 * @date 2018/12/24
 * 参考：https://blog.csdn.net/lmj623565791/article/details/49300989
 * 通过BitmapRegionDecoder 让大图局部展示
 */

public class BigImageView extends AppCompatImageView {
    private static final String TAG = "BigImageView";

    private BitmapRegionDecoder mRegionDecoder;
    /**
     * 图片的宽度和高度
     */
    private int mImageWidth, mImageHeight;
    /**
     * 绘制的区域
     */
    private Rect mRect = new Rect();
    private static BitmapFactory.Options sOptions = new BitmapFactory.Options();

    private MoveGestureDetector mDetector;

    static {
        sOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public BigImageView(Context context) {
        this(context, null);

        init();
    }

    public BigImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mDetector = new MoveGestureDetector(getContext(), new MoveGestureDetector.SimpleMoveGestureDetector() {
            @Override
            public boolean onMove(MoveGestureDetector detector) {
                int moveX = (int)detector.getMoveX();
                int moveY = (int)detector.getMoveY();

                if (mImageWidth > getWidth()) {
                    Log.d(TAG, "mImageWidth:" + mImageWidth + " getWidth():" + getWidth() + " -moveX" + (-moveX));
                    mRect.offset(-moveX, 0);
                    checkWidth();
                    invalidate();
                }
                if (mImageHeight > getHeight()) {
                    Log.d(TAG, "mImageHeight:" + mImageHeight + " getHeight():" + getHeight() + " -moveY:" + (-moveY));
                    mRect.offset(0, -moveY);
                    checkHeight();
                    invalidate();
                }

                return true;
            }
        });
    }

    public void setInputStream(InputStream inputStream) {
        try {
            mRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            mImageWidth = mRegionDecoder.getWidth();
            mImageHeight = mRegionDecoder.getHeight();
            Log.d(TAG, "mImageWidth:" + mImageWidth + " mImageHeight:" + mImageHeight);

            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        Log.d(TAG, "onMeasure--->width:" + width + " height:" + height);
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        //默认直接显示图片的中心区域，可以自己去调节
        //mRect.left = imageWidth / 2 - width / 2;
        //mRect.top = imageHeight / 2 - height / 2;
        //mRect.right = mRect.left + width;
        //mRect.bottom = mRect.top + height;


        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mRect.left + 1080;
        mRect.bottom = mRect.top + 2000;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
        if (mRegionDecoder != null) {
            Bitmap bitmap = mRegionDecoder.decodeRegion(mRect, sOptions);
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onToucEvent(event);
        return true;
    }

    private void checkWidth() {

        Rect rect = mRect;
        int imageWidth = mImageWidth;

        if (rect.right > imageWidth) {
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }

        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
        }
    }

    private void checkHeight() {

        Rect rect = mRect;
        int imageHeight = mImageHeight;

        if (rect.bottom > imageHeight) {
            Log.d(TAG, "rect.bottom:" + rect.bottom + " imageHeight:" + imageHeight);
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }

        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }
}
