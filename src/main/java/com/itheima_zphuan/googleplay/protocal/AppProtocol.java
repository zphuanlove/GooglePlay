package com.itheima_zphuan.googleplay.protocal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima_zphuan.googleplay.base.BaseProtocal;
import com.itheima_zphuan.googleplay.bean.ItemBean;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/3/4
 */
public class AppProtocol extends BaseProtocal<List<ItemBean>> {
    @Override
    public String getInterfaceKey() {
        return "app";
    }

    @Override
    protected List<ItemBean> parseJson(String resJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resJsonString,new TypeToken<List<ItemBean>>(){}.getType());

    }
}
