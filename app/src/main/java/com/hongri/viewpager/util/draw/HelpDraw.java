package com.hongri.viewpager.util.draw;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;

import com.hongri.viewpager.R;

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

    /**
     * 绘制颜色
     *
     * @param canvas
     */
    public static void drawColor(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#E0F7F5"));
//        canvas.drawARGB(255, 224, 247, 245);
//        三者等价
//        canvas.drawRGB(224, 247, 245);
    }

    /**
     * 绘制点
     */
    public static void drawPoint(Canvas canvas, Paint paint) {
        //绘制一个点
        canvas.drawPoint(100, 100, paint);
        //绘制一组点
        canvas.drawPoints(new float[]{200, 200, 300, 300, 400, 400}, paint);
    }

    /**
     * 绘制直线
     */
    public static void drawLine(Canvas canvas, Paint paint) {
        //绘制一条线
        canvas.drawLine(250, 250, 600, 600, paint);
        //绘制一组线
        canvas.drawLines(new float[]{200, 200, 200, 400, 200, 400, 500, 700, 500, 700, 800, 900}, paint);
    }

    /**
     * 绘制矩形
     *
     * @param canvas
     * @param paint
     */
    public static void drawRect(Canvas canvas, Paint paint) {
        canvas.drawRect(200, 200, 400, 400, paint);
        //等价
//        Rect rect = new Rect(500, 500, 700, 700);
//        canvas.drawRect(rect, paint);

        //矩阵圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(500, 500, 700, 700, 50, 50, paint);
        }
        //等价
//        RectF rect = new RectF(500, 500, 700, 700);
//        canvas.drawRoundRect(rect, 50, 50, paint);

    }

    /**
     * 绘制类圆
     */
    public static void drawLikeCircle(Canvas canvas, Paint paint) {
        //绘制圆
        canvas.drawCircle(200, 200, 50, paint);
        //绘制椭圆
//        RectF rectF = new RectF(500, 500, 800, 700);
//        canvas.drawOval(rectF, paint);

        //绘制圆弧
        RectF rectArc = new RectF(500, 500, 800, 700);
        canvas.drawArc(rectArc, 0, 90, true, paint);
    }

    public static void drawBitmap(Context context, Canvas canvas, Paint paint) {
        //定点绘制图片
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird);
//        canvas.drawBitmap(bitmap, 500, 500, paint);

        //变换矩阵绘制图片
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{
                1, 0.5f, 1500 * 3,
                0, 1, 100 * 3,
                0, 0, 3});
        canvas.drawBitmap(bitmap, matrix, paint);


    }

}
