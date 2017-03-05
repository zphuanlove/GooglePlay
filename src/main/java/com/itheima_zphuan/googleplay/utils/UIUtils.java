package com.itheima_zphuan.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;

import com.itheima_zphuan.googleplay.base.MyApplication;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 * des: 封装和ui相关的操作
 */
public class UIUtils {
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串信息
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到String.xml中的字符串数组信息
     */
    public static String[] getStrings(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到Color.xml中的颜色信息
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到应用程序包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * dip-->px
     */
    public static int dip2Px(int dip) {
        /*
        1.  px/(ppi/160) = dp
        2.  px/dp = density
         */
        //取得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    public static int px2Dip(int px) {
        // 2.  px/dp = density
        //取得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;
        int dip = (int) (px / density + .5f);
        return dip;
    }

}
