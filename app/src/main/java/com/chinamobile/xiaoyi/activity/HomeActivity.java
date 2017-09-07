package com.chinamobile.xiaoyi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.base.lib.base.AppBaseActivity;
import com.base.lib.base.BaseActivity;
import com.chinamobile.xiaoyi.R;

/**
 * Created by malijie on 2017/9/7.
 */

public class HomeActivity extends AppBaseActivity {
    private LocationClient mLocationClient;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initViews();
        initData();

    }

    @Override
    public void initViews() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//sadasdsadasd

        mLocationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();

        //就是这个方法设置为true，才能获取当前的位置信息
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setIsNeedAddress(true);
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
        //int span = 1000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mLocationClient.setLocOption(option);



    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            //经纬度
            double lati = location.getLatitude();
            double longa = location.getLongitude();
            //打印出当前位置
            Log.i("TAG", "location.getAddrStr()=" + location.getAddrStr());
            //打印出当前城市
            Log.i("TAG", "location.getCity()=" + location.getCity());
            //返回码
            int i = location.getLocType();
        }
    }



    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
