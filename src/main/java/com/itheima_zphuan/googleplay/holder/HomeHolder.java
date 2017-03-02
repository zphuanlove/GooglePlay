package com.itheima_zphuan.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima_zphuan.googleplay.R;
import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.bean.ItemBean;
import com.itheima_zphuan.googleplay.utils.StringUtils;
import com.itheima_zphuan.googleplay.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: 钟佩桓
 * date: 2017/3/2
 * des:  HomeHolder的作用：
 * 1.提供视图
 * 2.接收数据
 * 3.数据和视图的绑定
 */
public class HomeHolder extends BaseHolder<ItemBean> {


    @BindView(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;
    @BindView(R.id.item_appinfo_tv_title)
    TextView mItemAppinfoTvTitle;
    @BindView(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;
    @BindView(R.id.item_appinfo_tv_size)
    TextView mItemAppinfoTvSize;
    @BindView(R.id.item_appinfo_tv_des)
    TextView mItemAppinfoTvDes;

    /**
     * @return
     * @des 初始化holderView, 决定所能提供的视图长什么样子
     * @called HomeHolder一旦创建的时候
     */
    @Override
    public View initHolderView() {
        View itemView = View.inflate(UIUtils.getContext(), R.layout.item_home, null);
        ButterKnife.bind(this,itemView);
        return itemView;
    }

    /**
     * @param data
     * @des 数据和视图的绑定操作
     */
    @Override
    public void refreshHolderView(ItemBean data) {
        //view-->成员变量
        //data-->局部变量,基类还有
        //data+view
        mItemAppinfoTvTitle.setText(data.name);
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(data.size));
        mItemAppinfoTvDes.setText(data.des);
        //ratingbar
        mItemAppinfoRbStars.setRating(data.stars);
    }
}
