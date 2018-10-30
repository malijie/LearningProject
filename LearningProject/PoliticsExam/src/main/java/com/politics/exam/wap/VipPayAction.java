package com.politics.exam.wap;

import android.app.Activity;
import android.content.Context;

import com.politics.exam.util.SharedPreferenceUtil;
import com.politics.exam.util.ToastManager;
import com.wanpu.pay.PayConnect;
import com.wanpu.pay.PayResultListener;

/**
 * Created by malijie on 2017/3/27.
 */

public class VipPayAction extends PayBaseAction{
    private Activity mActivity;
    public VipPayAction(Activity activity){
        super(activity);
        mActivity = activity;
    }

    public void pay() {
        String userId = mPayConnect.getDeviceId(mActivity);
        String orderId = String.valueOf(System.currentTimeMillis());
        mPayConnect.pay(mActivity, orderId, userId, PRICE_VIP,
                GOODS_NAME_VIP, GOODS_DESCR_VIP, "",new MyPayResultListener());
    }

    private class MyPayResultListener implements PayResultListener {

        @Override
        public void onPayFinish(Context payViewContext, String orderId,
                                int resultCode, String resultString, int payType, float amount,
                                String goodsName) {
            // 可根据resultCode自行判断
            if (resultCode == 0) {
                ToastManager.showShortMsg("购买成功");
                // 支付成功时关闭当前支付界面
                PayConnect.getInstance(mActivity).closePayView(payViewContext);

                // TODO 在客户端处理支付成功的操作

                // 未指定notifyUrl的情况下，交易成功后，必须发送回执
                PayConnect.getInstance(mActivity).confirm(orderId,payType);
                SharedPreferenceUtil.savePayedVIPStatus(true);
            } else {
                ToastManager.showShortMsg("购买失败");
            }
        }
    }
}
