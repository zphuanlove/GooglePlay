package com.itheima_zphuan.googleplay.base;

import com.itheima_zphuan.googleplay.conf.Constants;
import com.itheima_zphuan.googleplay.utils.FileUtils;
import com.itheima_zphuan.googleplay.utils.HttpUtils;
import com.itheima_zphuan.googleplay.utils.IOUtils;
import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public T loadData(int index) throws Exception {
        T result = loadDataFromLocal(index);
        if(result!=null){
            //本地有数据
            KLog.d("从本地加载了数据-->" + getCacheFile(index).getAbsolutePath());
            return result;
        }
        return loadDataFromNet(index);
    }

    /**
     * 从网络加载数据，并存入本地
     *
     * @return
     * @throws IOException
     */
    private T loadDataFromNet(int index) throws IOException {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2.拼接要访问的URL
        // 拼接URL：http://localhost:8080/GooglePlayServer/home
        // ?index=0
        String url = Constants.URLS.BASEURL + getInterfaceKey();
        //定义参数对应的map
        Map<String, Object> params = new HashMap<>();
        params.put("index", index);//暂时不考虑分页
        //拼接参数信息 ？index = 0
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(params);
        url = url + "?" + urlParamsByMap;
        //3.创建请求对象
        Request request = new Request.Builder().get().url(url).build();
        //发起请求-->同步请求
        Response response = okHttpClient.newCall(request).execute();
        //4.解析响应的结果
        if (response.isSuccessful()) {
            String resJsonString = response.body().string();
            KLog.json("resJsonString-->" + resJsonString);
            /*--------------保存数据到本地--------------*/
            KLog.i("保存数据到本地-->" + getCacheFile(index));
            BufferedWriter bufferedWriter = null;
            try {
                File cacheFile = getCacheFile(index);
                FileWriter fileWriter = new FileWriter(cacheFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                //写第一行
                bufferedWriter.write(System.currentTimeMillis() + "");
                //换行
                bufferedWriter.newLine();
                //写第二行
                bufferedWriter.write(resJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(bufferedWriter);
            }
           /*--------------完成json解析--------------*/
            return parseJson(resJsonString);
        } else {
            return null;
        }
    }

    /**
     * 从本地加载数据
     * @param index
     * @return
     */
    private T loadDataFromLocal(int index) {
        BufferedReader bufferedReader = null;
        try {
            //找到缓存文件
            File cacheFile = getCacheFile(index);
            bufferedReader = new BufferedReader(new FileReader(cacheFile));
            //读取缓存的生成时间
            String firstLine = bufferedReader.readLine();
            Long cacheInsertTime = Long.parseLong(firstLine);
            //判断是否过期
            if ((System.currentTimeMillis() - cacheInsertTime) < Constants.PROTOCOLTIMEOUT) {
                //有效的缓存
                String diskCacheJsonString = bufferedReader.readLine();
                //解析返回
                return parseJson(diskCacheJsonString);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bufferedReader);
        }
        return null;
    }


    /**
     * 得到缓存文件
     */
    private File getCacheFile(int index) {
        //优先保存到外置sdcard,应用程序的缓存目录(sdcard/Android/data/包目录/json)
        String dir = FileUtils.getDir("json");
        KLog.e("dir:" + dir);
        //唯一命中的问题 interfaceKey+"."+index
        String fileName = getInterfaceKey() + "." + index;
        return new File(dir, fileName);
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
