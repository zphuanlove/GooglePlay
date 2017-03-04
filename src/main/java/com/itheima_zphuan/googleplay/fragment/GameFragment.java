package com.itheima_zphuan.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.base.LoadingPager;
import com.itheima_zphuan.googleplay.base.SuperBaseAdapter;
import com.itheima_zphuan.googleplay.bean.ItemBean;
import com.itheima_zphuan.googleplay.factory.ListViewFactory;
import com.itheima_zphuan.googleplay.holder.ItemHolder;
import com.itheima_zphuan.googleplay.protocal.GameProtocal;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class GameFragment extends BaseFragment {

    private List<ItemBean> mDatas;
    private GameProtocal mGameProtocal;
    @Override
    public LoadingPager.LoadedResult initData() {
        mGameProtocal = new GameProtocal();
        try {
            mDatas = mGameProtocal.loadData(0);
            //校验网络请求回来的数据,返回对应的状态
            LoadingPager.LoadedResult loadedResult = checkResult(mDatas);
            return loadedResult;
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new GameAdapter(mDatas,listView));
        return listView;
    }

    private class GameAdapter extends SuperBaseAdapter<ItemBean> {

        protected GameAdapter(List<ItemBean> dataSets, AbsListView mAbsListView) {
            super(dataSets, mAbsListView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder() {
            return new ItemHolder();
        }

        @Override
        public boolean hasLoadMore() {
            return true;
        }

        @Override
        public List onLoadMore() throws Exception {
            SystemClock.sleep(2000);
            List<ItemBean> itemBeans = mGameProtocal.loadData(mDatas.size());
            return itemBeans;
        }
    }
}
