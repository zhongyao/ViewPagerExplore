package com.hongri.viewpager;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.hongri.viewpager.jsinterface.MyJavascriptInterface;
import com.hongri.viewpager.util.DataUtil;

/**
 * @author hongri
 */
public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSettings;
    private final String URL = "https://www.toutiao.com/a6632089991168131591/";
    //private final String URL
    //    = "http://a.mp.uc.cn/article.html?uc_param_str=frdnsnpfvecpntnwprdssskt&client=ucweb&wm_aid=c51bcf6c1553481885da371a16e33dbe&wm_id=482efebe15ed4922a1f24dc42ab654e6&pagetype=share&btifl=100";

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
        //@Override
        //public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        //    return super.shouldOverrideUrlLoading(view, request);
        //}

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //webSettings.setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //webSettings.setJavaScriptEnabled(true);
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
