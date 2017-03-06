package com.itheima_zphuan.googleplay.protocal;

import com.itheima_zphuan.googleplay.base.BaseProtocal;
import com.itheima_zphuan.googleplay.bean.CategoryInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * author: 钟佩桓
 * date: 2017/3/6
 */
public class CategoryProtocol extends BaseProtocal<List<CategoryInfoBean>> {
    @Override
    public String getInterfaceKey() {
        return "category";
    }

    @Override
    protected List<CategoryInfoBean> parseJson(String resJsonString) {
         /*--------------- 只能用结点解析(jsonString的结构和Bean的结构不统一) ---------------*/
        List<CategoryInfoBean> categoryInfoBeans = new ArrayList<>();

        try {
            JSONArray rootJsonArray = new JSONArray(resJsonString);
            //遍历rootJsonArray
            for (int i = 0; i < rootJsonArray.length(); i++) {
                JSONObject itemJsonObject = rootJsonArray.getJSONObject(i);
                //取出title
                String title = itemJsonObject.getString("title");
                CategoryInfoBean titleCategoryInfoBean = new CategoryInfoBean();
                titleCategoryInfoBean.title = title;
                titleCategoryInfoBean.isTitle = true;
                //加入集合中
                categoryInfoBeans.add(titleCategoryInfoBean);

                JSONArray infosJsonArray = itemJsonObject.getJSONArray("infos");
                //遍历infosJsonArray
                for (int j = 0; j < infosJsonArray.length(); j++) {
                    JSONObject infoJsonObject = infosJsonArray.getJSONObject(j);
                    String name1 = infoJsonObject.getString("name1");
                    String name2 = infoJsonObject.getString("name2");
                    String name3 = infoJsonObject.getString("name3");
                    String url1 = infoJsonObject.getString("url1");
                    String url2 = infoJsonObject.getString("url2");
                    String url3 = infoJsonObject.getString("url3");

                    CategoryInfoBean categoryInfoBean = new CategoryInfoBean();
                    categoryInfoBean.name1 = name1;
                    categoryInfoBean.name2 = name2;
                    categoryInfoBean.name3 = name3;
                    categoryInfoBean.url1 = url1;
                    categoryInfoBean.url2 = url2;
                    categoryInfoBean.url3 = url3;

                    //加入集合中
                    categoryInfoBeans.add(categoryInfoBean);
                }
            }

            return categoryInfoBeans;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
