package com.chinamobile.xiaoyi.map;

import android.app.Activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.chinamobile.xiaoyi.R;

/**
 * Created by malijie on 2017/9/7.
 */

public class BaiduMapController {
    private Activity mActivity;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;

    private MyLocationListener myLocationListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;

    private LatLng mLatLng = null;
    private double mLongitude;
    private double mLatitude;
    private boolean isFirstIn = true;
    private float mCurrentDirection = 0;
    private LocationClient mLocationClient;

    public BaiduMapController(Activity activity){
        mActivity = activity;
        mMapView =  activity.findViewById(R.id.id_map_mapview);
    }

    public void initLocation(){
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocationClient = new LocationClient(mActivity);
        mLocationClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mLongitude = location.getLongitude();
            mLatitude = location.getLatitude();

            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(data);

            if(isFirstIn){
                centerToMyLocation(location);
                isFirstIn = false;
            }

        }
    }

    private void centerToMyLocation(BDLocation location){
        LatLng ll = new LatLng(location.getLatitude(),
                location.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    public void start(){
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
    }

    public void destroy(){
        mMapView.onDestroy();
    }

    public void resume(){
        mMapView.onResume();
    }

    public void pause(){
        mMapView.onPause();
    }

    public void stop(){
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

}
