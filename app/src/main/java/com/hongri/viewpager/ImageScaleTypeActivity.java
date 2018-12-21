package com.hongri.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageScaleTypeActivity extends AppCompatActivity {

    private ImageView image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale_type);

        image_view = findViewById(R.id.image_view);

        image_view.setImageResource(R.drawable.long_image);

        /**
         * 1、以FIT开头的会使用缩放
         */
        //默认 图片等比缩放到能够填充控件大小
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
        image_view.setScaleType(ScaleType.CENTER_CROP);
        //以完全展示图片内容为目的，图片将被等比缩放到能够完全展示在控件中，并居中展示；如果图片的大小小于控件大小，那么就直接居中展示该图片。
        //image_view.setScaleType(ScaleType.CENTER_INSIDE);

        /**
         * 3、MATRIX 矩阵
         */


    }
}
