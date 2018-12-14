package com.hongri.viewpager.jsinterface;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import com.hongri.viewpager.ViewPagerActivity;

/**
 * @author zhongyao
 * @date 2018/12/10
 */

public class MyJavascriptInterface {
    private Context mContext;
    private String[] mImageUrls;

    public MyJavascriptInterface(Context context, String[] imageUrls) {
        mContext = context;
        mImageUrls = imageUrls;
    }

    @JavascriptInterface
    public void openImage(String img) {
        Intent intent = new Intent(mContext, ViewPagerActivity.class);
        intent.putExtra("imageUrls",mImageUrls);
        intent.putExtra("curImageUrl",img);
        mContext.startActivity(intent);
    }
}
