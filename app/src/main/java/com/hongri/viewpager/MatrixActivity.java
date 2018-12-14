package com.hongri.viewpager;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * @author hongri
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

        updateMatrix(ACTION_ROTATE);
    }

    private void updateMatrix(int action) {
        Matrix matrix = new Matrix();

        switch (action) {
            case ACTION_ROTATE:
                matrix.postRotate(45, iv.getWidth() / 2, iv.getHeight() / 2);
                break;
            case ACTION_TRANSLATE:
                matrix.postTranslate(250, 250);
                break;
            case ACTION_SCALE:
                matrix.postScale(1 / 2f, 2, 100, 100);
                break;
            case ACTION_SKEW:
                matrix.postSkew(0.2f, 0.2f, 100, 100);
                break;
            case ACTION_CUSTOM:
                matrix.setTranslate(100, 1000);
                matrix.postScale(0.5f, 0.5f);
                break;
            default:
                break;
        }

        iv.setImageMatrix(matrix);
    }
}
