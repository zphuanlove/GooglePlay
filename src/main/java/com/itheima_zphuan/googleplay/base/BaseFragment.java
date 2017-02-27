package com.itheima_zphuan.googleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima_zphuan.googleplay.utils.UIUtils;

/**
 * author: 钟佩桓
 * date: 2017/2/28
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LoadingPager loadingPager = new LoadingPager(UIUtils.getContext()) {
            /**
             * @des 在子线程中真正的加载具体的数据
             * @called triggerLoadData()方法被调用的时候
             */
            @Override
            public LoadedResult initData() {
                return BaseFragment.this.initData();
            }

            /**
             * @return
             * @des 决定成功视图长什么样子(需要定义成功视图)
             * @des 数据和视图的绑定过程
             * @called triggerLoadData()方法被调用, 而且数据加载完成了, 而且数据加载成功
             */
            @Override
            protected View initSuccessView() {
                return BaseFragment.this.initSuccessView();
            }
        };

        //触发加载数据
        loadingPager.triggerLoadData();

        //4种视图中的一种(加载中,错误,空,成功)
        return loadingPager;
    }

    /**
     * @des 在子线程中真正的加载具体的数据
     * @des 在BaseFragent不知道如何具体的加载数据, 只能交给子类, 子类必须实现
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called triggerLoadData()方法被调用的时候
     */
    public abstract LoadingPager.LoadedResult initData();

    /**
     * @return
     * @des 决定成功视图长什么样子(需要定义成功视图)
     * @des 数据和视图的绑定过程
     * @des 在BaseFragment中不知道成功视图具体是啥, 不知道具体数据是啥, 不知道数据和视图如何绑定, 交给子类, 子类必须实现
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法交给子类具体实现
     * @called triggerLoadData()方法被调用, 而且数据加载完成了, 而且数据加载成功
     */
    public abstract View initSuccessView();
}
