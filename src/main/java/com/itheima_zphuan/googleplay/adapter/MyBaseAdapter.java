package com.itheima_zphuan.googleplay.adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/3/2
 * des:针对BaseAdapter简单封装,针对的是其中的3个方法(getCount,getItem,getItemId)
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private List<T> mDataSets;

    protected MyBaseAdapter(List<T> dataSets) {
        mDataSets = dataSets;
    }

    @Override
    public int getCount() {
        if(mDataSets!=null){
            return mDataSets.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(mDataSets!=null){
            return mDataSets.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
