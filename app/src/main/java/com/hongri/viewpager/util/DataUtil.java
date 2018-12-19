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

    private static String[] imageUrls = {"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
        "https://pre-article.xuexi.cn/test/lightProjectDemo/img/photo1.jpg",
        "http://img.soogif.com/8iXhOare516DocAmoflxHdJAQeOeLDHl.gif",
        "https://sf1-ttcdn-tos.pstatp.com/img/tos-cn-i-0022/43b16f00ea6141629df56505a4d0faf4~642x2356_noop.jpeg",
        "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg"};

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
