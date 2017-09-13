package com.chinamobile.xiaoyi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.base.lib.base.AppBaseActivity;
import com.base.lib.entity.WelfareInfo;
import com.base.lib.http.RequestParams;
import com.base.lib.utils.Logger;
import com.base.lib.utils.Util;
import com.chinamobile.xiaoyi.R;

import java.util.List;

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
