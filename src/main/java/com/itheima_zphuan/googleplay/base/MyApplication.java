package com.itheima_zphuan.googleplay.base;


import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

/**
 * author: 钟佩桓
 * date: 2017/2/26
 */
public class MyApplication extends Application{

    /**
     * 全局的上下文
     */
    private static Context mContext;
    /**
     * 主线程的handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程的ID
     */
    private static int mMainThreadId;

    private Map<String,String> mMemProtocalCache = new HashMap<>();

    public Map<String, String> getMemProtocalCache() {
        return mMemProtocalCache;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mMainThreadHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();

        KLog.init(com.itheima_zphuan.googleplay.conf.BuildConfig.LOG_DEBUG,"zph");
        System.out.println("---------myapplication create ------");
    }

    public static Context getContext(){
        return mContext;
    }

    public static Handler getMainThreadHandler(){
        return mMainThreadHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }
}
