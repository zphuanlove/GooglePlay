package com.itheima_zphuan.googleplay.base;

import android.view.View;

/**
 * author: 钟佩桓
 * date: 2017/3/2
 * des:ViewHolder的基类
 * 1.提供视图
 * 2.接收/加载数据
 * 3.数据和视图的绑定
 */
public abstract class BaseHolder<T> {
    public View mHolderView;//view
    public T mData;//model

    public BaseHolder() {
        //初始化根视图
        mHolderView = initHolderView();
        //找一个符合条件的holder,绑定在自己身上
        mHolderView.setTag(this);
    }

    /**
     * @param data
     * @des 1.接收数据
     * @des 2.数据和视图的绑定
     */
    public void setDataAndRefreshHolderView(T data) {
        //保存数据到成员变量
        mData = data;
        //进行数据的视图的绑定
        refreshHolderView(data);
    }

    /**
     * @return
     * @des 初始化holderView, 决定所能提供的视图长什么样子
     * @des 在BaseHolder中, 不知道如何初始化能提供的视图, 只能交给子类, 子类是必须实现
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called HomeHolder一旦创建的时候
     */
    public abstract View initHolderView();

    /**
     * @param data
     * @des 数据和视图的绑定操作
     * @des 在BaseHolder中不知道如何进行数据和视图的绑定, 只能交给子类, 子类必须实现
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     */
    public abstract void refreshHolderView(T data);

}
