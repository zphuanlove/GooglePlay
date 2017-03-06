package com.itheima_zphuan.googleplay.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.itheima_zphuan.googleplay.adapter.MyBaseAdapter;
import com.itheima_zphuan.googleplay.factory.ThreadPoolProxyFactory;
import com.itheima_zphuan.googleplay.holder.LoadMoreHolder;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/3/2
 * add:上滑加载更多功能
 */
public abstract class SuperBaseAdapter<T> extends MyBaseAdapter implements AdapterView.OnItemClickListener {
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
    private LoadMoreTask mLoadMoreTask;

    private AbsListView mAbsListView;
    private int mState;

    protected SuperBaseAdapter(List<T> dataSets,AbsListView mAbsListView) {
        super(dataSets);
        this.mAbsListView = mAbsListView;
        mAbsListView.setOnItemClickListener(this);
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
//            return VIEWTYPE_NORMAL;
            return getNormalItemViewType(position);
        }
    }
    /**
     * 得到普通条目的ViewType类型
     * 子类可以覆写该方法,返回更多的普通条目的viewType类型
     */
    public int getNormalItemViewType(int position) {
        return VIEWTYPE_NORMAL;//默认值是1
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
                holder = getSpecialBaseHolder(position);
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        /*--------------- 得到数据,然后绑定数据 ---------------*/

        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
            if (hasLoadMore()) {
                //显示正在加载更多的视图
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_LOADING);
                //触发加载更多的数据
                triggerLoadMoreData();
            } else {
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
     * 子类是必须实现吗？-不是
     */
    public LoadMoreHolder getLoadMoreHolder() {
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int curViewType = getItemViewType(position);
        if (curViewType == VIEWTYPE_LOADMORE) {
            if (mState == LoadMoreHolder.LOADMORE_ERROR) {
                //再次触发加载更多
                triggerLoadMoreData();
            }
        } else {//点击了普通条目
            onNormalitemClick(parent, view, position, id);
        }
    }
    /**
     * @des 普通条目的点击事件
     * @des 在SuperBaseAdapter中不知道如何处理普通条目的点击事件, 只能交给子类
     * @des 子类是选择性实现普通条目的点击事件
     */
    public void onNormalitemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    /**
     * 触发加载更多的数据
     */
    private void triggerLoadMoreData() {
        //异步加载
        if(mLoadMoreTask==null) {
            //加载之前显示正在加载更多
            int state = LoadMoreHolder.LOADMORE_LOADING;
            mLoadMoreHolder.setDataAndRefreshHolderView(state);

            mLoadMoreTask = new LoadMoreTask();
            ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadMoreTask);
        }
    }

    private static final int PAGESIZE = 20;//每页请求的总数

    private class LoadMoreTask implements Runnable {
        @Override
        public void run() {
            /*--------------定义刷新UI需要用到的两个值--------------*/
            List loadMoreList = null;
            /*--------------真正的在子线程中加载更多的数据,得到数据,处理数据--------------*/
            try {
                loadMoreList = onLoadMore();
                if (loadMoreList == null || loadMoreList.size() == 0) {
                    //没有更多数据可以加载了
                    mState = LoadMoreHolder.LOADMORE_NONE;
                } else {
                    if (loadMoreList.size() == PAGESIZE) {
                        //说明还有更多数据可以加载
                        //mLoadMoreHolder显示就是正在加载更多-->用户下一次看到的就是正在加载更多
                        mState = LoadMoreHolder.LOADMORE_LOADING;
                    } else {
                        //没有更多数据可以加载
                        mState = LoadMoreHolder.LOADMORE_NONE;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                mState = LoadMoreHolder.LOADMORE_ERROR;//加载更多失败
            }

            /*--------------- 生成了两个临时变量 ---------------*/
            final List finalLoadMoreList = loadMoreList;
            final int finalState = mState;

             /*--------------- 具体刷新ui ---------------*/
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    //刷新ui-->ListView-->修改数据集(mDataSets.add(loadMoreList))-->adapter.notifyDataSetChanged();
                    if (finalLoadMoreList != null) {
                        mDataSets.addAll(finalLoadMoreList);
                        notifyDataSetChanged();
                    }
                    //刷新ui-->mLoadMoreHolder-->mLoadMoreHolder.setDataAndRefreshHolder(curState);
                    mLoadMoreHolder.setDataAndRefreshHolderView(finalState);
                }
            });
            //代表走到run方法体的最后了,任务已经执行完成了,置空任务
            mLoadMoreTask = null;
        }
    }

    /**
     * @return
     * @throws Exception 加载更多过程中,出现了异常
     * @des 在子线中真正的加载更多的数据
     * @des 在SuperBaseAdapter中不知道如何加载更多的数据, 只能交给子类
     * @des 子类是选择性实现, 只有子类有加载更多的时候才覆写该方法, 完成具体加载更多
     */
    public List onLoadMore() throws Exception {
        return null;//默认是null
    }

    /**
     * @return
     * @des 得到BaseHolder具体的子类对象
     * @des 在SuperBaseAdapter中不知道如何创建BaseHolder的子类对象, 所以只能交给子类, 子类必须实现
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @param position
     */
    public abstract BaseHolder getSpecialBaseHolder(int position);

}
