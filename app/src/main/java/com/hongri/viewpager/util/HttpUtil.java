package com.hongri.viewpager.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import com.hongri.viewpager.widget.CustomPhotoView;
import pl.droidsonroids.gif.GifDrawable;

/**
 * @author zhongyao
 * @date 2018/12/19
 */

public class HttpUtil {

    private static String mUrl;
    private static CustomPhotoView mPhotoView;
    private static final int SUCCESS = 1;

    public static void DownLoadImage(int position, String url, CustomPhotoView photoView) {
        mUrl = url;
        mPhotoView = photoView;
        new Thread(saveFileRunnable).start();
    }

    private static Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    try {
                        byte[] bytes = (byte[])msg.obj;
                        GifDrawable drawable = new GifDrawable(bytes);
                        mPhotoView.setImageDrawable(drawable);

                        mPhotoView.setBytes(bytes);
                    } catch (IOException e) {
                        Logger.d("HttpUtil:" + e);
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private static Runnable saveFileRunnable = new Runnable() {
        @Override
        public void run() {
            InputStream inputStream;
            byte[] bytes;
            try {
                inputStream = getImageStream(mUrl);
                bytes = InputStreamToByte(inputStream);
                Message message = Message.obtain();
                message.obj = bytes;
                message.what = SUCCESS;
                messageHandler.sendMessage(message);
            } catch (Exception e) {
                Logger.d("HttpUtil-:" + e);
                e.printStackTrace();
            }

        }

    };

    public static InputStream getImageStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * 输入流转字节流
     */
    private static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        byte[] data;
        /**
         *
         * */
        Logger.d("is.available:" + is.available());
        while ((length = is.read(buffer)) != -1) {
            Logger.d("length:" + length + " buffer:" + buffer.length);
            bos.write(buffer, 0, length);
        }
        data = bos.toByteArray();
        bos.close();
        return data;
    }
}
