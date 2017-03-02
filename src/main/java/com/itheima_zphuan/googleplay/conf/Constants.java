package com.itheima_zphuan.googleplay.conf;

import com.itheima_zphuan.googleplay.utils.LogUtils;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class Constants {
    /*
   LogUtils.LEVEL_ALL:打开日志(显示所有的日志输出)
   LogUtils.LEVEL_OFF:关闭日志(屏蔽所有的日志输出)
    */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final class URLS {
        public static final String BASEURL = "http://10.0.2.2:8080/GooglePlayServer/";
        public static final String IMGBASEURL = BASEURL + "image?name=";
    }
}
