package com.hongri.viewpager.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.hongri.viewpager.Interface.STPhotoSaveCallBack;

/**
 * @author zhongyao
 * @date 2018/12/21
 */

public class ImageUtil {

    /**
     * @param bmp          获取的bitmap数据
     * @param picName      保持图片名称
     *
     *                     图片文件命名有如下几种方式：
     *                     1、随机命名：通过UUID生成字符串文件名 -- UUID.randomUUID().toString();
     *                     2、时间命名：
     *                     3、MD5处理文件URL来命名(唯一、安全)
     *                     目前采用第1种命名方式
     * @param saveCallBack
     */
    public static void saveBmp2Gallery(Context mContext, Bitmap bmp, String picName,
                                       STPhotoSaveCallBack saveCallBack) {
        String picNameUUID = UUID.randomUUID().toString();
        String fileName;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
            + File.separator + Environment.DIRECTORY_DCIM
            + File.separator + "Camera" + File.separator;

        // 声明文件对象
        File file;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picNameUUID + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
            //保存失败回调
            saveCallBack.savePhotoFailure();
            return;
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                //保持失败回调
                saveCallBack.savePhotoFailure();
                return;
            }
        }
        /**
         通知相册更新
         **/
        MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);

        //保存成功回调
        saveCallBack.savePhotoSuccess();
    }

    public static void saveGif2Gallery(Context mContext, Bitmap bmp, byte[] bytes, String picName,
                                       STPhotoSaveCallBack saveCallBack) {
        String picNameUUID = UUID.randomUUID().toString();
        String fileName;
        String filePath;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
            + File.separator + Environment.DIRECTORY_DCIM
            + File.separator + "Camera" + File.separator;

        // 声明文件对象
        File file;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picNameUUID + ".jpg");
            filePath = galleryPath + picNameUUID + ".jpg";
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                /**
                 * 存储gif图
                 */
                outStream.write(bytes);
                //bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
            //保存失败回调
            saveCallBack.savePhotoFailure();
            return;
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                //保持失败回调
                saveCallBack.savePhotoFailure();
                return;
            }
        }
        /**
         通知相册更新
         **/
        //MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bmp, fileName, null);
        try {
            MediaStore.Images.Media.insertImage(mContext.getContentResolver(), filePath, fileName, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);

        //保存成功回调
        saveCallBack.savePhotoSuccess();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
            : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
