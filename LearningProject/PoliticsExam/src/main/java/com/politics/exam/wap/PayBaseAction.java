package com.politics.exam.wap;


import android.app.Activity;

import com.wanpu.pay.PayConnect;


/**
 * Created by malijie on 2017/3/27.
 */

public class PayBaseAction {
    protected PayConnect mPayConnect = null;

    public static final String GOODS_NAME_VIP = "考研政治VIP会员";
    public static final String GOODS_DESCR_VIP = "只需9.9元，购买考研政治题库";
    public static final float PRICE_VIP = 9.9f;

    public PayBaseAction(Activity activity){
        mPayConnect = PayConnect.getInstance(activity);
    }
}
