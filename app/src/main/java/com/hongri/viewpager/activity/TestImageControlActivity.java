package com.hongri.viewpager.activity;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.hongri.viewpager.R;
import com.hongri.viewpager.util.Logger;
import com.hongri.viewpager.util.ToastUtil;
import com.hongri.viewpager.widget.CustomImageView;
import com.hongri.viewpager.widget.CustomImageView.ICustomMethod;

/**
 * @author hongri
 */
public class TestImageControlActivity extends AppCompatActivity {

    private CustomImageView imgControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_image);

        imgControl = findViewById(R.id.imageControl);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        init();
    }

    private void init() {
        Bitmap bmp;
        if (imgControl.getDrawingCache() != null) {
            bmp = Bitmap.createBitmap(imgControl.getDrawingCache());
        } else {
            bmp = ((BitmapDrawable)imgControl.getDrawable()).getBitmap();
        }

        /**
         * 状态栏高度
         */
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        /**
         * 获取屏幕的宽高
         */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int screenH = dm.heightPixels - statusBarHeight;
        //int screenH = dm.heightPixels;

        /**
         * 获取屏幕的真实宽高(高度+导航栏)
         */
        int realScreenH = 0;
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            realScreenH = dm.heightPixels;
        }


        /**
         * 标题栏高度
         */
        View v = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        int contentTop = v.getTop();
        int titleBarHeight = contentTop - statusBarHeight;

        /**
         * 获取虚拟导航栏高度
         */
        int navigationBarH = realScreenH - screenH;

        Logger.d("screenW:" + screenW + "\n" + "screenH:" + screenH + "\n" + "realScreenH:" + realScreenH
            + "\n" + "statusBarHeight:" + statusBarHeight + "\n"
            + "titleBarHeight:" + titleBarHeight + "\n" + "navigationBarH:" + navigationBarH);

        if (bmp != null) {
            imgControl.imageInit(bmp, screenW, screenH, statusBarHeight,
                new ICustomMethod() {

                    @Override
                    public void customMethod(Boolean currentStatus) {
                        // 当图片处于放大或缩小状态时，控制标题是否显示
                        if (currentStatus) {
                            //llTitle.setVisibility(View.GONE);
                        } else {
                            //llTitle.setVisibility(View.VISIBLE);
                        }
                    }
                });
        } else {
            ToastUtil.showToast(TestImageControlActivity.this, "图片加载失败，请稍候再试！");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                imgControl.mouseDown(event);
                break;

            /**
             * 非第一个点按下
             */
            case MotionEvent.ACTION_POINTER_DOWN:

                imgControl.mousePointDown(event);

                break;
            case MotionEvent.ACTION_MOVE:
                imgControl.mouseMove(event);

                break;

            case MotionEvent.ACTION_UP:
                imgControl.mouseUp();
                break;
            default:
                break;

        }

        return super.onTouchEvent(event);
    }
}
