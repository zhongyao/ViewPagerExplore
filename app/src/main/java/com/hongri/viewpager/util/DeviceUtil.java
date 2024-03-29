package com.hongri.viewpager.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author zhongyao
 * @date 2018/12/24
 */

public class DeviceUtil {
    public static int getScreenWidht() {
        return new DisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return new DisplayMetrics().heightPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param ctx     上下文
     * @param winSize 屏幕尺寸
     */
    public static void loadWinSize(Context ctx, Point winSize) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        winSize.x = outMetrics.widthPixels;
        winSize.y = outMetrics.heightPixels;
    }


    public static String getPhoneInfo() {
        String phoneInfo = "Product: " + android.os.Build.PRODUCT + "\n";
        phoneInfo += "CPU_ABI: " + android.os.Build.CPU_ABI + "\n";
        phoneInfo += "TAGS: " + android.os.Build.TAGS + "\n";
        phoneInfo += "VERSION_CODES.BASE: "
                + android.os.Build.VERSION_CODES.BASE + "\n";
        phoneInfo += "MODEL: " + android.os.Build.MODEL + "\n";
        phoneInfo += "SDK: " + android.os.Build.VERSION.SDK + "\n";
        phoneInfo += "VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE
                + "\n";
        phoneInfo += "DEVICE: " + android.os.Build.DEVICE + "\n";
        phoneInfo += "DISPLAY: " + android.os.Build.DISPLAY + "\n";
        phoneInfo += "BRAND: " + android.os.Build.BRAND + "\n";
        phoneInfo += "BOARD: " + android.os.Build.BOARD + "\n";
        phoneInfo += "FINGERPRINT: " + android.os.Build.FINGERPRINT + "\n";
        phoneInfo += "ID: " + android.os.Build.ID + "\n";
        phoneInfo += "MANUFACTURER: " + android.os.Build.MANUFACTURER + "\n";

        Logger.d("phoneInfo:" + phoneInfo);
        return phoneInfo;
    }
}
