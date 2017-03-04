package com.itheima_zphuan.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.base.LoadingPager;
import com.itheima_zphuan.googleplay.base.SuperBaseAdapter;
import com.itheima_zphuan.googleplay.bean.HomeBean;
import com.itheima_zphuan.googleplay.bean.ItemBean;
import com.itheima_zphuan.googleplay.holder.HomeHolder;
import com.itheima_zphuan.googleplay.protocal.HomeProtocol;
import com.itheima_zphuan.googleplay.utils.UIUtils;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class HomeFragment extends BaseFragment {

    private List<ItemBean> mItemBeans;
    private List<String> mPictures;
    private HomeProtocol mProtocol;

    /**
     * @des 在子线程中真正的加载具体的数据
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        /*--------------协议进行简单封装以后--------------*/
        mProtocol = new HomeProtocol();
        try {
            HomeBean homeBean = mProtocol.loadData(0);
            LoadingPager.LoadedResult result = checkResult(homeBean);
            if (result != LoadingPager.LoadedResult.SUCCESS) {//说明homeBean有问题,homeBean==null
                return result;
            }
            result = checkResult(homeBean.list);
            if (result != LoadingPager.LoadedResult.SUCCESS) {//说明list有问题,list.size==0
                return result;
            }

            //走到这里来说明是成功的
            //保存数据到成员变量
            mItemBeans = homeBean.list;
            mPictures = homeBean.picture;
            //返回相应的状态
            return result;//successs的状态
        } catch (Exception e) {//响应没有成功
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    @Override
    public View initSuccessView() {
        //view
        ListView listView = new ListView(UIUtils.getContext());
        //data-->mDataSets-->成员变量位置
        //data+view
        listView.setAdapter(new HomeAdapter(mItemBeans));
        return listView;
    }

    /**
     * @return
     * @des 得到BaseHolder具体的子类对象
     */
    class HomeAdapter extends SuperBaseAdapter<ItemBean> {

        protected HomeAdapter(List<ItemBean> dataSets) {
            super(dataSets);
        }

        @Override
        public BaseHolder getSpecialBaseHolder() {
            return new HomeHolder();
        }

        @Override
        public boolean hasLoadMore() {
            return true;
        }

        @Override
        public List onLoadMore() throws Exception {
            SystemClock.sleep(2000);
            HomeBean homeBean = mProtocol.loadData(mDataSets.size());
            if(homeBean!=null){
                return homeBean.list;
            }
            return super.onLoadMore();
        }
    }

}
