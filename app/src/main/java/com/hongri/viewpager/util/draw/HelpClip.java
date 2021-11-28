package com.hongri.viewpager.util.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;

/**
 * Create by zhongyao on 2021/11/27
 * Description:画布的裁剪
 */
public class HelpClip {

    /**
     * 1、内裁剪(会将区域内的之后绘制的内容保存)
     */
    public static void clipInner(Canvas canvas, Paint paint) {
        Rect rect = new Rect(100, 100, 200, 300);
        canvas.clipRect(rect);
        canvas.drawRect(0, 0, 200, 300, paint);
    }

    /**
     * 2、外部裁剪(会将区域外的之后绘制的内容保存)
     */
    public static void clipOuter(Canvas canvas, Paint paint) {
        Rect rect = new Rect(100, 100, 200, 300);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutRect(rect);
            canvas.drawRect(0, 0, 200, 300, paint);
        }
    }
}
