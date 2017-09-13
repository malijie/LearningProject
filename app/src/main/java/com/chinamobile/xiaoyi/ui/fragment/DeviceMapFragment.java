package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.model.LatLng;
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
        mMapController = new BaiduMapController(mActivity,v);
        mMapController.initLocation();
        mMapController.location(new LatLng(39.963175, 116.400244));
        mMapController.setMarker(new LatLng(39.963175, 116.400244));
    }

    @Override
    public void onStart() {
        super.onStart();
        //开启定位
        mMapController.start();
    }


    @Override
    public void initData() {


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
