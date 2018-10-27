package com.chinamobile.xiaoyi.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.ui.base.AppBaseActivity;

public class DeviceDetailActivity extends AppBaseActivity {
    private static final String TAG = DeviceDetailActivity.class.getSimpleName();
    private Button mButton = null;
    private Button mButtonSecond = null;
    private GridView mGridView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.device_detail;
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }
}
