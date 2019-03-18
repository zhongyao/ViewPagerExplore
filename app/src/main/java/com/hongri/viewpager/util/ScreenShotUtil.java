package com.hongri.viewpager.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;

/**
 * @author zhongyao
 * @date 2019/3/12
 * 截屏工具类
 *
 * 参考：
 * http://www.cnblogs.com/tgyf/p/4851092.html
 */

public class ScreenShotUtil {

    /**
     * 截取除了导航栏以外的屏幕
     *
     * @param activity
     */
    public static Bitmap capture1(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        //Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());


        //if (bitmap != null) {
        //    try {
        //        // 获取内置SD卡路径
        //        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        //        // 图片文件路径
        //        String filePath = sdCardPath + File.separator + "screenshot.png";
        //        File file = new File(filePath);
        //        FileOutputStream os = new FileOutputStream(file);
        //        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        //        os.flush();
        //        os.close();
        //    } catch (Exception e) {
        //    }
        //    return bitmap;
        //}
        return bitmap;
    }

    /**
     * 根据指定的Activity截图（去除状态栏）
     *
     * @param activity 要截图的Activity
     * @return Bitmap
     */
    public static Bitmap shotActivityNoStatusBar(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
            statusBarHeights, widths, heights - statusBarHeights);

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }
}