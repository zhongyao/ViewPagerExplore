package com.hongri.viewpager.util.draw;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

/**
 * Create by zhongyao on 2021/11/1
 * Description:
 */
public class HelpPath {

    /**
     * 绘制网格:注意只有用path才能绘制虚线
     *
     * @param step    小正方形边长
     * @param winSize 屏幕尺寸
     */
    public static Path gridPath(int step, Point winSize) {
        Path path = new Path();

        for (int i = 0; i < winSize.y / step + 1; i++) {
            path.moveTo(0, step * i);
            path.lineTo(winSize.x, step * i);
        }

        for (int i = 0; i < winSize.x / step + 1; i++) {
            path.moveTo(step * i, 0);
            path.lineTo(step * i, winSize.y);
        }
        return path;
    }

    /**
     * 坐标系路径
     *
     * @param coo     坐标点
     * @param winSize 屏幕尺寸
     * @return 坐标系路径
     */
    public static Path cooPath(Point coo, Point winSize) {
        Path path = new Path();

        //x轴正半轴
        path.moveTo(coo.x, coo.y);
        path.lineTo(winSize.x, coo.y);

        //y轴正半轴
        path.moveTo(coo.x, coo.y);
//        path.lineTo(coo.x, 0);
        path.lineTo(coo.x, coo.y - winSize.y);

        //x轴负半轴
        path.moveTo(coo.x, coo.y);
//        path.lineTo(0, coo.y);
        path.lineTo(coo.x - winSize.x, coo.y);

        //y轴负半轴
        path.moveTo(coo.x, coo.y);
        path.lineTo(coo.x, winSize.y);

        return path;
    }

}
