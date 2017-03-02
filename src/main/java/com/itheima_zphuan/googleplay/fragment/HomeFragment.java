package com.itheima_zphuan.googleplay.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.base.LoadingPager;
import com.itheima_zphuan.googleplay.base.SuperBaseAdapter;
import com.itheima_zphuan.googleplay.bean.HomeBean;
import com.itheima_zphuan.googleplay.bean.ItemBean;
import com.itheima_zphuan.googleplay.conf.Constants;
import com.itheima_zphuan.googleplay.holder.HomeHolder;
import com.itheima_zphuan.googleplay.utils.HttpUtils;
import com.itheima_zphuan.googleplay.utils.UIUtils;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: 钟佩桓
 * date: 2017/2/27
 */
public class HomeFragment extends BaseFragment {

    private List<ItemBean> mItemBeans;
    private List<String> mPictures;

    /**
     * @des 在子线程中真正的加载具体的数据
     * @called triggerLoadData()方法被调用的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        try {
            //1.创建OkHttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient();
            // 2.拼接要访问的URL
            // 拼接URL：http://localhost:8080/GooglePlayServer/home
            String url = Constants.URLS.BASEURL+"home";
            //定义参数对应的map
            Map<String,Object> params = new HashMap<>();
            params.put("index",0);
            //拼接参数信息 ？ index = 0
            String urlParamsByMap = HttpUtils.getUrlParamsByMap(params);
            url = url +"?" +urlParamsByMap;

            //3.创建请求对象
            Request request = new Request.Builder().get().url(url).build();
            //发起请求-->同步请求
            Response response = okHttpClient.newCall(request).execute();
            //4.解析响应的结果
            if(response.isSuccessful()){
                String resJsonString = response.body().string();
                KLog.json("resJsonString-->"+resJsonString);
                /************Json解析*************/
                Gson gson = new Gson();
                HomeBean homeBean = gson.fromJson(resJsonString, HomeBean.class);
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
            }else{//响应没有成功
                return LoadingPager.LoadedResult.ERROR;
            }
        } catch (IOException e) {
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
    }

}
