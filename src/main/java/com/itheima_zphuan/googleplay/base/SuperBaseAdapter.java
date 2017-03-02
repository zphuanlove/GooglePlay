package com.itheima_zphuan.googleplay.base;

import android.view.View;
import android.view.ViewGroup;

import com.itheima_zphuan.googleplay.adapter.MyBaseAdapter;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/3/2
 */
public abstract class SuperBaseAdapter<T> extends MyBaseAdapter {

    protected SuperBaseAdapter(List<T> dataSets) {
        super(dataSets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*--------------- 决定根布局(itemView) ---------------*/
        BaseHolder holder;
        if(convertView == null){
            //创建holder对象
            holder = getSpecialBaseHolder();
        }else{
            holder = (BaseHolder) convertView.getTag();
        }
        /*--------------- 得到数据,然后绑定数据 ---------------*/
        //data
        Object data =  mDataSets.get(position);
        holder.setDataAndRefreshHolderView(data);
        return holder.mHolderView;//其实这个convertView是经过了数据绑定的convertView
    }

    /**
     * @return
     * @des 得到BaseHolder具体的子类对象
     * @des 在SuperBaseAdapter中不知道如何创建BaseHolder的子类对象, 所以只能交给子类, 子类必须实现
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     */
    public abstract BaseHolder getSpecialBaseHolder();
}
