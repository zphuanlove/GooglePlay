package com.itheima_zphuan.googleplay.protocal;

import com.google.gson.Gson;
import com.itheima_zphuan.googleplay.base.BaseProtocal;
import com.itheima_zphuan.googleplay.bean.HomeBean;

/**
 * author: 钟佩桓
 * date: 2017/3/3
 * des:负责对HomeFragment里面涉及到的网络请求进行封装
 */
public class HomeProtocol extends BaseProtocal<HomeBean>{

    /**
     * 决定协议的关键字
     */
    @Override
    public String getInterfaceKey() {
        return "home";
    }

    /**
     * 完成网络请求回来jsonString的解析
     * @param resJsonString
     * @return
     */
    @Override
    protected HomeBean parseJson(String resJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resJsonString,HomeBean.class);
    }
}
