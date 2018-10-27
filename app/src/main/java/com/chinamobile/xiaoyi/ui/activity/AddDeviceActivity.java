package com.chinamobile.xiaoyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.http.app.ActionCallback;
import com.chinamobile.xiaoyi.http.entity.IndeInfo;
import com.chinamobile.xiaoyi.http.http.RequestParams;
import com.chinamobile.xiaoyi.ui.base.AppBaseActivity;
import com.chinamobile.xiaoyi.util.CommonUtil;
import com.chinamobile.xiaoyi.util.ToastManager;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by malijie on 2017/9/12.
 */

public class AddDeviceActivity extends AppBaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 200;
    private TextView mTextTitle;
    private ImageView mButtonBack = null;
    private TextView mTextNext;
    private LinearLayout mLayoutTitle = null;
    private EditText mEditTextSN = null;
    private Bundle bundle;
    private String idenCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();

    }

    @Override
    protected int getContentViewId() {
        return R.layout.add_device;
    }


    @Override
    public void initViews() {
        mTextTitle = findViewById(R.id.id_title_bar_text_title);
        mButtonBack = findViewById(R.id.id_title_bar_button_back);
        mLayoutTitle = findViewById(R.id.id_title_bar_layout_device);
        mTextNext = findViewById(R.id.id_title_bar_text_option);
        mEditTextSN = findViewById(R.id.id_add_device_text_input);
        mTextNext.setVisibility(View.VISIBLE);

        mLayoutTitle.setVisibility(View.GONE);
        mButtonBack.setOnClickListener(this);
        findViewById(R.id.iv_bar_code).setOnClickListener(this);
        findViewById(R.id.tv_rebind).setOnClickListener(this);
        mTextTitle.setText("设备添加");

        mTextNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep();
            }
        });
    }

    private void nextStep() {
        String imei = mEditTextSN.getText().toString().trim();
        if (TextUtils.isEmpty(imei)) {
            ToastManager.showShortMsg("请输入设备号");
            return;
        }else if (imei.length()!=15){
            ToastManager.showShortMsg("请输入正确的设备号");
            return;
        }
        verificationSnRequest(imei);
    }

    private void verificationSnRequest(final String imei) {
        mProgressDialog = CommonUtil.createProgressDialog(this,"正在验证设备号...");
        mProgressDialog.show();
        RequestParams params = new RequestParams();
        params.setParamsValue("imei", imei);
        action.verificationImei(params, new ActionCallback<IndeInfo>() {
            @Override
            public void onSuccess(IndeInfo data) {
                mProgressDialog.dismiss();
                onVerificationImeiSuccess(imei,data);
            }

            @Override
            public void onFailed(String errorMsg) {
                mProgressDialog.dismiss();
                ToastManager.showShortMsg(errorMsg);
            }
        });

    }

    private void onVerificationImeiSuccess(String imei, IndeInfo data) {
        if (bundle==null){
            bundle = new Bundle();
        }
        bundle.putString("imei",imei);
        bundle.putString("title_text","设备添加");
        bundle.putInt("active",data.getActiveStatus());


        Intent intent = new Intent();
        if (TextUtils.isEmpty(data.getIdenCode())) {
            intent.setClass(this,AddIdentifyActivity.class);
        }else {
           intent.setClass(this,AddDeviceDetailActivity.class);
//            ToastManager.showShortMsg("设备被绑定过，请进行重新绑定");
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void initData() {
        bundle = getIntent().getExtras();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_title_bar_button_back:
                finish(this);
                break;
            case R.id.iv_bar_code:
                Intent intent = new Intent(this, BarCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.tv_rebind:
                Intent intentRebind = new Intent(this, RebindActivity.class);
                startActivity(intentRebind);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    mEditTextSN.setText(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
