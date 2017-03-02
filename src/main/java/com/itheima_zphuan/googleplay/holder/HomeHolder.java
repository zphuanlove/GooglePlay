package com.itheima_zphuan.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.itheima_zphuan.googleplay.R;
import com.itheima_zphuan.googleplay.utils.UIUtils;

/**
 * author: 钟佩桓
 * date: 2017/3/2
 * des:  HomeHolder的作用：
 * 1.提供视图
 * 2.接收数据
 * 3.数据和视图的绑定
 */
public class HomeHolder {

    public View mHolderView;//view
    public String mData;//model

    private TextView mTvTmp1;
    private TextView mTvTmp2;

    public HomeHolder() {
        //初始化根视图
        mHolderView = initHolderView();
        //找到一个符合条件的holder，绑定在自己身上
        mHolderView.setTag(this);
    }

    /**
     * @return 当前Item对应的根视图View
     * @des 初始化holderView, 决定所能提供的视图长什么样子
     */
    private View initHolderView() {
        View itemView = View.inflate(UIUtils.getContext(), R.layout.item_temp, null);
        //初始化孩子对象
        mTvTmp1 = (TextView) itemView.findViewById(R.id.tmp_tv_1);
        mTvTmp2 = (TextView) itemView.findViewById(R.id.tmp_tv_2);
        return itemView;
    }

    /**
     * @des 1.接收数据
     * @des 2.数据和视图的绑定
     */
    public void setDataAndRefreshHolderView(String data) {
        //保存数据到成员变量
        mData = data;
        //进行数据的视图的绑定
        refreshHolderView(data);
    }

    /**
     * @des 数据和视图的绑定操作
     */
    private void refreshHolderView(String data) {
        //view-->成员变量
        //data-->局部变量有,成员变量里面也有
        //data+view
        mTvTmp1.setText("我是头-" + data);
        mTvTmp2.setText("我是尾巴-" + data);
    }
}
