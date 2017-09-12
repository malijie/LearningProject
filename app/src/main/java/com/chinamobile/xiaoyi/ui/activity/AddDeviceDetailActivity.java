package com.chinamobile.xiaoyi.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.base.lib.base.AppBaseActivity;
import com.chinamobile.xiaoyi.R;

/**
 * Created by malijie on 2017/9/12.
 */

public class AddDeviceDetailActivity extends AppBaseActivity{
    private ImageButton mButtonBack = null;
    private TextView mTextTitle = null;
    private TextView mTextOption = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.add_device_detail;
    }

    @Override
    public void initViews() {
        mButtonBack = findViewById(R.id.id_title_bar_button_back);
        mTextTitle =  findViewById(R.id.id_title_bar_text_title);
        mTextOption = findViewById(R.id.id_title_bar_text_option);

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(AddDeviceDetailActivity.this);
            }
        });
        mTextOption.setVisibility(View.VISIBLE);
        mTextTitle.setText(TITLE_ADD_DEVICE);
        mTextOption.setText("完成");
    }

    @Override
    public void initData() {

    }
}
