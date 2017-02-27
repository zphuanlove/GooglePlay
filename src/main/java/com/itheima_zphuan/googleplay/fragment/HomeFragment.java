package com.itheima_zphuan.googleplay.fragment;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.base.LoadingPager;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class HomeFragment extends BaseFragment {

    /**
     * @des 在子线程中真正的加载具体的数据
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        SystemClock.sleep(2000);//模拟耗时的网络请求
        return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        TextView successView = new TextView(getActivity());
        successView.setText("HomeFragment");
        successView.setTextColor(Color.RED);
        return successView;

    }
}
