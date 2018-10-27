package com.chinamobile.xiaoyi.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.ui.base.AppBaseActivity;
import com.chinamobile.xiaoyi.ui.fragment.LoginFragment;
import com.chinamobile.xiaoyi.ui.fragment.RegisterFragment;

/**
 * Created by malijie on 2017/9/12.
 */

public class LoginActivity extends AppBaseActivity {
    private TextView mTextRegister = null;
    private Button mButtonLogin = null;
    private TextView mTextForgetPWD = null;

    private Fragment loginFragment;
    private Fragment registerFragment;

    private FragmentManager fragmentManager;
    private View loginImageView;
    private View registerImageView;
    private RadioButton loginRadioButton;

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

        loginImageView = findViewById(R.id.iv_login_triangle);
        registerImageView = findViewById(R.id.iv_register_triangle);

        fragmentManager = getFragmentManager();
        loginFragment= new LoginFragment();
        registerFragment = new RegisterFragment();
        ((RegisterFragment)registerFragment).setCallBack(new RegisterFragment.CallBack() {
            @Override
            public void onRegisterSuccessCallback() {
                loginRadioButton.setChecked(true);
            }
        });


        ((RadioGroup) findViewById(R.id.rgroup_login_register)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                loginImageView.setVisibility(View.INVISIBLE);
                registerImageView.setVisibility(View.INVISIBLE);

                switch (i) {
                    case R.id.rbtn_login:
                        changeFragment(loginFragment, registerFragment);
                        loginImageView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbtn_register:
                        changeFragment(registerFragment,loginFragment);
                        registerImageView.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        loginRadioButton = (RadioButton) findViewById(R.id.rbtn_login);
        loginRadioButton.setChecked(true);
    }

    private void changeFragment(Fragment in, Fragment out) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (in.isAdded()) {
            transaction.show(in).hide(out);
        }else {
            transaction.add(R.id.ll_login_container,in);
        }
        transaction.commit();
    }

    @Override
    public void initData() {

    }
}
