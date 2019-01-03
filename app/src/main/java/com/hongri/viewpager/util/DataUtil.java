package com.hongri.viewpager.util;

import com.hongri.viewpager.R;

/**
 * @author zhongyao
 * @date 2018/12/2
 *
 * 地图：https://upload.wikimedia.org/wikipedia/commons/3/33/Physical_Political_World_Map.jpg
 */

public class DataUtil {
    private static int[] images = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.long_pic,
        R.drawable.bird};

    private static String[] descriptions = {"描述一", "描述二", "描述三", "描述四", "描述五"};

    /**
     * 默认第四张gif图
     */
    private static String[] imageUrls = {"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
        "https://pre-article.xuexi.cn/test/lightProjectDemo/img/photo1.jpg",
        "https://p3-tt.bytecdn.cn/img/tos-cn-i-0022/2ce694b57a164e7dbd1abcd5a093aa8f~810x11237_noop.jpeg",
        "http://img.soogif.com/8iXhOare516DocAmoflxHdJAQeOeLDHl.gif",
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
