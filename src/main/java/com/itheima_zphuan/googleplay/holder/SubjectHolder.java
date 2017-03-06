package com.itheima_zphuan.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima_zphuan.googleplay.R;
import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.bean.SubjectBean;
import com.itheima_zphuan.googleplay.conf.Constants;
import com.itheima_zphuan.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: 钟佩桓
 * date: 2017/3/6
 */
public class SubjectHolder extends BaseHolder<SubjectBean> {
    @BindView(R.id.item_subject_iv_icon)
    ImageView mItemSubjectIvIcon;
    @BindView(R.id.item_subject_tv_title)
    TextView mItemSubjectTvTitle;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        ButterKnife.bind(this, holderView);
        return holderView;

    }

    @Override
    public void refreshHolderView(SubjectBean data) {
        mItemSubjectTvTitle.setText(data.des);
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + data.url).into(mItemSubjectIvIcon);
    }
}
