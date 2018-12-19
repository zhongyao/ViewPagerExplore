package com.hongri.viewpager;

import android.app.Application;
import android.content.Context;

/**
 *
 * @author zhongyao
 * @date 2018/12/18
 */

public class MyApplication extends Application {
    public static Context appContext;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        appContext = base;
    }
}
