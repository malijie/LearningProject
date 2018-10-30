package com.politics.exam.util;

import android.app.Activity;
import android.content.Intent;

import com.politics.exam.PoliticsApplication;

/**
 * Created by Administrator on 2016/12/1.
 */
public class IntentManager {

    public static void startActivity(Class clazz){
        Intent i = new Intent(PoliticsApplication.sContext,clazz);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PoliticsApplication.sContext.startActivity(i);
    }


    public static void startActivity(Intent i,Class clazz){
        i.setClass(PoliticsApplication.sContext,clazz);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PoliticsApplication.sContext.startActivity(i);
    }

    public static void finishActivity(Activity activity){
        if(activity != null){
            activity.finish();
        }
    }

}
