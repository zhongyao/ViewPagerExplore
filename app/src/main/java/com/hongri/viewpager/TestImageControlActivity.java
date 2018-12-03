package com.hongri.viewpager;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
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
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //    WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int screenW = this.getWindowManager().getDefaultDisplay().getWidth();
        int screenH = this.getWindowManager().getDefaultDisplay().getHeight()
            - statusBarHeight;
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
