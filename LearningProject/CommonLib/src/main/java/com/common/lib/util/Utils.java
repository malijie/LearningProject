package com.common.lib.util;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import com.common.lib.base.CommonBase;


/**
 * Created by malijie on 2017/5/25.
 */

public class Utils {

    public static int getColor(int resId){
        return CommonBase.mContext.getResources().getColor(resId);
    }

    public static Drawable getDrawable(int resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return CommonBase.mContext.getDrawable(resId);
        }else{
            return CommonBase.mContext.getResources().getDrawable(resId);

        }
    }

    public static View getView(int resId){
        return LayoutInflater.from(CommonBase.mContext).inflate(resId,null);
    }

    public static String getString(int resId){
        return CommonBase.mContext.getResources().getString(resId);
    }

}
