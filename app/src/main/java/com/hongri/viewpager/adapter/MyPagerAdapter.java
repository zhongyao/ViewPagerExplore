package com.hongri.viewpager.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.hongri.viewpager.photoview.PhotoView;
import com.hongri.viewpager.util.DataUtil;
import com.hongri.viewpager.util.ImageUtil;
import com.hongri.viewpager.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zhongyao
 * @date 2018/12/2
 */

public class MyPagerAdapter extends PagerAdapter {
    private final String TAG = "MyPagerAdapter";
    private Context mContext;
    private ArrayList<View> mViewLists;
    private int mImageUrlCount;
    private int mImageViewCount;
    private int mImageViewIndex;

    public MyPagerAdapter(Context context, ArrayList<View> dataLists, int imageUrlCount, int imageViewCount) {
        mContext = context;
        mViewLists = dataLists;
        mImageUrlCount = imageUrlCount;
        mImageViewCount = imageViewCount;
    }

    @Override
    public int getCount() {
        return mImageUrlCount > 0 ? mImageUrlCount : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //方式一：使用Glide加载Gif图片（较慢 需要3-5s的时间）
        // Glide.with(mContext).load(DataUtil.getImageUrls()[position]).placeholder(R.drawable
        // .ic_launcher_background).override(800, 800).into((PhotoView)mViewLists.get(position));
        //方式二：使用地方开源库android-gif-drawable 用普通的下载方式，通过输入流等方式创建GifDrawable

        Logger.d("instantiateItem:" + position);

        mImageViewIndex = position % mImageViewCount;
        //if (position == 3) {
        //    HttpUtil.DownLoadImage(position, getImageUrls()[position], (CustomPhotoView)mViewLists.get(position));
        //} else {
//        Glide.with(mContext).load(DataUtil.getImageUrls()[position]).into((PhotoView)mViewLists.get(mImageViewIndex));
        Bitmap bitmap = ImageUtil.drawableToBitmap(mContext.getResources().getDrawable(DataUtil.getImageResource()[position]));
        int height = ((PhotoView) mViewLists.get(mImageViewIndex)).getHeight();
        Log.d(TAG, "ViewHeight:" + height + " bmpWidth:" + bitmap.getWidth() + " bmpHeight:" + bitmap.getHeight() + " isHardwareAccelerated:" + ((PhotoView) mViewLists.get(mImageViewIndex)).isHardwareAccelerated());
        ((PhotoView) mViewLists.get(mImageViewIndex)).setImageBitmap(bitmap);


        //}
        container.addView(mViewLists.get(mImageViewIndex));
        return mViewLists.get(mImageViewIndex);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "哈哈" + position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Logger.d("destroyItem:" + position);
        mImageViewIndex = position % mImageViewCount;
        container.removeView(mViewLists.get(mImageViewIndex));

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("hh","dddd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
