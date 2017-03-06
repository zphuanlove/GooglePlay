package com.itheima_zphuan.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.base.LoadingPager;
import com.itheima_zphuan.googleplay.base.SuperBaseAdapter;
import com.itheima_zphuan.googleplay.bean.SubjectBean;
import com.itheima_zphuan.googleplay.factory.ListViewFactory;
import com.itheima_zphuan.googleplay.holder.SubjectHolder;
import com.itheima_zphuan.googleplay.protocal.SubjectProtocal;
import com.itheima_zphuan.googleplay.utils.UIUtils;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class SubjectFragment extends BaseFragment {

    private SubjectProtocal mSubjectProtocal;
    private List<SubjectBean> mDatas;

    @Override
    public LoadingPager.LoadedResult initData() {
        mSubjectProtocal = new SubjectProtocal();
        try {
            mDatas = mSubjectProtocal.loadData(0);
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
        listView.setAdapter(new SubjectAdapter(mDatas, listView));
        return listView;
    }


    private class SubjectAdapter extends SuperBaseAdapter<SubjectBean> {
        protected SubjectAdapter(List<SubjectBean> dataSets, AbsListView mAbsListView) {
            super(dataSets, mAbsListView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            return new SubjectHolder();
        }

        @Override
        public boolean hasLoadMore() {
            return true;
        }

        @Override
        public List onLoadMore() throws Exception {
            SystemClock.sleep(2000);
            List<SubjectBean> subjectBeen = mSubjectProtocal.loadData(mDatas.size());
            return subjectBeen;
        }

        @Override
        public void onNormalitemClick(AdapterView<?> parent, View view, int position, long id) {
            //itemBean
            SubjectBean subjectBean = mDatas.get(position);
            Toast.makeText(UIUtils.getContext(), subjectBean.des, Toast.LENGTH_SHORT).show();
        }
    }
}
