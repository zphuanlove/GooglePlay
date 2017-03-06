package com.itheima_zphuan.googleplay.protocal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima_zphuan.googleplay.base.BaseProtocal;
import com.itheima_zphuan.googleplay.bean.SubjectBean;

import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/3/6
 */
public class SubjectProtocal extends BaseProtocal<List<SubjectBean>> {
    @Override
    public String getInterfaceKey() {
        return "subject";
    }

    @Override
    protected List<SubjectBean> parseJson(String resJsonString) {
        Gson gson = new Gson();
        List<SubjectBean> subjects = gson.fromJson(resJsonString, new TypeToken<List<SubjectBean>>() {
        }.getType());
        return subjects;
    }
}
