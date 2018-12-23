package com.hongri.viewpager.widget;

import java.io.InputStream;

import android.content.Context;
import com.hongri.viewpager.photoview.PhotoView;

/**
 * @author zhongyao
 * @date 2018/12/3
 */

public class CustomPhotoView extends PhotoView {

    private InputStream inputStream;

    private byte[] bytes;

    public CustomPhotoView(Context context) {
        super(context);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(scaleType);
    }

    public void setImageInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getImageInputStream(InputStream inputStream) {
        return inputStream;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
