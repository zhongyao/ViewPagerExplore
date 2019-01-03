package com.hongri.viewpager.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author：zhongyao on 2016/7/11 10:39
 * @description:图片处理
 */
public class BitmapUtil {

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
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
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
