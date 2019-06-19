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
    private static String[] imageUrls = {"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=121431040,2366766377&fm=26&gp=0.jpg",
        "https://p9-tt.byteimg.com/img/tos-cn-i-0022/6be80a53c692456b9b3a92c1f834d944~noop_1080x7552.jpeg",
        "https://p3-tt.bytecdn.cn/img/tos-cn-i-0022/2ce694b57a164e7dbd1abcd5a093aa8f~810x11237_noop.jpeg",
        "http://img.soogif.com/8iXhOare516DocAmoflxHdJAQeOeLDHl.gif",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548840516050&di=d4746333f6edec1859fc750e69665104&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F1%2F59840aae0fe6a.jpg"};

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
