package com.base.lib.utils;

import android.widget.Toast;


/**
 * Created by Administrator on 2017/2/22.
 */

public class ToastManager {
    public static void showLongMsg(String msg){
        ToastUtil.showMsg(msg, Toast.LENGTH_LONG);
    }

    public static void showShortMsg(String msg){
        ToastUtil.showMsg(msg, Toast.LENGTH_SHORT);
    }

    public static void showNoNetwork(){
        ToastUtil.showMsg("当前无网络...");
    }
}
