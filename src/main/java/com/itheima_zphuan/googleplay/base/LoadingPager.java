package com.itheima_zphuan.googleplay.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.itheima_zphuan.googleplay.R;
import com.itheima_zphuan.googleplay.utils.UIUtils;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 * des:
 * 1.提供视图-->4种视图中的一种(加载中视图,错误视图,空视图,成功视图)-->把自身提供出去就可以
 * 2.加载数据
 * 3.数据和视图的绑定
 */
public abstract class LoadingPager extends FrameLayout {

    public static final int STATE_LOADING = 0;//加载中
    public static final int STATE_ERROR = 1;//错误
    public static final int STATE_SUCCESS = 2;//成功
    public static final int STATE_EMPTY = 3;//空
    public int mCurState = STATE_LOADING;//默认是加载中的情况

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    public LoadingPager(Context context) {
        super(context);
        initCommonView();

    }

    /**
     * @des 初始化常规视图(加载中视图, 错误视图, 空视图3个静态视图)
     * @called LoadingPager创建的时候
     */
    private void initCommonView() {
        //加载中视图
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);

        //错误视图
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        this.addView(mErrorView);

        //空视图
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);

        //根据当前页面状态刷新UI
        refreshViewByState();
    }

    /**
     * @des 根据状态刷新ui(决定LoadingPager到底提供四种视图中的哪一种)
     * @called 1.LoadingPager创建的时候
     * @called 2.外界调用了triggerLoadData加载数据, 数据加载之前
     * @called 3.外界调用了triggerLoadData加载数据, 而且数据加载完成了
     */
    private void refreshViewByState() {
        //控制 加载中视图 显示隐藏
        if (mCurState == STATE_LOADING) {
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            mLoadingView.setVisibility(View.GONE);
        }

        //控制 错误视图 显示隐藏
        if (mCurState == STATE_ERROR) {
            mErrorView.setVisibility(View.VISIBLE);
        } else {
            mErrorView.setVisibility(View.GONE);
        }

        //控制 空视图 显示隐藏
        if (mCurState == STATE_EMPTY) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
        //这里就可能有成功视图了.因为数据已经加载完成了.而且数据加载成功了
        if (mSuccessView == null && mCurState == STATE_SUCCESS) {
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }
        // 控制 成功视图的 显示隐藏
        if (mSuccessView != null) {
            if (mCurState == STATE_SUCCESS) {
                mSuccessView.setVisibility(VISIBLE);
            } else {
                mSuccessView.setVisibility(GONE);
            }
        }

    }


    /**
     * @des 触发加载数据
     * @called 外界想让LoadingPager触发加载数据的时候调用
     */
    public void triggerLoadData() {
        new Thread(new LoadDataTask()).start();
    }


    private class LoadDataTask implements Runnable {
        @Override
        public void run() {
            //真正的在子线程中加载具体的数据-->得到数据
            LoadedResult loadedResult = initData();

            //处理数据
            mCurState = loadedResult.getState();

            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    // 刷新UI(决定到底提供4种视图中的哪一种视图)
                    refreshViewByState();//mCurState-->Int
                }
            });

        }
    }

    /**
     * @des 在子线程中真正的加载具体的数据
     * @des 在LoadingPager中, 不知道如何具体加载数据, 交给子类,子类必须实现
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called triggerLoadData()方法被调用的时候
     */

    public abstract LoadedResult initData();

    /**
     * @return
     * @des 决定成功视图长什么样子(需要定义成功视图)
     * @des 数据和视图的绑定过程
     * @des 在LoadingPager中, 其实不知道成功视图具体长什么样子, 更加不知道视图和数据如何绑定, 交给子类, 必须实现
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called triggerLoadData()方法被调用, 而且数据加载完成了, 而且数据加载成功
     */
    protected abstract View initSuccessView();

    /**
     *  标识数据加载结果的枚举类
     */
    public enum LoadedResult {
        /**
         * STATE_ERROR = 1;//错误
         * STATE_SUCCESS = 2;//成功
         * STATE_EMPTY = 3;//空
         */
        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);

        private int state;

        public int getState() {
            return state;
        }

        LoadedResult(int state) {
            this.state = state;
        }
    }

}
