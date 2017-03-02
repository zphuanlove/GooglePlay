package com.itheima_zphuan.googleplay.fragment;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima_zphuan.googleplay.R;
import com.itheima_zphuan.googleplay.adapter.MyBaseAdapter;
import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.base.LoadingPager;
import com.itheima_zphuan.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class HomeFragment extends BaseFragment {

    private List<String> mDatas;
    /**
     * @des 在子线程中真正的加载具体的数据
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        SystemClock.sleep(2000);//模拟耗时的网络请求
        //模拟请求回来的数据
        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDatas.add("" + i);
        }
        return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        //view
        ListView listView = new ListView(UIUtils.getContext());
        //data-->mDataSets-->成员变量位置
        //data+view
        listView.setAdapter(new HomeAdapter(mDatas));
        return listView;
    }
    class HomeAdapter extends MyBaseAdapter<String>{

        protected HomeAdapter(List<String> dataSets) {
            super(dataSets);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
              /*--------------- 决定根布局(itemView) ---------------*/
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_temp, null);
                holder.mTvTmp1 = (TextView) convertView.findViewById(R.id.tmp_tv_1);
                holder.mTvTmp2 = (TextView) convertView.findViewById(R.id.tmp_tv_2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            /*--------------- 得到数据,然后绑定数据 ---------------*/
            //data
            String data = mDatas.get(position);
            //view-->在holder对象中
            //data+view
            holder.mTvTmp1.setText("我是头-" + data);
            holder.mTvTmp2.setText("我是尾巴-" + data);
            return convertView;
        }

        class ViewHolder {
            TextView mTvTmp1;
            TextView mTvTmp2;
        }

    }
}
