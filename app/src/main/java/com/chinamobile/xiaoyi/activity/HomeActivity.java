package com.chinamobile.xiaoyi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.lib.base.AppBaseActivity;
import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.XiaoYiHelpApplication;
import com.chinamobile.xiaoyi.map.BaiduMapController;


/**
 * Created by malijie on 2017/9/7.
 */

public class HomeActivity extends AppBaseActivity {
    private static String TAG = HomeActivity.class.getSimpleName();
    private BaiduMapController mMapController = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_layout;
    }


    @Override
    public void initViews() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        mMapController.start();
    }

    @Override
    public void initData() {
        mMapController = new BaiduMapController(this);
        mMapController.initLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapController.destroy();

    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapController.resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mMapController.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭定位
        mMapController.stop();
    }
}
