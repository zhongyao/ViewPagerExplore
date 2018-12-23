package com.hongri.viewpager.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.hongri.viewpager.photoview.PhotoView;
import com.hongri.viewpager.util.DataUtil;
import com.hongri.viewpager.util.HttpUtil;
import com.hongri.viewpager.widget.CustomPhotoView;

import static com.hongri.viewpager.util.DataUtil.getImageUrls;

/**
 * @author zhongyao
 * @date 2018/12/2
 */

public class MyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<View> mDataLists;

    public MyPagerAdapter(Context context, ArrayList<View> dataLists) {
        mContext = context;
        mDataLists = dataLists;
    }

    @Override
    public int getCount() {
        return (mDataLists != null && mDataLists.size() > 0) ? mDataLists.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //方式一：使用Glide加载Gif图片（较慢 需要3-5s的时间）
        // Glide.with(mContext).load(DataUtil.getImageUrls()[position]).placeholder(R.drawable.ic_launcher_background).override(800, 800).into((PhotoView)mDataLists.get(position));
        //方式二：使用地方开源库android-gif-drawable 用普通的下载方式，通过输入流等方式创建GifDrawable
        if (position == 3){
            HttpUtil.DownLoadImage(position, getImageUrls()[position],(CustomPhotoView)mDataLists.get(position));
        }else {
            Glide.with(mContext).load(DataUtil.getImageUrls()[position]).into((PhotoView)mDataLists.get(position));
        }
        container.addView(mDataLists.get(position));
        return mDataLists.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "哈哈" + position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mDataLists.get(position));
    }
}
