package com.hongri.viewpager;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import com.hongri.viewpager.widget.BigImageView;

public class LongImageActivity extends AppCompatActivity {

    private ImageView mImageView;
    private BigImageView mBigImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_long_image_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mImageView = findViewById(R.id.iv);
        mBigImageView = findViewById(R.id.bigImageView);

        //initImageView();

        initBigImageView();
    }

    private void initImageView() {
        try {

            /**
             * 获得图片的宽高
             */
            InputStream inputStream = getAssets().open("avatar.jpg");
            BitmapFactory.Options tempOptions = new Options();
            tempOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, tempOptions);
            int width = tempOptions.outWidth;
            int height = tempOptions.outHeight;

            /**
             * 设置显示图片的中心区域
             */
            BitmapRegionDecoder regionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new Options();
            options.inPreferredConfig = Config.RGB_565;
            Rect rect = new Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100);
            Bitmap bitmap = regionDecoder.decodeRegion(rect, options);
            mImageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initBigImageView() {
        InputStream inputStream;
        try {
            inputStream = getAssets().open("long_image_map.jpg");
            mBigImageView.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
