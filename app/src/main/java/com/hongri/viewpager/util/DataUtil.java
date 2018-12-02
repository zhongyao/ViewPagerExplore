package com.hongri.viewpager.util;

import com.hongri.viewpager.R;

/**
 * @author zhongyao
 * @date 2018/12/2
 */

public class DataUtil {
    private static int[] images = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3};

    private static String[] descriptions = {"描述一", "描述二", "描述三"};

    public static int[] getImageResource() {
        return images;
    }

    public static String[] getDescriptions() {
        return descriptions;
    }
}
