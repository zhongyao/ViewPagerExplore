package com.hongri.viewpager.activity;

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
import com.hongri.viewpager.R;
import com.hongri.viewpager.util.DeviceOpenGLUtil;
import com.hongri.viewpager.util.Logger;
import com.hongri.viewpager.widget.BigImageView;

/**
 * 超长(大)图Activity
 */
public class LongImageActivity extends AppCompatActivity {

    private ImageView mImageView;
    private BigImageView mBigImageView;

    private final String LONG_IMAGE_APP = "long_image_app.jpg";
    private final String LONG_IMAGE_MAP = "long_image_map.jpg";

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

        //打印硬解码情况下该设备纹理(texture)限制值
        Logger.d("texture:" + DeviceOpenGLUtil.getGLESLimitTexture());
        //在View层开启软解，View层开启的软解，只能打开不能关闭，即如果打开软解了，便不可以再打开硬解。
        //mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        //判断当前View是软解还是硬解
        Logger.d("isHardwareAccelerated:" + mImageView.isHardwareAccelerated());

        /**
         * 图片分开加载的一个简单测试
         */
        testImageRegionDecoder();
        /**
         * 加载原图
         */
        loadOriginalBigImage();

        /**
         * 使用分块加载方式加载超大图
         */
        regionDecoderBigImage();
    }

    private void testImageRegionDecoder() {
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

    private void loadOriginalBigImage() {
        try {
            InputStream inputStream = getAssets().open(LONG_IMAGE_APP);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            mImageView.setImageBitmap(bitmap);

            //int inSampleSize = 1;
            //while ((DeviceOpenGLUtil.getGLESLimitTexture() > 0) && (DeviceOpenGLUtil.getGLESLimitTexture() < (height
            //    / inSampleSize))) {
            //    inSampleSize *= 2;
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void regionDecoderBigImage() {
        InputStream inputStream;
        try {
            inputStream = getAssets().open(LONG_IMAGE_APP);
            mBigImageView.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
