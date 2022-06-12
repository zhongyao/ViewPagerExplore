package com.hongri.viewpager.util;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.IntegerRes;

import com.hongri.viewpager.MyApplication;

public class ResHelper {
    public static Drawable sDefaultItemDrawable = null;

    public static Drawable getDefaultItemDraeable() {
        if (null == sDefaultItemDrawable) {
            sDefaultItemDrawable = new ColorDrawable(Color.BLUE);
        }
        return sDefaultItemDrawable;
    }

    public static Drawable getDrawable(int resID) {
        Resources resources = MyApplication.appContext.getResources();
        return resources.getDrawable(resID);
    }

    public static int getColor(int resID) {
        Resources resources = MyApplication.appContext.getResources();
        return resources.getColor(resID);
    }

    /**
     * 获取带透明度的颜色
     *
     * @param alphaId 透明度资源Id(文件：res／values／alpha)
     * @param colorId 颜色资源Id
     * @return 返回aarrggbb值
     */

    public static int getColor(@IntegerRes int alphaId, @ColorRes int colorId) {
        //实体色
        int color = getColor(colorId) & 0x00ffffff;
        //透明度
        int alpha = getInteger(255, alphaId);
        return (alpha << 24) | color;
    }

    public static int getInteger(int defaultValue, @IntegerRes int integerId) {
        return MyApplication.appContext.getResources().getInteger(integerId);
    }
}
