package com.hongri.viewpager.util;

import java.text.NumberFormat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 设备展示相关-工具类
 *
 * @author hongri
 */
public class DisplayUtil {
    /**
     * 取百分比
     */
    private static NumberFormat numberFormat;

    /**
     * 获取手机屏幕的尺寸分辨率等信息
     */
    public static void getPhoneInfo(Activity mActivity, Context appContext) {

        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //屏幕宽
        int width = dm.widthPixels;
        //屏幕高
        int height = dm.heightPixels;

        Logger.d("手机屏幕的width（像素）:" + width + "\n");
        Logger.d("手机屏幕的height（像素）:" + height + "\n");

        int width2 = mActivity.getResources().getDisplayMetrics().widthPixels;
        int height2 = mActivity.getResources().getDisplayMetrics().heightPixels;
        Logger.d("手机屏幕的width2（像素）--通过mActivity.getResources获取:" + width2 + "\n");
        Logger.d("手机屏幕的height2（像素）--通过mActivity.getResources获取:" + height2 + "\n");

        int width3 = appContext.getResources().getDisplayMetrics().widthPixels;
        int height3 = appContext.getResources().getDisplayMetrics().heightPixels;
        Logger.d("手机屏幕的width3（像素）-----通过appContext.getResources获取：" + width3 + "\n");
        Logger.d("手机屏幕的height3（像素）-----通过appConext.gerResources获取：" + height3 + "\n");

        int realHeight = getDeviceRealHeight(mActivity);
        Logger.d("手机屏幕的真实高度(屏幕展示高度+虚拟导航栏(if have)):" + realHeight);

        int statusBarHeight = getStatusBarHeight(mActivity);
        Logger.d("手机屏幕的状态栏高度：" + statusBarHeight);

        int titleBarHeight = getTitleBarHeight(mActivity);
        Logger.d("手机屏幕的标题栏高度：" + titleBarHeight);

        int navigationBarHeight = realHeight - height;
        Logger.d("手机屏幕的虚拟导航栏高度：" + navigationBarHeight);

        final float scale = mActivity.getResources().getDisplayMetrics().density;
        int px2dpX = (int)(width / scale + 0.5f);
        Logger.d("手机屏幕的width的dp:" + px2dpX);

        float dp2px = (int)(px2dpX * scale + 0.5f);
        Logger.d("手机屏幕的width的px:" + dp2px);

        float px2dpY = (int)(height / scale + 0.5f);
        Logger.d("手机屏幕的height的dp:" + px2dpY);

        Logger.d("1dp == " + dp2px / px2dpX + " px");

        float dp2pxH = (int)(px2dpY * scale + 0.5f);
        Logger.d("手机屏幕的height的px:" + dp2pxH);

        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);

        Logger.d("手机屏幕x轴dpi:" + dm.xdpi);
        Logger.d("手机屏幕y轴dpi:" + dm.ydpi);

        Logger.d("手机屏幕width(英寸):" + dm.widthPixels / dm.xdpi);
        Logger.d("手机屏幕height(英寸):" + dm.heightPixels / dm.ydpi);

        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        Logger.d("手机屏幕尺寸:" + screenInches);
    }

    /**
     * 状态栏的高度
     */
    public static int getStatusBarHeight(Activity mActivity) {
        Rect frame = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    /**
     * 标题栏高度
     */
    public static int getTitleBarHeight(Activity mActivity) {
        View v = mActivity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        int contentTop = v.getTop();
        int titleBarHeight = contentTop - getStatusBarHeight(mActivity);
        return titleBarHeight;
    }

    /**
     * 获取屏幕完整高度(屏幕展示高度+导航栏)[针对具有导航栏的手机]
     */

    public static int getDeviceRealHeight(Activity mActivity) {
        int realScreenH;
        DisplayMetrics dm = new DisplayMetrics();
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            realScreenH = dm.heightPixels;
        } else {
            mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            realScreenH = dm.heightPixels;
        }
        return realScreenH;
    }

    /**
     * @return 是否是 MI PAD
     */
    public static boolean isMiPad() {
        return !TextUtils.isEmpty(android.os.Build.MODEL) && android.os.Build.MODEL.contains("MI PAD");
    }

    /**
     * 不含7寸pad。如果要所有的pad。
     *
     * @return
     */
    public static boolean isPad(Context context) {
        return Configuration.ORIENTATION_LANDSCAPE == getDeviceDefaultOrientation(context)
            || isMiPad();
    }

    /**
     * 获取设备默认的Orientation
     *
     * @param context
     * @return
     */
    private static int DEFAULT_ORIENTATION;

    public static int getDeviceDefaultOrientation(Context context) {
        if (0 == DEFAULT_ORIENTATION) {
            WindowManager windowManager = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
            Configuration config = context.getResources().getConfiguration();
            int rotation = windowManager.getDefaultDisplay().getRotation();
            if (((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
                && config.orientation == Configuration.ORIENTATION_LANDSCAPE)
                || ((rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)
                && config.orientation == Configuration.ORIENTATION_PORTRAIT)) {
                DEFAULT_ORIENTATION = Configuration.ORIENTATION_LANDSCAPE;
            } else {
                DEFAULT_ORIENTATION = Configuration.ORIENTATION_PORTRAIT;
            }
        }
        return DEFAULT_ORIENTATION;
    }

    /**
     * Return 是否是横屏
     */
    public static Boolean isLandscape(Context c) {
        return c.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Return 是否是竖屏
     */
    public static Boolean isPortrait(Context c) {
        return c.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取应用版本号
     */
    public static int getAPPVersion(Context context) {
        PackageInfo version;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return version.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 定义一个文件存储路径（可修改）
     *
     * @return
     */
    public static String getFilePath() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhong/";
        return filePath;
    }

    public static String getPercent(float current, float total) {

        if (numberFormat == null) {
            // 创建一个数值格式化对象
            numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后0位
            numberFormat.setMaximumFractionDigits(0);
        }
        String result = numberFormat.format(current / total * 100) + "%";
        return result;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5f);
    }
}