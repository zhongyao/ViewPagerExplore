package com.hongri.viewpager.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hongri.viewpager.R;
import com.hongri.viewpager.util.BitmapUtil;
import com.hongri.viewpager.util.FastBlur;
import com.hongri.viewpager.util.Logger;

/**
 * 高斯模糊处理Activity
 *
 * @author hongri
 */
public class BlurActivity extends AppCompatActivity {

    private ImageView iv;

    private static final int radius = 10;
    private long startTime, endTime, timeConsuming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);

        iv = findViewById(R.id.iv);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eye);
        Bitmap scaledBitmap = BitmapUtil.pressBitmapInScaledBitmap(bitmap);

        startTime = System.currentTimeMillis();
        Bitmap blurBitmap = FastBlur.doBlur(scaledBitmap, radius, false);
        endTime = System.currentTimeMillis();

        timeConsuming = endTime - startTime;
        Logger.d("高斯耗时：" + timeConsuming + "ms");
        iv.setImageBitmap(blurBitmap);

    }
}
