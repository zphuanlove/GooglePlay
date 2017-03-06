package com.itheima_zphuan.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.base.LoadingPager;
import com.itheima_zphuan.googleplay.base.SuperBaseAdapter;
import com.itheima_zphuan.googleplay.bean.CategoryInfoBean;
import com.itheima_zphuan.googleplay.factory.ListViewFactory;
import com.itheima_zphuan.googleplay.holder.CategoryNormalHolder;
import com.itheima_zphuan.googleplay.holder.CategoryTitleHolder;
import com.itheima_zphuan.googleplay.protocal.CategoryProtocol;
import com.itheima_zphuan.googleplay.utils.LogUtils;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class CategoryFragment extends BaseFragment {

    private List<CategoryInfoBean> mDatas;

    @Override
    public LoadingPager.LoadedResult initData() {
        CategoryProtocol protocol = new CategoryProtocol();
        try {
            mDatas = protocol.loadData(0);
            LogUtils.printList(mDatas);
            return checkResult(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }

    }

    @Override
    public View initSuccessView() {
        //view
        ListView listView = ListViewFactory.createListView();
        //data-->成员变量
        //data+view
        listView.setAdapter(new CategoryAdapter(mDatas, listView));
        return listView;
    }

    class CategoryAdapter extends SuperBaseAdapter<CategoryInfoBean> {

        public CategoryAdapter(List<CategoryInfoBean> dataSets, AbsListView absListView) {
            super(dataSets, absListView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            CategoryInfoBean itemBean = mDatas.get(position);
            if (itemBean.isTitle) {
                return new CategoryTitleHolder();
            } else {
                return new CategoryNormalHolder();
            }
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }

        @Override
        public int getNormalItemViewType(int position) {
            CategoryInfoBean itemBean = mDatas.get(position);
            if (itemBean.isTitle) {
                return 1;
            } else {
                return 2;
            }

        }
    }

}
