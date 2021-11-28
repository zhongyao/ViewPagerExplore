package com.hongri.viewpager.util.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

/**
 * Create by zhongyao on 2021/11/27
 * Description:画布变换
 */
public class HelpTransform {

    private static final String TAG = "HelpTransform";

    public static void stateTest(Canvas canvas, Point coo, Paint paint) {
        canvas.drawLine(coo.x + 400, coo.y + 200, coo.x + 800, coo.y + 400, paint);
        canvas.rotate(45);
        canvas.drawRect(coo.x + 100, coo.y + 100, coo.x + 300, coo.y + 200, paint);
    }

    /**
     * 画布旋转
     *
     * @param canvas
     * @param coo
     * @param paint
     */
    public static void stateTestRotate(Canvas canvas, Point coo, Paint paint) {
        canvas.drawLine(coo.x + 400, coo.y + 200, coo.x + 800, coo.y + 400, paint);
        canvas.drawRect(coo.x + 100, coo.y + 100, coo.x + 300, coo.y + 200, paint);
        //保存Canvas状态
        canvas.save();
        //(角度,中心点x,中心点y)
        canvas.rotate(45, coo.x + 100, coo.y + 100);
        paint.setColor(Color.parseColor("#880FB5FD"));
        canvas.drawRect(coo.x + 100, coo.y + 100, coo.x + 300, coo.y + 200, paint);
        //合并图层
        canvas.restore();
    }

    /**
     * 画布平移
     *
     * @param canvas
     * @param coo
     * @param paint
     */
    public static void stateTestTranslate(Canvas canvas, Point coo, Paint paint) {
        canvas.save();
        canvas.translate(coo.x, coo.y);
        canvas.drawLine(400, 200, 800, 400, paint);
        canvas.drawRect(100, 100, 300, 200, paint);
        //保存canvas状态
        canvas.save();
        canvas.rotate(45, 100, 100);
        paint.setColor(Color.parseColor("#880FB5FD"));
        canvas.drawRect(100, 100, 300, 200, paint);
        canvas.restore();
        canvas.restore();
    }

    /**
     * 画布缩放
     * @param canvas
     * @param coo
     * @param paint
     */
    public static void stateTestScale(Canvas canvas, Point coo, Paint paint) {
        canvas.save();
        canvas.translate(coo.x, coo.y);
        canvas.drawLine(500, 200, 800, 400, paint);
        canvas.drawRect(100, 100, 300, 200, paint);
        canvas.save();
        canvas.scale(2, 2, 100, 100);
        paint.setColor(Color.parseColor("#880FB5FD"));
        canvas.drawRect(100, 100, 300, 200, paint);
        canvas.restore();
        canvas.restore();
    }


    public static void stateTestSkew(Canvas canvas, Point coo, Paint paint) {
        int saveNum1 = canvas.save();
        Log.d(TAG, "saveNum1:" + saveNum1);
        canvas.translate(coo.x, coo.y);
        canvas.drawLine(500, 200, 800, 400, paint);
        paint.setColor(Color.parseColor("#FF0000"));
        canvas.drawRect(100, 100, 300, 200, paint);
        int saveNum2 = canvas.save();
        Log.d(TAG, "saveNum2:" + saveNum2);

        //获取图层的个数
        int saveCount = canvas.getSaveCount();
        Log.d(TAG, "saveCount:" + saveCount);
        canvas.skew(1.0f, 0);
        paint.setColor(Color.parseColor("#880FB5FD"));
        canvas.drawRect(100, 100, 300, 200, paint);
        canvas.restore();//图层向下合并
        canvas.restore();

        //直接恢复到第几个图层
//        canvas.restoreToCount(2);

    }
}
