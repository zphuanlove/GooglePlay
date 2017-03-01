package com.itheima_zphuan.googleplay.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.base.LoadingPager;
import com.itheima_zphuan.googleplay.utils.UIUtils;

import java.util.Random;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class AppFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        SystemClock.sleep(2000);//模拟耗时的网络请求

        Random random = new Random();
        int index = random.nextInt(3);// 0  1 2

        LoadingPager.LoadedResult[] loadedResults = {LoadingPager.LoadedResult.SUCCESS, LoadingPager.LoadedResult.EMPTY, LoadingPager.LoadedResult.ERROR};
        //随机返回一种情况
        return loadedResults[index];//数据加载完成之后的状态(成功,失败,空)return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public void onStart() {
        super.onStart();
        //触发加载数据
    }

    @Override
    public View initSuccessView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setText(this.getClass().getSimpleName());//"HomeFragment"
        tv.setTextColor(Color.RED);
        return tv;
    }
}
