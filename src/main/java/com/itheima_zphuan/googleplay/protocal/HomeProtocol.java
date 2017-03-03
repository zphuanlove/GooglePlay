package com.itheima_zphuan.googleplay.protocal;

import com.google.gson.Gson;
import com.itheima_zphuan.googleplay.bean.HomeBean;
import com.itheima_zphuan.googleplay.conf.Constants;
import com.itheima_zphuan.googleplay.utils.HttpUtils;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: 钟佩桓
 * date: 2017/3/3
 * des:负责对HomeFragment里面涉及到的网络请求进行封装
 */
public class HomeProtocol {

    public HomeBean loadData() throws Exception{
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
        if(response.isSuccessful()) {
            String resJsonString = response.body().string();
            KLog.json("resJsonString-->" + resJsonString);
            /************Json解析*************/
            Gson gson = new Gson();
            HomeBean homeBean = gson.fromJson(resJsonString, HomeBean.class);
            return homeBean;
        }else{
            return null;
        }
    }
}
