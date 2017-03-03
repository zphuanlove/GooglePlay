package com.itheima_zphuan.googleplay.base;

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
 * des:针对具体的protocol封装一个基类
 */
public abstract class BaseProtocal<T> {

    public T loadData() throws Exception{
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2.拼接要访问的URL
        // 拼接URL：http://localhost:8080/GooglePlayServer/home
        // ?index=0
        String url = Constants.URLS.BASEURL + getInterfaceKey();
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
           /*--------------完成json解析--------------*/
            return parseJson(resJsonString);
        }else{
            return null;
        }
    }



    /**
     * @return
     * @des得到协议的关键字
     * @des 在BaseProtocl中, 不知道协议关键字具体是啥, 交给子类
     * @des 子类是必须实现, 所以定义成为抽象方法, 交给子类具体实现
     */
    public abstract String getInterfaceKey();

    /**
     * @param resJsonString
     * @return
     * @des 负责解析网络请求回来的jsonString
     * @des 一共有3种解析情况(结点解析, Bean解析, 泛型解析)
     */
    protected abstract T parseJson(String resJsonString);
}
