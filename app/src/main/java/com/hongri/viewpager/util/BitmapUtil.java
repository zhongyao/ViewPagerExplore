package com.hongri.viewpager.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;

/**
 * @author：zhongyao on 2016/7/11 10:39
 * @description:图片处理
 */
public class BitmapUtil {

    /**
     * 图片压缩的几种方式：
     * 首先应该清楚一个概念就是Bitmap占用的内存大小 = 图片的宽度*图片的高度*每个像素点包含的字节数
     * android 包含的图片压缩格式：
     * ALPHA_8:表示8位Alpha位图，没有颜色,只有透明度，占用一个字节。
     * ARGB_4444:表示16位ARGB位图，占用两个字节。
     * ARGB_8888:表示32位ARGB位图，占用四个字节。
     * RBG_565:表示16位RGB位图，没有透明度，占用连个字节。
     *
     * 1、质量压缩
     * 2、采样率压缩
     * 3、缩放法压缩
     * 4、RGB_565压缩
     * 5、createScaledBitmap
     */

    /**
     * 1、质量压缩:
     * 可以看到图片的大小、宽、高是不变的，所以所占用的内存大小也不会变
     * 但是bytes.length是随着quality变小而变小的。这样适合去传递二进制的图片数据
     */
    public static Bitmap pressBimmapInQuality(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 10;
        forBitmapPressedLog(0, bitmap, null, quality);
        bitmap.compress(CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();

        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        forBitmapPressedLog(1, bitmap, bytes, quality);
        return bitmap;
    }

    /**
     * 2、采样率压缩
     *
     * @return
     */
    public static Bitmap pressBitmapInSampleSize(Resources res, int resId, int reqWidth, int reqHeight) {
        Options options = new Options();
        Bitmap testBitmap = BitmapFactory.decodeResource(res, resId, options);
        forBitmapPressedLog(0, testBitmap, null, -1);
        options.inJustDecodeBounds = true;
        //forBitmapPressedLog(0,null,null,0);
        Bitmap preBitmap = BitmapFactory.decodeResource(res, resId, options);
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            //计算出实际宽高和目标宽高的比率
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize > reqHeight) && (halfWidth / inSampleSize > reqWidth)) {
                //按照宽度的比例去取样
                inSampleSize *= 2;
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        Bitmap afterBitmap = BitmapFactory.decodeResource(res, resId, options);
        forBitmapPressedLog(1, afterBitmap, null, -1);
        return afterBitmap;
    }

    /**
     * 3、缩放法压缩
     */
    public static Bitmap pressBitmapInScale(Bitmap bitmap, float widthScale, float heightScale) {
        forBitmapPressedLog(0, bitmap, null, -1);
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        forBitmapPressedLog(1, bitmap, null, -1);
        return bitmap;
    }

    /**
     * 4、RGB_565法
     */
    public static Bitmap pressBitmapInRGB(Bitmap bitmap, Resources res, int resId) {
        forBitmapPressedLog(0, bitmap, null, -1);
        Options options = new Options();
        options.inPreferredConfig = Config.RGB_565;
        bitmap = BitmapFactory.decodeResource(res, resId, options);
        forBitmapPressedLog(1, bitmap, null, -1);
        return bitmap;
    }

    /**
     * 5、createScaledBitmap法
     */

    public static Bitmap pressBitmapInScaledBitmap(Bitmap bitmap) {
        forBitmapPressedLog(0, bitmap, null, -1);
        bitmap = Bitmap.createScaledBitmap(bitmap, 192 * 1, 108 * 1, true);
        forBitmapPressedLog(1, bitmap, null, -1);
        return bitmap;
    }

    private static void forBitmapPressedLog(int i, Bitmap bitmap, byte[] bytes, int quality) {
        String status;
        if (i == 0) {
            status = "前";
        } else {
            status = "后";
        }
        Logger.d(
            "图片压缩" + status + "的大小：" + (bitmap != null ? ((float)bitmap.getByteCount()) / 1024 / 1024 : "---")
                + "M\n图片的高度："
                + bitmap.getHeight()
                + "\n图片的宽度：" + bitmap
                .getWidth() + "\nbytes.length:" + (bytes != null ? bytes.length : "---") + "\nquality:" + (quality != -1
                ? quality : "---"));
    }

    /**
     * 压缩图片大小
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // 调用上面定义的方法计算inSampleSize值
        //options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize = 1;

        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeResource(res, resId, options);
        Logger.d("options.outWidth:" + options.outWidth + "\noptions.outHeight:" + options.outHeight + "\nreqWidth:"
            + reqWidth + "\nreqHeight:" + reqHeight);
        return Bitmap.createScaledBitmap(bmp, reqWidth, reqHeight, false);
        //return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            //计算出实际宽高和目标宽高的比率
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Logger.d("inSampleSize:" + inSampleSize);
        return inSampleSize;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId   simplesize=0,不按大小比例压缩
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 获得网络图片Bitmap
     *
     * @param imageUrlStr
     * @return
     */
    public static Bitmap loadBitmapFromNet(String imageUrlStr) {
        Bitmap bitmap = null;
        URL imageUrl = null;

        if (imageUrlStr == null || imageUrlStr.length() == 0) {
            return null;
        }

        try {
            imageUrl = new URL(imageUrlStr);
            URLConnection conn = imageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            int length = conn.getContentLength();
            if (length != -1) {
                byte[] imgData = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) != -1) {
                    System.arraycopy(temp, 0, imgData, destPos, readLen);
                    destPos += readLen;
                }
                bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            }
        } catch (IOException e) {
            return null;
        }

        return bitmap;
    }

    public static int getMaxHeapMemory(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        return am != null ? am.getMemoryClass() : 0;
    }

}
