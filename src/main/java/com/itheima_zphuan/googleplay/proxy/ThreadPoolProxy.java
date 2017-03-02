package com.itheima_zphuan.googleplay.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: 钟佩桓
 * date: 2017/3/2
 * des: 线程池代理类,替线程池完成相关操作
 * 1.代理模式就是多一个代理类出来，替原对象进行一些操作
 * 2.只需提供使用原对象时候真正关心的方法(提交任务,执行任务,移除任务)
 * 3.可以对原对象方法进行增强
 */
public class ThreadPoolProxy {
    ThreadPoolExecutor mExecutor;

    private int mCorePoolSize;//核心池的大小
    private int mMaximumPoolSize;//线程最大线程数

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
    }

    /**
     * 初始化ThreadPoolExecutor
     */
    public void initThreadPoolExecutor() {
        //isShutDown当调用shutdown()方法后返回为true。
        //isTerminated当调用shutdown()方法后，并且所有提交的任务完成后返回为true
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    long keepAliveTime = 0;//额外线程的空闲时间
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue();//无界队列
                    //当添加任务出错时的策略捕获器，如果出现错误，不做处理
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, keepAliveTime,
                            unit, workQueue, handler);
                }
            }
        }
    }

    /**
     * 提交任务
     * @param task
     */
    public void submit(Runnable task){
        initThreadPoolExecutor();
        mExecutor.submit(task);
    }

    /**
     * 执行任务
     * @param task
     */
    public void execute(Runnable task){
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    /**
     * 移除任务
     * @param task
     */
    public void remove(Runnable task){
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }

}
