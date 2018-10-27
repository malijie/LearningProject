package com.chinamobile.xiaoyi.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.http.app.ActionCallback;
import com.chinamobile.xiaoyi.http.http.RequestParams;
import com.chinamobile.xiaoyi.ui.base.AppBaseActivity;
import com.chinamobile.xiaoyi.util.CommonUtil;
import com.chinamobile.xiaoyi.util.JudgeUtil;
import com.chinamobile.xiaoyi.util.Logger;
import com.chinamobile.xiaoyi.util.MD5Util;
import com.chinamobile.xiaoyi.util.ToastManager;

/**
 * Created by malijie on 2017/9/13.
 */

public class ForgetPasswordActivity extends AppBaseActivity implements View.OnClickListener {

    private EditText editTextPhone;
    private EditText editTextCode;
    private EditText editTextPassword;
    private EditText editTextPasswordRepeat;
    private TextView textViewSendCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.forget_pwd_layout;
    }

    @Override
    public void initViews() {
        findViewById(R.id.id_title_bar_layout_device).setVisibility(View.INVISIBLE);
        TextView textViewTitle = (TextView) findViewById(R.id.id_title_bar_text_title);
        textViewTitle.setText("找回密码");
        textViewTitle.setVisibility(View.VISIBLE);
        TextView textViewOption = (TextView) findViewById(R.id.id_title_bar_text_option);
        textViewOption.setText("确定");
        textViewOption.setOnClickListener(this);
        textViewOption.setVisibility(View.VISIBLE);


        findViewById(R.id.id_title_bar_button_back).setOnClickListener(this);
//        findViewById(R.id.id_forget_pwd_button_forget_pwd).setOnClickListener(this);
//        findViewById(R.id.id_forget_pwd_button_forget_pwd).setOnClickListener(this);

        editTextPhone = (EditText) findViewById(R.id.et_phone);
        editTextCode = (EditText) findViewById(R.id.et_identify_code);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        editTextPasswordRepeat = (EditText) findViewById(R.id.et_password_repeat);

        textViewSendCode = (TextView) findViewById(R.id.tv_send_code);

        textViewSendCode.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_title_bar_button_back:
                finish();
                break;
            case R.id.id_title_bar_text_option:
                resetPassword();
                break;
            case R.id.tv_send_code:
                sendCode();
                break;

        }
    }

    private void resetPassword() {
        String phoneNumber = editTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)||!JudgeUtil.isPhoneNumber(phoneNumber)) {
            ToastManager.showShortMsg("请输入正确号码");
            return;
        }

        String code = editTextCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastManager.showShortMsg("请输入验证码");
            return;
        }

        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastManager.showShortMsg("请输入密码");
            return;
        }else if (!JudgeUtil.isPassword(password)){
            Toast.makeText(this, R.string.password_format, Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordRepeat = editTextPasswordRepeat.getText().toString().trim();
        if (TextUtils.isEmpty(passwordRepeat)||!TextUtils.equals(password,passwordRepeat)) {
            ToastManager.showShortMsg("两次密码不相同");
            return;
        }

        resetPasswordRequest(phoneNumber,code,password,passwordRepeat);

    }

    private void resetPasswordRequest(String phoneNumber, String code, String password, String passwordRepeat) {
        mProgressDialog = CommonUtil.createProgressDialog(this, "正在重设密码...");
        mProgressDialog.show();

        RequestParams params = new RequestParams();
        params.setParamsValue("phone", phoneNumber);
        params.setParamsValue("messageCode", code);
        params.setParamsValue("rePassword", MD5Util.getMD5(passwordRepeat));
        params.setParamsValue("password", MD5Util.getMD5(password));
        Logger.hyw(params.toString());
        action.forgetPasswordRequestInfo(params, new ActionCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mProgressDialog.dismiss();
                onResetPassWordSuccess(data);
            }

            @Override
            public void onFailed(String errorMsg) {
                mProgressDialog.dismiss();
                ToastManager.showShortMsg(errorMsg);
            }
        });
    }

    private void onResetPassWordSuccess(String data) {
        ToastManager.showShortMsg(data);
        this.finish();
    }

    private void sendCode() {
        String phoneNumber = editTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastManager.showShortMsg("请输入电话号码");
            return;
        }
        if (!JudgeUtil.isPhoneNumber(phoneNumber)) {
            ToastManager.showShortMsg("请输入正确号码");
            return;
        }

        messageCodeRequest(phoneNumber);
        setButtonText();
    }

    private void setButtonText() {
        textViewSendCode.setEnabled(false);
        textViewSendCode.setBackgroundResource(R.drawable.bg_dark_login_textview);
        textViewSendCode.setText("重新发送(60)");
        textViewSendCode.postDelayed(new Runnable() {
            int i = 60;
            @Override
            public void run() {
                textViewSendCode.setText("重新发送(" + --i + ")");
                if (i <= 0) {
                    textViewSendCode.setText(R.string.send_indentify_code);
                    textViewSendCode.setBackgroundResource(R.drawable.bg_login_textview);
                    textViewSendCode.setEnabled(true);
                } else {
                    textViewSendCode.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void messageCodeRequest(String phoneNumber) {
        RequestParams params = new RequestParams();
        params.setParamsValue("phone", phoneNumber);
        action.sendMessageCode(params, new ActionCallback<String>() {
            @Override
            public void onSuccess(String data) {
                ToastManager.showShortMsg("验证码发送成功");
            }

            @Override
            public void onFailed(String errorMsg) {
                ToastManager.showShortMsg(errorMsg);
            }
        });
    }
}
