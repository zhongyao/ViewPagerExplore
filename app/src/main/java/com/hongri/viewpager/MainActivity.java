package com.hongri.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.hongri.viewpager.util.ToastUtil;

/**
 * @author hongri
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.layout).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(MainActivity.this,"根布局点击");
            }
        });

        findViewById(R.id.sublayout).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(MainActivity.this,"子根布局点击");
            }
        });

        findViewById(R.id.tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                ToastUtil.showToast(MainActivity.this,"子布局点击");
                //Intent intent = new Intent();
                //intent.setClass(MainActivity.this, ViewPagerActivity.class);
                //startActivity(intent);
                //finish();
                //overridePendingTransition(R.anim.activity_up_finish, R.anim.activity_down_finish);
            }
        });
    }
}
