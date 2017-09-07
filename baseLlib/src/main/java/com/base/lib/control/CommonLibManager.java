package com.base.lib.control;

import android.content.Context;

import com.base.lib.profile.Profile;

/**
 * Created by malijie on 2017/8/2.
 */

public class CommonLibManager {
    public static void init(Context context){
        Profile.init(context);
    }
}
