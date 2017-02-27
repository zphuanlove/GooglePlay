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
 *      1.提供视图-->4种视图中的一种(加载中视图,错误视图,空视图,成功视图)-->把自身提供出去就可以
 *      2.加载数据
 *      3.数据和视图的绑定
 */
public class LoadingPager extends FrameLayout {

    public static final int STATE_LOADING = 0;//加载中
    public static final int STATE_ERROR = 1;//错误
    public static final int STATE_SUCCESS = 2;//成功
    public static final int STATE_EMPTY = 3;//空
    public int mCurState = STATE_LOADING;//默认是加载中的情况

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

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

    }


}
