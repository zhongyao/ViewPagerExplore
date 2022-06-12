package com.hongri.viewpager.jsapi;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import com.hongri.viewpager.activity.ViewPagerPhotosActivity;

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

    /**
     * 点击WebView图片时--JS调用方法
     *
     * @param img
     */
    @JavascriptInterface
    public void openImage(String img) {
        Intent intent = new Intent(mContext, ViewPagerPhotosActivity.class);
        intent.putExtra("imageUrls",mImageUrls);
        intent.putExtra("curImageUrl",img);
        mContext.startActivity(intent);
    }
}
