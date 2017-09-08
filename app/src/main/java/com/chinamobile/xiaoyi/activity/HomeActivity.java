package com.chinamobile.xiaoyi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.base.lib.base.AppBaseActivity;
import com.base.lib.utils.Logger;
import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.XiaoYiHelpApplication;


/**
 * Created by malijie on 2017/9/7.
 */

public class HomeActivity extends AppBaseActivity {
    private static String TAG = HomeActivity.class.getSimpleName();
    private Button mButtonNaviCenter;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    //定位相关
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;
    private LatLng mLatLng = null;
    private double mLongitude;
    private double mLatitude;
    private boolean isFirstIn = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initViews();
        initData();




    }

    private Context getContext(){
        return XiaoYiHelpApplication.mContext;
    }

    @Override
    public void initViews() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mButtonNaviCenter = findViewById(R.id.id_home_btn_back_home);
        mButtonNaviCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                centerToMyLocation();
            }
        });

        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        initLocation();

    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);

        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .latitude(location.getLatitude())
                    .longitude(location.getLatitude()).build();

            mBaiduMap.setMyLocationData(data);


            mLongitude = location.getLongitude();
            mLatitude = location.getLatitude();
Logger.mlj("location lat=" + mLatitude + ",log=" + mLongitude);

            if(isFirstIn){
                centerToMyLocation();
                isFirstIn = false;
            }

        }
    }

    private void centerToMyLocation(){
        LatLng latLng = new LatLng(mLatitude,mLongitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
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

    @Override
    protected void onStop() {
        super.onStop();
        //关闭定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }
}
