package com.hongri.viewpager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import com.hongri.viewpager.Interface.STPhotoSaveCallBack;
import com.hongri.viewpager.util.ImageUtil;
import com.hongri.viewpager.util.Logger;
import com.hongri.viewpager.util.ScreenShotUtil;
import com.hongri.viewpager.util.ToastUtil;

/**
 * @author zhongyao
 */
public class ScreenShotActivity extends Activity implements View.OnClickListener,STPhotoSaveCallBack {

    private ImageView iv,iv2;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_screen_shot);

        iv = findViewById(R.id.iv);
        iv2 = findViewById(R.id.iv2);
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //Bitmap bitmap = ScreenShotUtil.capture1(this);
                Bitmap bitmap = ScreenShotUtil.shotActivityNoStatusBar(this);
                ImageUtil.saveBmp2Gallery(this, bitmap, "哈哈哈", this);
                iv2.setImageBitmap(bitmap);
                btn1.setVisibility(View.GONE);
                iv2.setVisibility(View.VISIBLE);
                ToastUtil.showToast(this,"点击事件");
                break;
            default:
                break;
        }
    }

    @Override
    public void savePhotoSuccess() {
        Logger.d("截图存储成功");
    }

    @Override
    public void savePhotoFailure() {
        Logger.d("截图存储失败");
    }
}
