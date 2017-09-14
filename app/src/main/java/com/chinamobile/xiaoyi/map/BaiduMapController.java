package com.chinamobile.xiaoyi.map;

import android.app.Activity;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.base.lib.utils.ToastManager;
import com.base.lib.utils.Util;
import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.ui.wedgit.DeviceInfoWindow;

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

    public BaiduMapController(Activity activity,View v){
        mActivity = activity;
        mMapView =  v.findViewById(R.id.id_map_mapview);
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
//        LatLng ll = new LatLng(location.getLatitude(),
//                location.getLongitude());
//        MapStatus.Builder builder = new MapStatus.Builder();
//        builder.target(ll).zoom(18.0f);
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
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

    /**
     * 根据经纬度定位位置
     * @param latLng
     */
    public void location(LatLng latLng){
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    public void setMarker(final LatLng latLng){
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_launcher);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ToastManager.showShortMsg("click marker");
                View v = Util.getView(R.layout.info_window);
                DeviceInfoWindow infoWindow = new DeviceInfoWindow(v,latLng,-80);
                mBaiduMap.showInfoWindow(infoWindow);

                return false;
            }
        });
    }


}
