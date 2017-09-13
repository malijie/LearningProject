package com.chinamobile.xiaoyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.base.lib.base.AppBaseActivity;
import com.chinamobile.xiaoyi.R;

/**
 * Created by malijie on 2017/9/12.
 */

public class LoginActivity extends AppBaseActivity {
    private TextView mTextRegister = null;
    private Button mButtonLogin = null;
    private TextView mTextForgetPWD = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.login_layout;
    }

    @Override
    public void initViews() {
        mTextRegister = findViewById(R.id.id_login_text_register);
        mButtonLogin = findViewById(R.id.id_login_button_login);
        mTextForgetPWD = findViewById(R.id.id_login_text_forget_pwd);

        mTextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));

            }
        });

        mTextForgetPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));

            }
        });
    }

    @Override
    public void initData() {

    }
}
