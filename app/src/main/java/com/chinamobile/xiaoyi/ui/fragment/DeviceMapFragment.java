package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.map.BaiduMapController;

/**
 * Created by malijie on 2017/9/12.
 */

public class DeviceMapFragment extends BaseFragment {

    private FragmentManager fragmentManager;
    private BaiduMapController mMapController = null;
    private Activity mActivity = null;


    public DeviceMapFragment(FragmentManager supportFragmentManager) {
        this.fragmentManager = supportFragmentManager;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.device_map, container, false);
        mActivity = getActivity();
        initViews(v);
        initData();

        return v;
    }

    @Override
    public void initViews(View v) {

    }

    @Override
    public void onStart() {
        super.onStart();
        //开启定位
        mMapController.start();
    }


    @Override
    public void initData() {
        mMapController = new BaiduMapController(getActivity());
        mMapController.initLocation();

    }

    @Override
    public void onDestroy() {
        super.onStop();
        //关闭定位
        mMapController.stop();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapController.resume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapController.pause();
    }


    @Override
    public void onStop() {
        super.onStop();
        mMapController.stop();
    }
}
