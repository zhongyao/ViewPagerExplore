package com.hongri.viewpager;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * @author hongri
 *
 * 参考：
 * https://blog.csdn.net/cquwentao/article/details/51445269
 * https://blog.csdn.net/gaojinshan/article/details/17334181
 *
 * set系列方法：setTranslate setScale setRotate setSkew 设置 会覆盖之前的参数
 * pre系列方法：preTranslate preScale preRotate preSkew 矩阵先乘 M' = M * T(dx, dy)
 * post系列方法：postTranslate postScale postRotate postSkew 矩阵后乘 M' = T(dx, dy)*M
 */
public class MatrixActivity extends AppCompatActivity {

    private ImageView iv;
    public static final int ACTION_ROTATE = 101;
    public static final int ACTION_TRANSLATE = 102;
    public static final int ACTION_SCALE = 103;
    public static final int ACTION_SKEW = 104;
    public static final int ACTION_CUSTOM = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_matrix);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        iv = findViewById(R.id.iv);
        iv.setScaleType(ScaleType.MATRIX);

        updateMatrix(ACTION_CUSTOM);
    }

    private void updateMatrix(int action) {
        Matrix matrix = new Matrix();

        switch (action) {
            case ACTION_ROTATE:
                //matrix.postRotate(45, iv.getWidth() / 2, iv.getHeight() / 2);
                matrix.postRotate(45, 0, 0);
                break;
            case ACTION_TRANSLATE:
                matrix.postTranslate(250, 250);
                break;
            case ACTION_SCALE:
                //matrix.postScale(1 / 2f, 2, 100, 100);
                matrix.postScale(2, 2, 0, 0);
                break;
            case ACTION_SKEW:
                //matrix.postSkew(0.2f, 0.2f, 100, 100);
                matrix.postSkew(0.2f,0f);
                break;
            case ACTION_CUSTOM:
                matrix.setTranslate(10, 10);
                matrix.postScale(0.5f, 0.5f);
                break;
            default:
                break;
        }

        iv.setImageMatrix(matrix);
    }
}
