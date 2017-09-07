package com.chinamobile.xiaoyi;

import android.app.Application;
import android.content.Context;

import com.base.lib.control.CommonLibManager;
import com.chinamobile.xiaoyi.map.BaiduMapManager;

/**
 * Created by malijie on 2017/9/7.
 */

public class XiaoYiHelpApplication  extends Application {
    public static Context mContext = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CommonLibManager.init(getApplicationContext());
        BaiduMapManager.init();
    }
}