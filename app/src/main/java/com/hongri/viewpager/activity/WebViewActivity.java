package com.hongri.viewpager.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.hongri.viewpager.R;
import com.hongri.viewpager.jsapi.MyJavascriptInterface;
import com.hongri.viewpager.util.DataUtil;

/**
 * @author hongri
 * WebView相关Activity
 * 参考：https://www.jb51.net/article/106272.htm
 */
public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSettings;
    private final String URL = "https://www.toutiao.com/a6632089991168131591/";

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(URL);
        webView.addJavascriptInterface(new MyJavascriptInterface(this, DataUtil.getImageUrls()), "imagelistener");
        webView.setWebViewClient(new XWebViewClient());
    }

    private class XWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            //待网页加载完全后设置图片点击的监听方法
            addImageClickListener(view);
        }
    }

    private void addImageClickListener(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
            "var objs = document.getElementsByTagName(\"img\"); " +
            "for(var i=0;i<objs.length;i++) " +
            "{"
            + " objs[i].onclick=function() " +
            " { "
            + "  window.imagelistener.openImage(this.src); " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
            " } " +
            "}" +
            "})()");
    }

}
