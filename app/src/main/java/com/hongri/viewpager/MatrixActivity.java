package com.hongri.viewpager;

import java.util.Arrays;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import com.hongri.viewpager.util.Logger;
import com.hongri.viewpager.util.ResHelper;

/**
 * rectangle [数]长方形 矩形
 * concat 连接多个数组或字符串
 *
 * @author hongri
 *
 *         参考：
 *         https://blog.csdn.net/cquwentao/article/details/51445269
 *         https://blog.csdn.net/gaojinshan/article/details/17334181
 *
 *         pre 前乘（右乘）：M' = M * T
 *         post 后乘（左乘）：M' = T * M
 *
 *         set系列方法：setTranslate setScale setRotate setSkew 设置 会覆盖之前的参数
 *         pre系列方法：preTranslate preScale preRotate preSkew 矩阵前乘 M' = M * T(dx, dy)
 *         post系列方法：postTranslate postScale postRotate postSkew 矩阵后乘 M' = T(dx, dy)*M
 */
@RequiresApi(api = VERSION_CODES.LOLLIPOP)
public class MatrixActivity extends AppCompatActivity {

    private ImageView iv;
    private Drawable drawable;
    public static final int ACTION_ROTATE = 101;
    public static final int ACTION_TRANSLATE = 102;
    public static final int ACTION_SCALE = 103;
    public static final int ACTION_SKEW = 104;
    public static final int ACTION_CUSTOM = 105;
    public static final int NONE = 105;
    public static final String TAG = MatrixActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_matrix);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        iv = findViewById(R.id.iv);
        Drawable drawable = getDyeDrawable(R.drawable.st_web_container_back,
            ResHelper.getColor(R.color.common_default_red_color));
        iv.setImageDrawable(drawable);
        //iv.setScaleType(ScaleType.MATRIX);

        //drawable = iv.getDrawable();

        //init(drawable);

        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eye);
        //initMatrix(bitmap);
        //iv.setOnTouchListener(new TouchListener());

        //updateMatrix(ACTION_SCALE);

        /**
         * 矩阵其他重要方法测试
         */
        //testMethod();
    }

    public static Drawable getDyeDrawable(int resId, int colorValue) {
        Drawable drawable = ResHelper.getDrawable(resId);
        if (drawable != null) {
            drawable.setColorFilter(new LightingColorFilter(0xFF000000, colorValue));
        }
        return drawable;
    }

    Matrix mMatrix = new Matrix();

    private void testMethod() {
        //初始数据为三个点 (0, 0) (80, 100) (400, 300)
        //float[] pts = new float[] {0, 0, 80, 100, 400, 300};
        //mMatrix.setScale(0.5f, 1f);
        //Logger.d("before:" + Arrays.toString(pts));
        //mMatrix.mapPoints(pts);
        //Logger.d("after:" + Arrays.toString(pts));

        /**
         * 原始数据需要保留，用此方法
         */
        //float[] src = new float[] {0, 0, 80, 100, 400, 300};
        //float[] dst = new float[] {0, 0, 0, 0, 0, 0};
        //mMatrix.setScale(0.5f, 1f);
        //Logger.d("before:\n" + "src:" + Arrays.toString(src) + "  dst:" + Arrays.toString(dst));
        //mMatrix.mapPoints(dst, src);
        //Logger.d("after:\n" + "src:" + Arrays.toString(src) + "  dst:" + Arrays.toString(dst));

        /**
         * 将第二个第三个计算后存储在dst最初始位置
         */
        //float[] src = new float[] {0, 0, 80, 100, 400, 300};
        //float[] dst = new float[6];
        //mMatrix.setScale(0.5f,1f);
        //Logger.d("before:\n" + "src:" + Arrays.toString(src) + "  dst:" + Arrays.toString(dst));
        //mMatrix.mapPoints(dst,0,src,2,2);
        //Logger.d("before:\n" + "src:" + Arrays.toString(src) + "  dst:" + Arrays.toString(dst));

        /**
         * 测量半径，由于圆可能会因为画布变换变成椭圆，所以此处测量的是平均半径。
         */
        //float radius = 100;
        //float result;
        //mMatrix.setScale(0.5f, 1f);
        //Logger.d("before:mapRadius " + radius);
        //result = mMatrix.mapRadius(radius);
        //Logger.d("after:mapRadius " + result);

        /**
         * 测量Rect并将结果放入Rect中，返回值是矩阵经过变换后是否依然为矩阵
         */
        //RectF rectF = new RectF(400, 400, 1000, 800);
        //mMatrix.setScale(0.5f, 1f);
        //mMatrix.postSkew(1, 0);
        //Logger.d("before:mapRect:" + rectF.toString());
        //boolean result = mMatrix.mapRect(rectF);
        //Logger.d("after:mapRect:" + rectF.toString());
        //Logger.d("result:" + result);

        float[] src = new float[] {1000, 800};
        float[] dst = new float[2];

        mMatrix.setScale(0.5f, 1f);
        mMatrix.postTranslate(100, 100);

        // 计算向量, 不受位移影响
        mMatrix.mapVectors(dst, src);
        Logger.d("mapVectors: " + Arrays.toString(dst));

        // 计算点
        mMatrix.mapPoints(dst, src);
        Logger.d("mapPoints: " + Arrays.toString(dst));

    }

    private void init(Drawable drawable) {
        RectF rectF = new RectF();
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        rectF.set(0, 0, drawableWidth, drawableHeight);
        Logger.d("rectF:" + rectF.toShortString());
    }

    //矩阵知识点
    private void initMatrix(Bitmap bitmap) {
        /**
         * 1、构造函数
         * <1>直接创建一个单位矩阵
         * <2>根据提供的矩阵创建一个新的矩阵
         */
        Matrix matrix = new Matrix();
        Matrix matrixXX = new Matrix(matrix);

        /**
         * 2、判断是否是单位矩阵
         * 判断是否是【仿射矩阵】:只要最后一行是0 0 1便是仿射矩阵
         */
        matrix.isIdentity();
        matrix.isAffine();

        /**
         * 3、rectStaysRect():
         * 判断是否可以将一个矩阵依旧变化成另一个矩阵
         * 当矩阵是单位矩阵或者只进行平移缩放的以及旋转90度的倍数的时候返回true
         */
        matrixXX.rectStaysRect();
        /**
         * 4、reset重置矩阵为单位矩阵
         */

        /**
         * 5、设置平移效果，参数分别是x,y上的平移量
         * setTranslate(x,y)
         */

        matrix.setTranslate(100, 100);
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawBitmap(bitmap, matrix, paint);

        /**
         * 6、setScale(sx,sy)
         * setScale(sx,sy,px,py)
         *
         * sx sy 表示缩放的倍数，px py表示缩放的中心点坐标
         */
        matrix.setScale(2, 2);
        matrix.setScale(2, 2, 100, 100);

        /**
         * 7、setRotate(degree)
         * setRotate(degree,px,py)
         */
        matrix.setRotate(90);
        matrix.setRotate(900, 100, 100);

        /**
         * 8、setSinCos(float sinValue,float cosValue,float px,float py)
         * setSinCos(float sinValue,float cosValue)
         */
        //旋转90度
        matrix.setSinCos(1, 0, 0, 0);
        matrix.setSinCos(1, 0);

        /**
         * 9、setSkew 错切
         * setSkew(float kx,float ky,float px,float py)
         * setSkew(float kx,float ky)
         * 这里的kx ky代表了x y上从错切因子，px py表示错切中心
         */
        matrix.setSkew(1, 0, 0, 0);
        matrix.setSkew(1, 0);

        /**
         * 10、setConcat(Matrix a,Matrix b)
         */
        //matrix的值变为matrix和matrixXX的乘积
        matrix.setConcat(matrix, matrixXX);

        /**
         * 11、setRectToRect(RectF src,RectF dst,ScaleToFit stf)
         * 将Rect变化成Rect，与RectStaysRect方法一致，只不过这里是通过stf参数控制
         * stf 包含：FILL FIT_START FIT_CENTER FIT_END
         */

        /**
         * 12、setPolyToPoly(float[] src,int srcIndex,float[] dst,int dstIndex,int pointCount)
         * 通过指定的0--4个点，原始坐标以及变化后的坐标，来得到一个新的矩阵。如果指定0个点则没有效果
         */
        //一个点 平移
        float[] src = {0, 0};
        int DX = 300;
        float[] dst = {0 + DX, 0 + DX};
        Canvas canvas1 = new Canvas();
        matrix.setPolyToPoly(src, 0, dst, 0, 1);
        canvas1.drawBitmap(bitmap, matrix, paint);

        //两个点 旋转
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float[] src1 = {width / 2, height / 2, width, 0};
        float[] dst1 = {width / 2, height / 2, width / 2 + height / 2, height / 2 + width / 2};
        matrix.setPolyToPoly(src1, 0, dst1, 0, 2);
        canvas.drawBitmap(bitmap, matrix, paint);

        //三个点 错切
        Matrix matrix1 = new Matrix();
        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();
        float[] src2 = {0, 0, 0, bh, bw, bh};
        float[] dst2 = {0, 0, 200, bh, bw + 200, bh};
        matrix1.setPolyToPoly(src2, 0, dst2, 0, 3);
        canvas.drawBitmap(bitmap, matrix1, paint);

        //四个点 透视
        Matrix matrix2 = new Matrix();
        int bw2 = bitmap.getWidth();
        int bh2 = bitmap.getHeight();
        float[] src3 = {0, 0, 0, bh2, bw2, bh2, bw2, 0};
        int DX3 = 100;
        float[] dst3 = {0 + DX3, 0, 0, bh2, bw2, bh2, bw2 - DX3, 0};
        matrix2.setPolyToPoly(src3, 0, dst3, 0, 4);
        canvas.drawBitmap(bitmap, matrix2, paint);

        /**
         * 13、invert(Matrix matrix)
         * 反转当前矩阵，如果能反转就返回true，并将反转后的值写入inverse，否则返回false
         * 当前矩阵*inverse = 单位矩阵
         * 反转之后其实是对效果的一种反转
         */

        /**
         * 14、mapPoints:映射点的值到指定的数组中，这个方法可以在矩阵变换以后。给出指定点的值。
         * mapPoints(float[] dst,int dstIndex,float[] src,int srcIndex,int pointCount)
         *
         */

        /**
         * 15、mapVectors
         * 这里是将一个矩阵作用于一个向量，由于向量的平移前后是相等的，所以这个方法不会对translate相关的方法产生反应
         */

        /**
         * 16、mapRect：
         * 返回值即是调用的rectStaysRect() 这里把src中指定的矩阵左上角跟右下角的坐标写入到dst数组中。2
         */

        /**
         * 17、mapRadius:
         * 返回一个圆圈半径的平均值，将matrix作用于一个指定radius半径的圆，随后返回的平均半径
         */

    }

    private void updateMatrix(int action) {
        Matrix matrix = new Matrix();

        switch (action) {
            case ACTION_ROTATE:
                //matrix.postRotate(30);
                matrix.postRotate(30, 200, 200);
                //matrix.postRotate(45, iv.getWidth() / 2, iv.getHeight() / 2);
                //matrix.postRotate(45, 0, 0);
                break;
            case ACTION_TRANSLATE:
                //matrix.preTranslate(200,200);
                matrix.postTranslate(200, 200);
                break;
            case ACTION_SCALE:

                //旋转180度
                matrix.postScale(-0.5f, 1);
                //左右倒置（镜像）
                //matrix.postScale(-1,1);
                //上下倒置
                //matrix.postScale(1,-1);
                //matrix.postScale(2.0f,2.0f,-200,-200);
                break;
            case ACTION_SKEW:
                //matrix.postSkew(0.2f, 0.2f, 100, 100);
                //matrix.postSkew(0.3f, 0f);
                matrix.postSkew(0.3f, 0f, -800, -800);
                break;
            case ACTION_CUSTOM:
                /**
                 * 1、x轴和y轴分别下移100
                 */
                matrix.setTranslate(100, 100);
                /**
                 * 2.1、前乘--移动距离不变，大小缩小0.5倍
                 */
                //matrix.preScale(0.5f,0.5f);

                /**
                 * 2.2、后乘--移动距离和大小均减少0.5倍【即后乘的移动距离受了影响】
                 */

                matrix.postScale(0.5f, 0.5f);
                break;
            default:
                break;
        }

        iv.setImageMatrix(matrix);
    }

    private final class TouchListener implements OnTouchListener {

        /**
         * 记录是拖拉照片模式还是放大缩小照片模式
         */
        private int mode = 0;// 初始状态
        /**
         * 拖拉照片模式
         */
        private static final int MODE_DRAG = 1;
        /**
         * 放大缩小照片模式
         */
        private static final int MODE_ZOOM = 2;

        /**
         * 用于记录开始时候的坐标位置
         */
        private PointF startPoint = new PointF();
        /**
         * 用于记录拖拉图片移动的坐标位置
         */
        private Matrix matrix = new Matrix();
        /**
         * 用于记录图片要进行拖拉时候的坐标位置
         */
        private Matrix currentMatrix = new Matrix();

        /**
         * 两个手指的开始距离
         */
        private float startDis;
        /**
         * 两个手指的中间点
         */
        private PointF midPoint;

        /**
         * 手指点击屏幕的触摸事件
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 手指压下屏幕
                case MotionEvent.ACTION_DOWN:
                    mode = MODE_DRAG;
                    // 记录ImageView当前的移动位置
                    currentMatrix.set(iv.getImageMatrix());
                    startPoint.set(event.getX(), event.getY());
                    break;
                // 手指在屏幕上移动，改事件会被不断触发
                case MotionEvent.ACTION_MOVE:
                    // 拖拉图片
                    if (mode == MODE_DRAG) {
                        float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                        float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                        // 在没有移动之前的位置上进行移动
                        matrix.set(currentMatrix);
                        matrix.postTranslate(dx, dy);
                    }
                    // 放大缩小图片
                    else if (mode == MODE_ZOOM) {
                        float endDis = distance(event);// 结束距离
                        if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                            float scale = endDis / startDis;// 得到缩放倍数
                            matrix.set(currentMatrix);
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                        }
                    }
                    break;
                // 手指离开屏幕
                case MotionEvent.ACTION_UP:
                    // 当触点离开屏幕，但是屏幕上还有触点(手指)
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    break;
                // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = MODE_ZOOM;
                    /** 计算两个手指间的距离 */
                    startDis = distance(event);
                    /** 计算两个手指间的中间点 */
                    if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        midPoint = mid(event);
                        //记录当前ImageView的缩放倍数
                        currentMatrix.set(iv.getImageMatrix());
                    }
                    break;
                default:
                    break;
            }
            iv.setImageMatrix(matrix);
            return true;
        }

        /**
         * 计算两个手指间的距离
         */
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            /** 使用勾股定理返回两点之间的距离 */
            return (float)Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 计算两个手指间的中间点
         */
        private PointF mid(MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }

    }

}
