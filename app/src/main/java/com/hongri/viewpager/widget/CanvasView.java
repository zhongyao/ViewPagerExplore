package com.hongri.viewpager.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hongri.viewpager.R;

/**
 * Create by zhongyao on 2021/10/28
 * Description:
 * 参考：http://wuxiaolong.me/2016/08/27/Canvas/
 */
public class CanvasView extends View {

    private Bitmap bitmap;
    private Paint paint;

    public CanvasView(Context context) {
        super(context);

        init(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (context == null) {
            return;
        }
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * save/restore示例
         */
//        canvasSave(canvas);

//        canvasDrawRect(canvas);

        canvasDrawBitmap(canvas);

//        canvasDrawCircle(canvas);
    }

    private void canvasDrawCircle(Canvas canvas) {
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        int circlePoint = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() / 2 : bitmap.getHeight() / 2;
        canvas.drawCircle(circlePoint, circlePoint, circlePoint, paint);
    }

    private void canvasDrawBitmap(Canvas canvas) {
//        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        canvas.drawBitmap(bitmap, rect, rect, paint);

        //指定绘制图片的区域
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        //指定图片在屏幕上的绘制（显示）区域
        Rect dest = new Rect(100, 100, bitmap.getWidth() * 2, bitmap.getHeight() * 2);
        canvas.drawBitmap(bitmap, src, dest, paint);
    }

    private void canvasDrawRect(Canvas canvas) {
        Rect rect = new Rect(0, 0, bitmap.getWidth() * 2, bitmap.getHeight() * 2);
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawRect(rect, paint);
    }


    private void canvasSave(Canvas canvas) {
        //第一张
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //保存画布状态
        canvas.save();
        canvas.rotate(45, (float) (getWidth() / 2.0), (float) (getHeight() / 2.0));
        //第二张
        canvas.drawBitmap(bitmap, (float) (getWidth() / 2.0 - bitmap.getWidth() / 2.0), (float) (getHeight() / 2.0 - bitmap.getHeight() / 2.0), paint);
        //去除原来保存的状态
        canvas.restore();
        //第三张
        canvas.drawBitmap(bitmap, 0, getHeight() - bitmap.getHeight(), paint);
    }
}
