package com.chinamobile.xiaoyi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.pay.AliPayment;
import com.chinamobile.xiaoyi.pay.IPayCallback;
import com.chinamobile.xiaoyi.pay.PayManager;
import com.chinamobile.xiaoyi.ui.base.AppBaseActivity;
import com.chinamobile.xiaoyi.ui.widget.DateDialog;
import com.chinamobile.xiaoyi.util.ToastManager;


public class TestActivity extends AppBaseActivity implements OnGetGeoCoderResultListener {
    private static final String TAG = TestActivity.class.getSimpleName();

    private Button mButtonGet = null;
    private Button mButtonPost = null;
    private Button mButtonDate = null;
    private TextView mTextResult = null;
    private Button mButtonPay = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonGet = (Button) findViewById(R.id.id_get);
        mButtonPost = (Button) findViewById(R.id.id_post);
        mButtonDate = findViewById(R.id.show_date);
        mButtonPay = findViewById(R.id.id_pay);
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DateDialog dialog = new DateDialog(TestActivity.this);
                dialog.setButtonClickListener(new DateDialog.DialogButtonListener() {
                    @Override
                    public void onConfirm(int year, int month, int day) {
                        ToastManager.showShortMsg("year=" + year + ",month=" + month + ",day=" + day);
                    }

                    @Override
                    public void onCancel() {
                        dialog.dissmiss();
                    }
                });

                dialog.show();
            }
        });

        mButtonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }



    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

//    private View.OnClickListener onClickGet = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            mProgressDialog = CommonUtil.createProgressDialog(TestActivity.this, getResources().getString(R.string.loading_progress_tip));
//            mProgressDialog.show();
//
//            RequestParams params = new RequestParams();
//            params.setParamsValue("groupId", "10");
//            params.setParamsValue("itemId", "1");
//
//            action.testGetRequestInfo(params, new RequestCallback<List<WelfareInfo>>() {
//                @Override
//                public void onSuccess(List<WelfareInfo> data) {
//                    mProgressDialog.dismiss();
//                    mTextResult.setText("data=" + data);
//
//                }
//
//            });
//        }
//    };
//
//    private View.OnClickListener onClickPost = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            mProgressDialog = CommonUtil.createProgressDialog(TestActivity.this, getResources().getString(R.string.loading_progress_tip));
//            mProgressDialog.show();
//
//            RequestParams params = new RequestParams();
//            params.setParamsValue("url", "http://blog.csdn.net/dog250/article/details/77993563");
//            params.setParamsValue("desc", "testasdasdsadasd");
//            params.setParamsValue("who", "1234");
//            params.setParamsValue("type", "Android");
//            params.setParamsValue("debug", "true");
//
//            action.testPostRequestInfo(params, new RequestCallback<String>() {
//                @Override
//                public void onSuccess(String msg) {
//                    mProgressDialog.dismiss();
//                    mTextResult.setText("data=" + msg);
//                }
//            });
//
//        }
//    };

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mThreadPool.removeAllTask();
//        mThreadPool.waitTermination();
//    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }
}
