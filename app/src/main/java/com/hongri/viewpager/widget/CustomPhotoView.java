package com.hongri.viewpager.widget;

import android.content.Context;
import com.hongri.viewpager.photoview.PhotoView;

/**
 *
 * @author zhongyao
 * @date 2018/12/3
 */

public class CustomPhotoView extends PhotoView {
    public CustomPhotoView(Context context) {
        super(context);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(scaleType);
    }
}
