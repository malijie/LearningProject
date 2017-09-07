package com.base.lib.http;

import com.base.lib.utils.Logger;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by malijie on 2017/8/1.
 */

public class HttpEngine {
    private final static String TAG = "HttpEngine";

    private final static String SERVER_URL = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/";
    private final static String REQUEST_METHOD_POST = "POST";
    private final static String REQUEST_METHOD_GET = "GET";
    private final static String ENCODE_TYPE = "UTF-8";
    private final static int TIME_OUT = 20000;
    private static HttpEngine sHttpEngine = null;

    public static HttpEngine getInstance(){
           if(sHttpEngine == null){
               synchronized (HttpEngine.class){
                   if(sHttpEngine == null){
                        sHttpEngine = new HttpEngine();
                   }
               }
           }
       return sHttpEngine;
    }


    public <T> T postHandle(RequestParams params, Type typeOfT)  throws IOException {
        HttpURLConnection connection = getConnection(params);
        int code = connection.getResponseCode();
        if (code == 200) {
            // 获取响应的输入流对象
            InputStream is = connection.getInputStream();
            // 创建字节输出流对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 定义读取的长度
            int len = 0;
            // 定义缓冲区
            byte buffer[] = new byte[1024];
            // 按照缓冲区的大小，循环读取
            while ((len = is.read(buffer)) != -1) {
                // 根据读取的长度写入到os对象中
                baos.write(buffer, 0, len);
            }
            // 释放资源
            is.close();
            baos.close();
            connection.disconnect();
            // 返回字符串
            final String result = new String(baos.toByteArray());
            // 打印出结果
            Logger.d(TAG,"response: " + result);
            Gson gson = new Gson();
            return gson.fromJson(result, typeOfT);
        } else {
            connection.disconnect();
            return null;
        }
    }

    // 获取connection
    private HttpURLConnection getConnection(RequestParams params) {
        HttpURLConnection connection = null;
        String finalURL = SERVER_URL + params.getParamsValue("groupId") + "/" + params.getParamsValue("itemId");
//        String finalURL = SERVER_URL + joinParams(params);
        try {
            URL url = new URL(finalURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Response-Type", "json");
            connection.setChunkedStreamingMode(0);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private String joinParams(RequestParams requestParams){
        Map<String,String> paramsMap = requestParams.getParamsMap();
        String params = "?";
        List<String> keyList = new ArrayList<>(paramsMap.keySet());
        for(int i=0;i<keyList.size();i++){
            String key = keyList.get(i);
            params +=  key+ "=" +paramsMap.get(key) + "&";
        }
        return params.substring(0,params.length()-1);
    }

}
