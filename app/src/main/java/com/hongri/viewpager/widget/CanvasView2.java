package com.hongri.viewpager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.util.AttributeSet;
import android.view.View;

import com.hongri.viewpager.util.DeviceUtil;
import com.hongri.viewpager.util.draw.HelpDraw;

/**
 * Create by zhongyao on 2021/11/1
 * Description:
 * 参考：https://juejin.cn/post/6844903705930629128
 */
public class CanvasView2 extends View {

    private Paint mGridPaint;//网格画笔
    private Point mWinSize;//屏幕尺寸
    private Point mCoo;//坐标系原点
    private Paint mPointPaint;//点画笔
    private Paint mLinePaint;//线画笔
    private final String COLOR_RED = "#FF0000";
    private Context mContext;

    public CanvasView2(Context context) {
        super(context);
        init(context);
    }

    public CanvasView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CanvasView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        //准备屏幕尺寸
        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWinSize = new Point();
        mCoo = new Point(500, 500);
        DeviceUtil.loadWinSize(getContext(), mWinSize);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(Color.parseColor(COLOR_RED));
        mPointPaint.setStrokeWidth(20);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor(COLOR_RED));
        mLinePaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        HelpDraw.drawColor(canvas);
        HelpDraw.drawGrid(canvas, mWinSize, mGridPaint);
        HelpDraw.drawCoo(canvas, mCoo, mWinSize, mGridPaint);
//        HelpDraw.drawPoint(canvas, mPointPaint);
//        HelpDraw.drawLine(canvas, mLinePaint);
//        HelpDraw.drawRect(canvas, mLinePaint);
//        HelpDraw.drawLikeCircle(canvas, mLinePaint);
        HelpDraw.drawBitmap(mContext, canvas, mLinePaint);
    }
}