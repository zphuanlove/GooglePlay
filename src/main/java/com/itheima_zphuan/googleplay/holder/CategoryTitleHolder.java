package com.itheima_zphuan.googleplay.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.bean.CategoryInfoBean;
import com.itheima_zphuan.googleplay.utils.UIUtils;

/**
 * author: 钟佩桓
 * date: 2017/3/6
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfoBean> {

    private TextView mTvTitle;

    /**
     * 决定对应holder所能提供的视图是啥
     *
     * @return
     */
    @Override
    public View initHolderView() {
        mTvTitle = new TextView(UIUtils.getContext());
        int padding = UIUtils.dip2Px(5);
        mTvTitle.setPadding(padding, padding, padding, padding);
        return mTvTitle;
    }

    /**
     * 进行数据和视图的绑定
     *
     * @param data
     */
    @Override
    public void refreshHolderView(CategoryInfoBean data) {
        mTvTitle.setText(data.title);
        mTvTitle.setTextColor(Color.GRAY);
    }
}