package com.itheima_zphuan.googleplay.base;

import android.view.View;
import android.view.ViewGroup;

import com.itheima_zphuan.googleplay.adapter.MyBaseAdapter;
import com.itheima_zphuan.googleplay.holder.LoadMoreHolder;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/3/2
 * add:上滑加载更多功能
 */
public abstract class SuperBaseAdapter<T> extends MyBaseAdapter {
    /**
     * 加载更多
     */
    public static final int VIEWTYPE_LOADMORE = 0;
    /**
     * 普通的Item
     */
    public static final int VIEWTYPE_NORMAL = 1;
    /**
     * 加载更多的holder
     */
    private LoadMoreHolder mLoadMoreHolder;

    protected SuperBaseAdapter(List<T> dataSets) {
        super(dataSets);
    }

    /**
     * get(得到)ViewType(ViewType)Count(总数),默认是1钟类型
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;//1(普通类型)+1(加载更多) = 2
    }

    /**
     * get(得到)Item(指定条目)ViewType(ViewType类型)(int position),默认是0
     * 范围：0 ~ getViewTypeCount() - 1
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMORE;
        } else {
            return VIEWTYPE_NORMAL;
        }
    }

    /**
     * 注意！别忘掉复写父类的getCount条目总数
     *
     * @return
     */
    @Override
    public int getCount() {
        return super.getCount() + 1;//其实就是加的加载更多的条目
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*--------------- 决定根布局(itemView) ---------------*/
        BaseHolder holder;
        if (convertView == null) {
            //创建holder对象
            if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
                holder = getLoadMoreHolder();
            } else {
                holder = getSpecialBaseHolder();
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        /*--------------- 得到数据,然后绑定数据 ---------------*/

        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
            if(hasLoadMore()){
                //显示正在加载更多的视图
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_LOADING);
            }else{
                //隐藏加载更多的视图,以及重试视图
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_NONE);
            }
        } else {
            //data 注意放进来！否则数组角标越界！
            Object data = mDataSets.get(position);
            holder.setDataAndRefreshHolderView(data);
        }
        return holder.mHolderView;//其实这个convertView是经过了数据绑定的convertView
    }

    /**
     * @des 属于BaseHolder的子类对象
     * @des 加载更多的Holder的对象
     */
    private LoadMoreHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    /**
     * @return
     * @des 是否有加载更多, 默认没有加载更多
     * @des 子类可以覆写该方法, 可以决定有加载跟多
     */
    public boolean hasLoadMore() {
        return false;//默认没有加载更多
    }


    /**
     * @return
     * @des 得到BaseHolder具体的子类对象
     * @des 在SuperBaseAdapter中不知道如何创建BaseHolder的子类对象, 所以只能交给子类, 子类必须实现
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     */
    public abstract BaseHolder getSpecialBaseHolder();
}
