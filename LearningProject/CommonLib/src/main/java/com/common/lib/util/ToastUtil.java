package com.common.lib.util;

import android.widget.Toast;

import com.common.lib.base.CommonBase;


/**
 * Created by Administrator on 2017/2/22.
 */

public class ToastUtil {
    private static Toast sToast = null;

    public static void showMsg(String msg, int during){
        if(sToast != null){
            sToast.setText(msg);
            sToast.setDuration(during);
            sToast.show();
        }else{
            sToast = Toast.makeText(CommonBase.mContext,msg,during);
            sToast.show();
        }
    }
}
