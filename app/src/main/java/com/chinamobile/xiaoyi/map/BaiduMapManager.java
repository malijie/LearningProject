package com.chinamobile.xiaoyi.map;

import com.baidu.mapapi.SDKInitializer;
import com.chinamobile.xiaoyi.XiaoYiHelpApplication;

/**
 * Created by malijie on 2017/9/7.
 */

public class BaiduMapManager {

    public static void init(){
        SDKInitializer.initialize(XiaoYiHelpApplication.mContext);

    }

}
