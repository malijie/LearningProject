package com.pay.lib.wap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.waps.AppConnect;

/**
 * Created by malijie on 2017/8/30.
 */

public class WapManager {
    private final static Object sObject = new Object();
    private static  WapManager sWapManager = null;
    private Context mContext = null;
    private AppConnect mAppConnect = null;

    private WapManager(Context context){
        mContext = context;
        mAppConnect = AppConnect.getInstance(context);
    }

    public static WapManager getInstance(Context context){
        if(sWapManager == null){
            synchronized (sObject){
                if(sWapManager == null){
                    sWapManager = new WapManager(context);
                }
            }
        }
        return sWapManager;
    }

    public void feedback(Activity activity){
        mAppConnect.showFeedback(activity);
    }

    public void about(Activity activity,Class clazz){
        Intent i = new Intent(activity,clazz);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

    public void update(Activity activity){
        mAppConnect.checkUpdate(activity);
    }

    public void close(){
        mAppConnect.close();
    }

    public AppConnect getAppConnect(){
        return mAppConnect;
    }


}
