package com.hongri.viewpager.activity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.hongri.viewpager.R;
import com.hongri.viewpager.util.Logger;

/**
 * @author hongri
 * 手势处理Activity
 */
public class GestureActivity extends AppCompatActivity implements OnTouchListener{

    private ImageView iv;
    private ScaleGestureDetector mScaleGestureDetector;
    private static final String TAG = GestureDetector.class.getSimpleName();

    private Matrix matrix = new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        iv = findViewById(R.id.iv);
        iv.setScaleType(ScaleType.MATRIX);

        iv.setOnTouchListener(this);

        mScaleGestureDetector = new ScaleGestureDetector(this, new OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                Logger.d(TAG + " onScale");
                float factor = detector.getScaleFactor();
                matrix.postScale(factor, factor, detector.getFocusX(), detector.getFocusY());
                iv.setImageMatrix(matrix);
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                Logger.d(TAG + " onScaleBegin");
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                Logger.d(TAG + " onScaleEnd");
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //some code....
                break;
            case MotionEvent.ACTION_UP:
                v.performClick();
                break;
            default:
                break;
        }
        return true;
    }
}
