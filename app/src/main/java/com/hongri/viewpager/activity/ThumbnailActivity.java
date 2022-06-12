package com.hongri.viewpager.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hongri.viewpager.R;
import com.hongri.viewpager.util.BitmapUtil;

import java.io.File;

import static android.media.ThumbnailUtils.OPTIONS_RECYCLE_INPUT;
import static android.provider.MediaStore.Video.Thumbnails.FULL_SCREEN_KIND;
import static android.provider.MediaStore.Video.Thumbnails.MICRO_KIND;
import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;


/**
 * @author zhongyao
 * @date 2021/10/29
 * 获取各种大小的缩略图【图片 + 视频】
 */

public class ThumbnailActivity extends AppCompatActivity {

    private ImageView iv;
    private final String VIDEO_URL = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM" + File.separator + "Camera" + File.separator + "66.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail);

        iv = findViewById(R.id.iv);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);

        //【1】原图
//        iv.setImageResource(R.drawable.image);

        //【2】把原始bitmap截取成一个 300 * 300 的正方形缩略图。注意：默认是以中心为原点截取成缩略图。
//        Bitmap thumbBmp = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
//        iv.setImageBitmap(thumbBmp);

        //【3】对原始图片Bitmap等比例缩小5倍的缩略图
//        Bitmap thumbBmp2 = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth() / 5, bitmap.getHeight() / 5);
//        iv.setImageBitmap(thumbBmp2);

        //【4】如果options定义为OPTIONS_RECYCLE_INPUT,则回收
        // * @param source这个资源文件[即原来的bitmap]
        // * (除非缩略图等于@param source)
//        Bitmap thumbBmp3 = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//        iv.setImageBitmap(thumbBmp3);

        //【5】获取视频缩略图
        // MICRO_KIND 超小
        // MINI_KIND 小
        // FULL_SCREEN_KIND 全屏大小
//        Bitmap videoThumbnails = ThumbnailUtils.createVideoThumbnail(VIDEO_URL, FULL_SCREEN_KIND);
//        iv.setImageBitmap(videoThumbnails);

        //【6】获取视频缩略图 -- 方法2 (全屏大小)
        Bitmap videoThumbnails2 = BitmapUtil.getVideoThumbnail(VIDEO_URL);
        iv.setImageBitmap(videoThumbnails2);

    }
}