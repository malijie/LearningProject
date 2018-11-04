package com.common.lib.base;

import android.content.Context;

/**
 * Created by malijie on 2018/11/2.
 */

public class CommonBase {
    public static Context mContext = null;

    public static void init(Context ctx){
        mContext = ctx;
    }
}
