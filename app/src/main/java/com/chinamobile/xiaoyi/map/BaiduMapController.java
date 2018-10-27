package com.chinamobile.xiaoyi.map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.XiaoYiHelpApplication;
import com.chinamobile.xiaoyi.http.entity.DeviceInfo;
import com.chinamobile.xiaoyi.control.DeviceController;
import com.chinamobile.xiaoyi.util.CommonUtil;
import com.chinamobile.xiaoyi.util.Logger;
import com.chinamobile.xiaoyi.util.SharedPreferenceUtil;
import com.chinamobile.xiaoyi.ui.widget.DeviceInfoWindow;
import com.chinamobile.xiaoyi.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malijie on 2017/9/7.
 */

public class BaiduMapController{
    private static final String TAG  = BaiduMapController.class.getSimpleName();

    private static final int accuracyCircleFillColor =  0x00000000;//CommonUtil.getColor(R.color.white);
//    private static final int accuracyCircleFillColor =  0x22FF9933;//CommonUtil.getColor(R.color.white);
    private static final int accuracyCircleStrokeColor = CommonUtil.getColor(R.color.accuracyCircleStrokeColor);

    private Activity mActivity = null;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private static BaiduMapController sController = null;
    private XiaoYiHelpApplication trackApp = null;
    private DeviceController mController = null;

    //地址定位回调
    private MyLocationListener myLocationListener = new MyLocationListener();
    //定位模式，正常，罗盘，跟随
    private MyLocationConfiguration.LocationMode mCurrentMode;
    //地图工具类
    private MapUtil mMapUtil = MapUtil.getInstance();
    //设备坐标
    private LatLng mDeviceLatLng = null;
    //纬度
    private double mLongitude;
    //经度
    private double mLatitude;
    //是否第一次进入
    private boolean isFirstIn = true;
    //当前方向
    private float mCurrentDirection = 0;
    private LocationClient mLocationClient;
    //点击marker浮窗
    private DeviceInfoWindow infoWindow = null;
    //定位Location
    private BDLocation mBDLocation = null;


    public static BaiduMapController getInstance(){
        if(sController == null){
            synchronized (BaiduMapController.class){
                if(sController == null){
                    sController = new BaiduMapController();
                }
            }
        }
        return sController;
    }

    /**
     * fragment初始化地图方式
     */
    public void init(View v,Activity activity){
        mMapView =  v.findViewById(R.id.id_map_mapview);
        mActivity = activity;
        trackApp = (XiaoYiHelpApplication) mActivity.getApplication();
        mMapUtil.init(mMapView);
        initLocation();

        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
        mMapView.showScaleControl(false);
        // 不显示地图缩放控件（按钮控制栏）
        mMapView.showZoomControls(false);

        initListener();
    }
    /**
     * activity初始化地图方式
     */
    public void init(Activity activity){
        mActivity = activity;
        mMapView =  activity.findViewById(R.id.id_map_mapview);
        trackApp = (XiaoYiHelpApplication) mActivity.getApplication();
        mMapUtil.init(mMapView);
        initListener();
    }

    private void initListener() {
//        mMapUtil.baiduMap.setOnMapClickListener(this);
    }

    private BaiduMapController(){
        mController = DeviceController.getInstance();
    }

    public void initLocation(){

        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocationClient = new LocationClient(XiaoYiHelpApplication.mContext);
        mLocationClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.mipmap.target_user);

        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.target_user_test);

        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleFillColor));
//        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
//                mCurrentMode, true, mCurrentMarker));
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
                mBDLocation = location;
                saveUserLocation(mBDLocation);
                isFirstIn = false;
            }

        }
    }

    private void saveUserLocation(BDLocation location){
        SharedPreferenceUtil.saveUserLatitude(String.valueOf(location.getLatitude()));
        SharedPreferenceUtil.saveUserLongitude(String.valueOf(location.getLongitude()));
    }

    public LatLng getUserLocation(){
        return new LatLng(mBDLocation.getLatitude(),mBDLocation.getLongitude());
    }

    /**
     * 定位到使用者当前位置
     */
    public void centerToUserLocation(){
        if(mBDLocation != null){
            LatLng ll = new LatLng(mBDLocation.getLatitude(),
                    mBDLocation.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(15.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    centerToUserLocation();
                }
            },500);
        }
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
        mMapView.setVisibility(View.VISIBLE);
        mMapView.onResume();
    }

    public void pause(){
        mMapView.setVisibility(View.INVISIBLE);
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
        builder.target(latLng).zoom(17.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private Overlay overlay = null;
    private List<Overlay> mOverlays = new ArrayList<>();
    private boolean isShown = true;
    private List<BaiduMap.OnMarkerClickListener> listenerList = new ArrayList<>();

//    public void setMarker(final DeviceInfo deviceInfo,final int index){
//        //没有上报数据返回
//        if(mController.isNullLntLng(deviceInfo)){
//            return;
//        }
//
//        final LatLng latLng = new LatLng(deviceInfo.getLatitude(),deviceInfo.getLongitude());
//
//        Bundle mBundle = new Bundle();
//        mBundle.putInt("id", index);
//        mController.setDeviceInfo(deviceInfo);
//
//        //构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(mController.getSmallIconRes());
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(latLng)
//                .extraInfo(mBundle)
//                .icon(bitmap);
//        //在地图上添加Marker，并显示
//        overlay = mBaiduMap.addOverlay(option);
//
////        Marker marker = (Marker) mBaiduMap.addOverlay(option);
////        marker.setToTop();
//
//        mOverlays.add(overlay);
//
//        BaiduMap.OnMarkerClickListener listener = new BaiduMap.OnMarkerClickListener(){
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                marker.setToTop();
//
//                Bundle bundle = marker.getExtraInfo();
//                Logger.e(TAG,"bundle=" + bundle + ",isShown=" + isShown);
//                if(bundle == null){
//                    bundle = new Bundle();
//                    bundle.putInt("id", index);
////                    return false;
//                }
//                int id = bundle.getInt("id");
//                if(id == index){
//                    Logger.d(TAG,"showInfoWindow");
////                    if(!isShown){
//                        showInfoWindow(deviceInfo);
////                        isShown = true;
////                    }else{
////                        hideInfoWindow();
////                        showInfoWindow(deviceInfo,latLng);
////                    }
//                    mController.saveActiveDeviceInfo(deviceInfo);
//                }else{
//                    Logger.e(TAG,"id=" + id + ",index=" + index);
//                }
//                return true;
//            }
//        };
//
//        mBaiduMap.setOnMarkerClickListener(listener);
//        listenerList.add(listener);
//        setOnMapClickListener();
//    }

    public void setMarker(final DeviceInfo deviceInfo){
        //没有上报数据返回
        if(deviceInfo== null || mController.isNullLntLng(deviceInfo)){
            return;
        }

        final LatLng latLng = new LatLng(deviceInfo.getLatitude(),deviceInfo.getLongitude());
        final int index = 0;

        Bundle mBundle = new Bundle();
        mBundle.putInt("id", index);
        mController.setDeviceInfo(deviceInfo);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(mController.getSmallIconRes());
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .extraInfo(mBundle)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        overlay = mBaiduMap.addOverlay(option);

//        Marker marker = (Marker) mBaiduMap.addOverlay(option);
//        marker.setToTop();

        mOverlays.add(overlay);

        BaiduMap.OnMarkerClickListener listener = new BaiduMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setToTop();

                Bundle bundle = marker.getExtraInfo();
                Logger.e(TAG,"bundle=" + bundle + ",isShown=" + isShown);
                if(bundle == null){
                    bundle = new Bundle();
                    bundle.putInt("id", index);
//                    return false;
                }
                int id = bundle.getInt("id");
                if(id == index){
                    Logger.d(TAG,"showInfoWindow");
//                    if(!isShown){
                    showInfoWindow(deviceInfo);
//                        isShown = true;
//                    }else{
//                        hideInfoWindow();
//                        showInfoWindow(deviceInfo,latLng);
//                    }
                    mController.saveActiveDeviceInfo(deviceInfo);
                }else{
                    Logger.e(TAG,"id=" + id + ",index=" + index);
                }
                return true;
            }
        };

        mBaiduMap.setOnMarkerClickListener(listener);
        listenerList.add(listener);
        setOnMapClickListener();
    }

    public void showInfoWindow(DeviceInfo deviceInfo){
        LatLng latLng = new LatLng(deviceInfo.getLatitude(),deviceInfo.getLongitude());
        View v = CommonUtil.getView(R.layout.info_window);
        infoWindow = new DeviceInfoWindow(mActivity,mBaiduMap,v,deviceInfo,latLng,-100);
        mBaiduMap.showInfoWindow(infoWindow);
    }

    public void setOnMapClickListener(){
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                Logger.d(TAG,"==onMapPoiClick==");
                hideInfoWindow();
                return false;
            }
            @Override
            public void onMapClick(LatLng latLng) {
                Logger.d(TAG,"==onMapClick==");
                hideInfoWindow();
            }
        });
    }

    public void clearStatus(){
        clearMarkers();
        hideInfoWindow();
        clearMarkOnClickListener();
    }

    public void clearMarkOnClickListener(){
        if(listenerList.size() != 0){
            for(int i=0;i<listenerList.size();i++){
                mBaiduMap.removeMarkerClickListener(listenerList.get(i));
            }
        }
        listenerList.clear();
    }

    public void hideInfoWindow(){
        if(infoWindow != null){
            Logger.d(TAG,"hideInfoWindow");
            infoWindow.hide();
            isShown = false;
        }
    }


    private void clearMarkers(){
        for (Overlay overlay: mOverlays) {
             overlay.remove();
        }
    }



    /**
     * 获取设备当前位置
     * @return
     */
    public void setDeviceLatLng(LatLng latLng){
        mDeviceLatLng = latLng;
    }

    public BaiduMap getBaiduMap(){
        return mBaiduMap;
    }

}
