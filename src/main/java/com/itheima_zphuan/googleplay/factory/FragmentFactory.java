package com.itheima_zphuan.googleplay.factory;

import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.fragment.AppFragment;
import com.itheima_zphuan.googleplay.fragment.CategoryFragment;
import com.itheima_zphuan.googleplay.fragment.GameFragment;
import com.itheima_zphuan.googleplay.fragment.HomeFragment;
import com.itheima_zphuan.googleplay.fragment.HotFragment;
import com.itheima_zphuan.googleplay.fragment.RecommendFragment;
import com.itheima_zphuan.googleplay.fragment.SubjectFragment;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class FragmentFactory {

    public static final int FRAGMENT_HOME = 0;//首页
    public static final int FRAGMENT_APP = 1;//应用
    public static final int FRAGMENT_GAME = 2;//游戏
    public static final int FRAGMENT_SUBJECT = 3;//专题
    public static final int FRAGMENT_RECOMMEND = 4;//推荐
    public static final int FRAGMENT_CATEGORY = 5;//分类
    public static final int FRAGMENT_HOT = 6;//排行

    /** 用于缓存Fragment的实例 */
    public static Map<Integer, BaseFragment> mCacheFragments = new HashMap<>();

    public static BaseFragment createFragment(int position) {
        //定义Fragment对象
        BaseFragment fragment = null;

        //优先缓存集合中取出来
        if (mCacheFragments.containsKey(position)) {
            fragment = mCacheFragments.get(position);
            KLog.i("有缓存对象fragment，不再创建fragment--"+position);
            return fragment;
        }


        switch (position) {
            case FRAGMENT_HOME://返回 首页 对应的fragment
                fragment = new HomeFragment();
                break;
            case FRAGMENT_APP://返回 应用 对应的fragment
                fragment = new AppFragment();
                break;
            case FRAGMENT_GAME://返回 游戏 对应的fragment
                fragment = new GameFragment();
                break;
            case FRAGMENT_SUBJECT://返回 专题 对应的fragment
                fragment = new SubjectFragment();
                break;
            case FRAGMENT_RECOMMEND://返回 推荐 对应的fragment
                fragment = new RecommendFragment();
                break;
            case FRAGMENT_CATEGORY://返回 分类 对应的fragment
                fragment = new CategoryFragment();
                break;
            case FRAGMENT_HOT://返回 排行 对应的fragment
                fragment = new HotFragment();
                break;

            default:
                break;
        }
        mCacheFragments.put(position,fragment);
        return fragment;
    }
}
