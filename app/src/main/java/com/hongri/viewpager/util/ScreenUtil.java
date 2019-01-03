package com.hongri.viewpager.util;

import android.util.DisplayMetrics;

/**
 * @author zhongyao
 * @date 2018/12/24
 */

public class ScreenUtil {
    public static int getScreenWidht() {
        return new DisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return new DisplayMetrics().heightPixels;
    }
}
