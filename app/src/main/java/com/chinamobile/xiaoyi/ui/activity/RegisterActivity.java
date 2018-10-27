package com.chinamobile.xiaoyi.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.ui.base.AppBaseActivity;

/**
 * Created by malijie on 2017/9/13.
 */

public class RegisterActivity extends AppBaseActivity {
    private TextView mTextTitle = null;
    private ImageButton mButtonBack = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.register_layout;
    }

    @Override
    public void initViews() {
        mTextTitle = findViewById(R.id.id_title_bar_text_title);
        mButtonBack = findViewById(R.id.id_title_bar_button_back);
        mTextTitle.setText(TITLE_REGISTER);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(RegisterActivity.this);
            }
        });
    }

    @Override
    public void initData() {

    }
}
