package com.itheima_zphuan.googleplay.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima_zphuan.googleplay.R;
import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.bean.CategoryInfoBean;
import com.itheima_zphuan.googleplay.conf.Constants;
import com.itheima_zphuan.googleplay.utils.UIUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: 钟佩桓
 * date: 2017/3/6
 */
public class CategoryNormalHolder extends BaseHolder<CategoryInfoBean> {
    @BindView(R.id.item_category_icon_1)
    ImageView mItemCategoryIcon1;
    @BindView(R.id.item_category_name_1)
    TextView mItemCategoryName1;
    @BindView(R.id.item_category_item_1)
    LinearLayout mItemCategoryItem1;
    @BindView(R.id.item_category_icon_2)
    ImageView mItemCategoryIcon2;
    @BindView(R.id.item_category_name_2)
    TextView mItemCategoryName2;
    @BindView(R.id.item_category_item_2)
    LinearLayout mItemCategoryItem2;
    @BindView(R.id.item_category_icon_3)
    ImageView mItemCategoryIcon3;
    @BindView(R.id.item_category_name_3)
    TextView mItemCategoryName3;
    @BindView(R.id.item_category_item_3)
    LinearLayout mItemCategoryItem3;

    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_category_normal, null);
        ButterKnife.bind(this, holderView);
        return holderView;

    }

    @Override
    public void refreshHolderView(CategoryInfoBean data) {
        refreshUI(data.name1, data.url1, mItemCategoryName1, mItemCategoryIcon1);
        refreshUI(data.name2, data.url2, mItemCategoryName2, mItemCategoryIcon2);
        refreshUI(data.name3, data.url3, mItemCategoryName3, mItemCategoryIcon3);
    }

    public void refreshUI(final String name, String url, TextView tvName, ImageView ivIcon) {
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(url)) {
            ViewParent parent = tvName.getParent();
            ((ViewGroup) parent).setVisibility(View.INVISIBLE);
        } else {
            tvName.setText(name);
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + url).into(ivIcon);
            ViewParent parent = tvName.getParent();
            ((ViewGroup) parent).setVisibility(View.VISIBLE);
        }
    }

}
