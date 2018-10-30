package com.politics.exam.wap;


import android.app.Activity;

import com.wanpu.pay.PayConnect;


/**
 * Created by malijie on 2017/3/27.
 */

public class PayBaseAction {
    protected PayConnect mPayConnect = null;
    public static final String GOODS_NAME_VIDEO = "2019考研政治名师强化课";
    public static final String GOODS_DESCR_VIDEO = "只需79元，购买最新名师强化课";

    public static final String GOODS_NAME_VIP = "2019考研政治题库";
    public static final String GOODS_DESCR_VIP = "只需9.9元，购买考研政治题库";
    public static final float PRICE_VIP = 0.09f;
    public static final float PRICE_VIDEO = 0.07f;

    public PayBaseAction(Activity activity){
        mPayConnect = PayConnect.getInstance(activity);
    }
}
