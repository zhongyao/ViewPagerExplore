package com.hongri.viewpager.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.hongri.viewpager.R;
import com.hongri.viewpager.util.BitmapUtil;
import com.hongri.viewpager.util.Logger;

/**
 * ImageView属性Activity
 */
public class ImageActivity extends Activity {

    private ImageView image_view;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_scale_type);

        image_view = findViewById(R.id.image_view);

        image_view.setImageResource(R.drawable.avatar);

        /**
         * 使用BitmapRegionDecoder展示长图
         */
//        displayBigImage();

        /**
         * ImageView相关ScaleType测试
         */
        imageScaleType();

        /**
         * Bitmap & Drawable
         */
//        bitmapAndDrawable();
    }

    private void displayBigImage() {
        try {
            int maxHeapMemory = BitmapUtil.getMaxHeapMemory(this);
            Logger.d("maxHeapMemory:" + maxHeapMemory);
            iv = findViewById(R.id.iv);
            //iv.setScaleType(ScaleType.CENTER_CROP);

            //Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.comic, 1920/2,
            //    1080/2);

            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.comic);
            //bitmap = BitmapUtil.pressBimmapInQuality(bitmap);

            Bitmap bitmap = BitmapUtil.pressBitmapInSampleSize(getResources(), R.drawable.comic, 200, 200);
            iv.setImageBitmap(bitmap);

            //方法3
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.comic);
            //bitmap = BitmapUtil.pressBitmapInScale(bitmap, 0.5f, 0.5f);
            //iv.setImageBitmap(bitmap);

            //方法4
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.comic);
            //bitmap = BitmapUtil.pressBitmapInRGB(bitmap, getResources(), R.drawable.comic);

            //方法5
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.comic);
            //bitmap = BitmapUtil.pressBitmapInScaledBitmap(bitmap);

            iv.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void bitmapAndDrawable() {
        /**
         * 将Drawable转为Bitmap
         */
        Bitmap bitmap = drawableToBitmap(image_view.getDrawable());

        /**
         * 将Bitmap转为Drawable
         */
        Drawable drawable = bitmapToDrawable();

    }

    private Drawable bitmapToDrawable() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        return bitmapDrawable;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        //获取Drawable的w、h
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        //获取Drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565;
        //建立对应的bitmap
        Bitmap bmp = Bitmap.createBitmap(w, h, config);

        //建立对应的Bitmap的画布
        Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, w, h);

        drawable.draw(canvas);
        return bmp;
    }

    /**
     * 参考：https://www.jianshu.com/p/32e335d5b842
     */
    private void imageScaleType() {
        image_view.setVisibility(View.VISIBLE);
        /**
         * 1、以FIT开头的会使用缩放
         */
        //【默认】图片等比缩放到能够填充控件大小
        //image_view.setScaleType(ScaleType.FIT_CENTER);
        //图片等比缩放到能够填充控件大小，并放置在控件的上面或者左边展示
        //如果图片的宽大于高，则会在下半部分留白；如果图片的高度大于宽度，则会在右半部分留白。
        //image_view.setScaleType(ScaleType.FIT_START);
        //图片等比缩放到能够填充控件大小，并放置在控件的下面或者右边展示
        //如果图片的宽大于高，则会在上半部分留白；如果图片的高度大于宽度，则会在左半部分留白
        //image_view.setScaleType(ScaleType.FIT_END);
        //图片缩放到控件大小，完全填充控件展示（非等比缩放）
        //image_view.setScaleType(ScaleType.FIT_XY);
        /**
         * 2、以CENTER开头的共同点是居中显示，图片的中心点会与控件的中心点重叠
         */
        //会展示图片的中心部分。如果图片的大小小于控件的宽高，那么图片会被居中显示
        //image_view.setScaleType(ScaleType.CENTER) ;
        //图片会被等比缩放到完全填充整个控件，并居中展示（常用）。
        //image_view.setScaleType(ScaleType.CENTER_CROP);
        //以完全展示图片内容为目的，图片将被等比缩放到能够完全展示在控件中，并居中展示；如果图片的大小小于控件大小，那么就直接居中展示该图片。
        //image_view.setScaleType(ScaleType.CENTER_INSIDE);

        /**
         * 3、MATRIX 矩阵
         */

    }
}
