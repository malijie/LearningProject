package com.common.lib.util;

import android.app.Activity;
import android.content.Intent;

import com.common.lib.base.CommonBase;


/**
 * Created by Administrator on 2016/12/1.
 */
public class IntentManager {

    public static void startActivity(Class clazz){
        Intent i = new Intent(CommonBase.mContext,clazz);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CommonBase.mContext.startActivity(i);
    }


    public static void startActivity(Intent i,Class clazz){
        i.setClass(CommonBase.mContext,clazz);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CommonBase.mContext.startActivity(i);
    }

    public static void finishActivity(Activity activity){
        if(activity != null){
            activity.finish();
        }
    }

}
