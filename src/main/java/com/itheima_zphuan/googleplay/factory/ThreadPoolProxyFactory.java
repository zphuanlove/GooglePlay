package com.itheima_zphuan.googleplay.factory;

import com.itheima_zphuan.googleplay.proxy.ThreadPoolProxy;

/**
 * author: 钟佩桓
 * date: 2017/3/2
 * des:ThreadPoolProxy工厂类,封装对ThreadPoolProxy创建
 */
public class ThreadPoolProxyFactory {
    //普通类型的线程池代理
    static ThreadPoolProxy mNormalThreadPoolProxy;

    //下载类型的线程池代理
    static ThreadPoolProxy mDownLoadThreadPoolProxy;

    /**
     * 得到普通类型的线程池代理
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5, 5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 得到下载类型的线程池代理
     */
    public static ThreadPoolProxy getDownLoadThreadPoolProxy() {
        if (mDownLoadThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mDownLoadThreadPoolProxy == null) {
                    mDownLoadThreadPoolProxy = new ThreadPoolProxy(3, 3);
                }
            }
        }
        return mDownLoadThreadPoolProxy;
    }
}