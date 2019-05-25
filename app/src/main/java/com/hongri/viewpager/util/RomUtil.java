package com.hongri.viewpager.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class RomUtil {

    private static final String ROM_OPPO = "com.oppo.feature.screen.heteromorphism";
    private static final String ROM_VIVO = "android.util.FtFeature";
    private static final String ROM_HUAWEI = "com.huawei.android.util.HwNotchSizeUtil";
    private static final String ROM_MIUI = "android.os.SystemProperties";

    private static boolean sInit = false;
    private static boolean sHasNotchScreen = false;
    private static boolean sInitDevice = false;
    private static int sVirtualBarHeight = 0;
    private static int[] sNotchSize;

    class AvailableRomType {
        static final int MIUI = 1;
        static final int FLYME = 2;
        static final int ANDROID_NATIVE = 3;
        static final int NA = 4;
    }

    /**
     * The version name from PackageManager.
     *
     * @return version name of the current running app.
     */
    public static String getVersionName(Context context) {
        if (context == null) {
            return "";
        }
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取是否存在NavigationBar
     *
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String)m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

    /**
     * 获取虚拟功能键高度
     *
     * @param context
     * @return
     */
    public static int getVirtualBarHeight(Context context) {
        if (sInitDevice) {
            return sVirtualBarHeight;
        }
        int vh = 0;
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sVirtualBarHeight = vh;
        sInitDevice = true;
        return vh;
    }

    public static boolean isLightStatusBarAvailable() {
        if (isMIUIV6OrAbove() || isFlymeV4OrAbove() || isAndroidMOrAbove()) {
            return true;
        }
        return false;
    }

    public static int getLightStatusBarAvailableRomType() {
        if (isMIUIV6OrAbove()) {
            return AvailableRomType.MIUI;
        }

        if (isFlymeV4OrAbove()) {
            return AvailableRomType.FLYME;
        }

        if (isAndroidMOrAbove()) {
            return AvailableRomType.ANDROID_NATIVE;
        }

        return AvailableRomType.NA;
    }

    /**
     * Flyme V4的displayId格式为 [Flyme OS 4.x.x.xA]，Flyme V5的displayId格式为 [Flyme 5.x.x.x beta]
     */
    private static boolean isFlymeV4OrAbove() {
        String displayId = Build.DISPLAY;
        if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
            String[] displayIdArray = displayId.split(" ");
            for (String temp : displayIdArray) {
                //版本号4以上，形如4.x.
                if (temp.matches("^[4-9]\\.(\\d+\\.)+\\S*")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * MIUI V6对应的versionCode是4，MIUI V7对应的versionCode是5
     */
    private static boolean isMIUIV6OrAbove() {
        String miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code");
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                int miuiVersionCode = Integer.parseInt(miuiVersionCodeStr);
                if (miuiVersionCode >= 4) {
                    return true;
                }
            } catch (Exception e) {}
        }
        return false;
    }

    /**
     * Android Api 23以上
     */
    private static boolean isAndroidMOrAbove() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }

    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }

    /**
     * 获取刘海屏宽高
     */
    public static int[] getNotchSize(Activity activity) {
        if (sNotchSize != null && sNotchSize.length == 2) {
            return sNotchSize;
        }

        if (hasNotchInMiui(activity)) {
            sNotchSize = getNotchSizeInMiui(activity);
        } else if (hasNotchInHuawei(activity)) {
            sNotchSize = getNotchSizeInHuaWei(activity);
        } else if (hasNotchInOppo(activity)) {
            sNotchSize = getNotchSizeInOppo(activity);
        } else if (hasNotchInVivo(activity)) {
            sNotchSize = getNotchSizeInVivo(activity);
        } else if (hasNotchScreen(activity)) {
            sNotchSize = getNotchSizeOther(activity);
        } else {
            sNotchSize = new int[] {0, 0};
        }

        return sNotchSize;
    }

    private static int[] getNotchSizeInMiui(Activity activity) {
        int[] ret = new int[] {0, 0};

        if (activity == null) {
            return ret;
        }

        try {
            int widthResourceId = activity.getResources().getIdentifier("notch_width", "dimen", "android");
            if (widthResourceId > 0) {
                ret[0] = activity.getResources().getDimensionPixelSize(widthResourceId);
            }
            int heightResourceId = activity.getResources().getIdentifier("notch_height", "dimen", "android");
            if (heightResourceId > 0) {
                ret[1] = activity.getResources().getDimensionPixelSize(heightResourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    private static int[] getNotchSizeInHuaWei(Activity activity) {
        int[] ret = new int[] {0, 0};

        if (activity == null) {
            return ret;
        }

        try {
            ClassLoader cl = activity.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[])get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static int[] getNotchSizeInOppo(Activity activity) {
        int[] ret = new int[] {0, 0};

        if (activity == null) {
            return ret;
        }

        ret[0] = 324;
        ret[1] = 80;

        return ret;
    }

    private static int[] getNotchSizeInVivo(Activity activity) {
        int[] ret = new int[] {0, 0};

        if (activity == null) {
            return ret;
        }

        ret[0] = DisplayUtil.dip2px(activity, 100);
        ret[1] = DisplayUtil.dip2px(activity, 27);

        return ret;
    }

    private static int[] getNotchSizeOther(Activity activity) {
        int[] ret = new int[] {0, 0};

        if (activity == null) {
            return ret;
        }

        ret[0] = DisplayUtil.dip2px(activity, 100);
        ret[1] = DisplayUtil.dip2px(activity, 27);

        return ret;
    }

    /**
     * 设备是否是刘海屏
     */
    public static boolean hasNotchScreen(Context activity) {
        if (sInit) {
            return sHasNotchScreen;
        }
        if (hasNotchInOppo(activity) || hasNotchInVivo(activity)
            || hasNotchInHuawei(activity) || hasNotchInMiui(activity)) {
            sHasNotchScreen = true;
        } else {
            sHasNotchScreen = false;
        }
        sInit = true;

        return sHasNotchScreen;
    }

    /**
     * 判断oppo手机是否是齐刘海屏
     */
    private static boolean hasNotchInOppo(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().hasSystemFeature(ROM_OPPO);
    }

    /**
     * 判断vivo手机是否是齐刘海屏
     */
    private static boolean hasNotchInVivo(Context context) {
        if (context == null) {
            return false;
        }

        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class ftFeature = cl.loadClass(ROM_VIVO);
            Method[] methods = ftFeature.getDeclaredMethods();
            if (methods != null) {
                for (int i = 0; i < methods.length; i++) {
                    Method method = methods[i];
                    if (method.getName().equalsIgnoreCase("isFeatureSupport")) {
                        hasNotch = (boolean)method.invoke(ftFeature, 0x00000020);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hasNotch = false;
        }
        return hasNotch;
    }

    /**
     * 判断华为手机是否是齐刘海屏
     */
    private static boolean hasNotchInHuawei(Context context) {
        if (context == null) {
            return false;
        }

        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass(ROM_HUAWEI);
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            hasNotch = (boolean)get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNotch;
    }

    private static boolean hasNotchInMiui(Context activity) {
        if (activity == null) {
            return false;
        }

        if (!isXiaoMi()) {
            return false;
        }

        int result = 0;
        try {
            ClassLoader classLoader = activity.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = classLoader.loadClass(ROM_MIUI);
            //参数类型
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = int.class;
            Method getInt = SystemProperties.getMethod("getInt", paramTypes);
            //参数
            Object[] params = new Object[2];
            params[0] = new String("ro.miui.notch");
            params[1] = new Integer(0);
            result = (Integer)getInt.invoke(SystemProperties, params);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result == 1;
    }

    private static boolean isXiaoMi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }

}