package com.base.lib.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;

import com.base.lib.profile.Profile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by malijie on 2017/2/24.
 */

public class Util {
    public static boolean hasInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) Profile.sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()){
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    public static String getResString(int resId){
        return Profile.sContext.getResources().getString(resId);
    }

    public static View getView(int resId){
        return LayoutInflater.from(Profile.sContext).inflate(resId,null);
    }

    public static String getCurrentDate(){
        return  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    public static ProgressDialog createProgressDialog(Activity activity, String msg){
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(msg);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

//    public static int getColor(int resColor){
//        return Profile.sContext.getResources().getColor(resColor);
//    }

}
