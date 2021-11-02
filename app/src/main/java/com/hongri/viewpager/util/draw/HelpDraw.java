package com.hongri.viewpager.util.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;

/**
 * Create by zhongyao on 2021/11/1
 * Description:
 */
public class HelpDraw {

    private static final float TEXT_SIZE = 30;

    /**
     * 绘制网格
     *
     * @param canvas
     * @param winSize
     * @param gridPaint
     */
    public static void drawGrid(Canvas canvas, Point winSize, Paint gridPaint) {
        //初始化网格画笔
        gridPaint.setStrokeWidth(2);
        gridPaint.setColor(Color.GRAY);
        gridPaint.setStyle(Paint.Style.STROKE);
        //设置虚线效果new float[]{可见长度, 不可见长度},偏移值
        gridPaint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        canvas.drawPath(HelpPath.gridPath(50, winSize), gridPaint);
    }

    /**
     * 绘制坐标系
     *
     * @param canvas
     * @param coo
     * @param winSize
     * @param paint
     */
    public static void drawCoo(Canvas canvas, Point coo, Point winSize, Paint paint) {
        //初始化坐标系画笔
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        //设置虚线效果
        paint.setPathEffect(null);
        canvas.drawPath(HelpPath.cooPath(coo, winSize), paint);
        //右箭头
        canvas.drawLine(winSize.x, coo.y, winSize.x - 40, coo.y - 20, paint);
        canvas.drawLine(winSize.x, coo.y, winSize.x - 40, coo.y + 20, paint);
        //下箭头
        canvas.drawLine(coo.x, winSize.y, coo.x + 20, winSize.y - 40, paint);
        canvas.drawLine(coo.x, winSize.y, coo.x - 20, winSize.y - 40, paint);
        //为坐标系绘制文字
        drawText4Coo(canvas, coo, winSize, paint);
    }

    /**
     * 为坐标系绘制文字
     *
     * @param canvas
     * @param coo
     * @param winSize
     * @param paint
     */
    private static void drawText4Coo(Canvas canvas, Point coo, Point winSize, Paint paint) {
        paint.setStrokeWidth(2);
        paint.setTextSize(TEXT_SIZE);
        //x正轴文字
        for (int i = 1; i < (winSize.x - coo.x) / 50; i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(String.valueOf(100 * i), coo.x - (i != 0 ? TEXT_SIZE : 0) + 100 * i, coo.y + 40, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x + 100 * i, coo.y, coo.x + 100 * i, coo.y - 10, paint);
        }

        //x负轴文字
        for (int i = 1; i < coo.x / 50; i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(String.valueOf(-100 * i), coo.x - TEXT_SIZE - 100 * i, coo.y + 40, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x - 100 * i, coo.y, coo.x - 100 * i, coo.y - 10, paint);
        }

        //y正轴文字
        for (int i = 1; i < (winSize.y - coo.y) / 50; i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(String.valueOf(100 * i), coo.x + 20, coo.y + 100 * i + TEXT_SIZE / 3, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x, coo.y + 100 * i, coo.x + 10, coo.y + 100 * i, paint);
        }

        //y负轴文字
        for (int i = 1; i < coo.y / 50; i++) {
            paint.setStrokeWidth(2);
            canvas.drawText(String.valueOf(-100 * i), coo.x + 20, coo.y + TEXT_SIZE / 3 - 100 * i, paint);
            paint.setStrokeWidth(5);
            canvas.drawLine(coo.x, coo.y - 100 * i, coo.x + 10, coo.y - 100 * i, paint);
        }
    }

}
