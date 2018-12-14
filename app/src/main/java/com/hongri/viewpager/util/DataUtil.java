package com.hongri.viewpager.util;

import com.hongri.viewpager.R;

/**
 * @author zhongyao
 * @date 2018/12/2
 */

public class DataUtil {
    private static int[] images = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.long_pic,
        R.drawable.bird};

    private static String[] descriptions = {"描述一", "描述二", "描述三", "描述四", "描述五"};

    private static String[] imageUrls = {"http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
        "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
        "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
        "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
        "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg"};

    public static int[] getImageResource() {
        return images;
    }

    public static String[] getDescriptions() {
        return descriptions;
    }

    public static String [] getImageUrls(){
        return imageUrls;
    }
 }
