package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.baidu.mapapi.model.LatLng;
import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.control.DevicePermission;
import com.chinamobile.xiaoyi.http.entity.DeviceInfo;
import com.chinamobile.xiaoyi.http.http.RequestParams;
import com.chinamobile.xiaoyi.map.BaiduMapController;
import com.chinamobile.xiaoyi.ui.activity.DeviceSwitchActivity;
import com.chinamobile.xiaoyi.ui.base.IDeviceUIAction;
import com.chinamobile.xiaoyi.ui.widget.TipDialog;
import com.chinamobile.xiaoyi.util.CommonUtil;
import com.chinamobile.xiaoyi.util.Logger;
import com.chinamobile.xiaoyi.util.SharedPreferenceUtil;
import com.chinamobile.xiaoyi.util.ToastManager;
import com.chinamobile.xiaoyi.util.enums.ActiveEnum;

import java.io.Serializable;
import java.util.List;

import static com.chinamobile.xiaoyi.ui.base.ActivityResultCode.REQUEST_CODE_RESULT;

/**
 * Created by malijie on 2017/9/12.
 */

public class DeviceMapFragment extends BaseFragment implements IDeviceUIAction {
    private static final String TAG = DeviceMapFragment.class.getSimpleName();
    private static final int LOW_BATTERY_PERCENT = 30;

    private ImageButton mButtonRefresh = null;
    private ImageButton mButtonUserLocation = null;
    private FragmentManager fragmentManager;
    private BaiduMapController mMapController = null;
    private Activity mActivity = null;
    private ViewGroup mContainer;
    private String mPhone;
    private View v;
    private List<DeviceInfo> mDeviceInfos = null;

    public DeviceMapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.device_map, container, false);
        mContainer = container;
        mActivity = getActivity();
        initViews(v);
        initData();

        return v;
    }

    @Override
    public void initViews(View v) {
        mMapController = BaiduMapController.getInstance();
        mMapController.init(v,mActivity);
        mProgressDialog = CommonUtil.createProgressDialog(getActivity(),"获取设备中...");

        mButtonRefresh = v.findViewById(R.id.id_device_map_button_refresh);
        mButtonUserLocation = v.findViewById(R.id.id_device_map_button_location);
        v.findViewById(R.id.id_device_map_button_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeviceSwitchActivity.class);
                i.putExtra("device_infos", (Serializable) mDeviceInfos);
                startActivityForResult(i,REQUEST_CODE_RESULT);
            }
        });

        mButtonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeviceController.refresh();
            }
        });

        mButtonUserLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapController.centerToUserLocation();
            }
        });

    }


    @Override
    public void initData() {
        Logger.d(TAG,"initData");
        mDeviceController.addDeviceActionPage(this);
        mPhone = SharedPreferenceUtil.loadPhone();
        showBindDeviceEvent();
    }

    /**
     * 显示当前用户绑定的设备
     */
    private void showBindDeviceEvent(){
        mDeviceInfos = (List<DeviceInfo>) mActivity.getIntent().getExtras().getSerializable("loginInfoList");
        if(mDeviceInfos== null || mDeviceInfos.size() == 0){
            //没有绑定设备
            ToastManager.showNoDeviceBind();
            mMapController.centerToUserLocation();
            return;
        }

        for(int i=0;i<mDeviceInfos.size();i++){
            refreshDeviceMarkerOnMap(mDeviceInfos.get(i),i);
            //设置最新剩余时间
            mDeviceController.setActiveTimeLeft(mDeviceInfos.get(i));
        }

        centerLocation(mDeviceInfos);

        handleTargetDevices(mDeviceInfos);

        showLowBatteryAlarmTip(mDeviceInfos);

    }

    /**
     * 显示低电量提示，条件设备上报且电量小于30%
     * @param deviceInfos
     */
    private void showLowBatteryAlarmTip(List<DeviceInfo> deviceInfos) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<deviceInfos.size();i++){
            DeviceInfo deviceInfo = deviceInfos.get(i);
            if(DevicePermission.isReported(deviceInfo) && deviceInfo.getPercent() < LOW_BATTERY_PERCENT){
                builder.append(deviceInfo.getName() + "(" + deviceInfo.getIdenCode() + ")的设备电量为" + deviceInfo.getPercent() + "%，");

                if(i != deviceInfos.size()-1){
                    builder.append("\n");
                }
            }
        }
        //有低电量设备
        if(builder.length()>0){
            TipDialog dialog = new TipDialog(getActivity(),builder.substring(0,builder.length()-1).toString());
            dialog.show();
        }

    }


    /**
     * 刷新设备在地图
     */
    private void refreshDeviceMarkerOnMap(DeviceInfo deviceInfo, int index) {
        if(deviceInfo == null){
            ToastManager.showNoDeviceBind();
            return;
        }

//        if(deviceInfo.getLatitude() == 0 && deviceInfo.getLongitude() == 0){
//            mMapController.centerToUserLocation();
//            return;
//        }

        //未激活不显示
        if(deviceInfo.getActivateStatus() == ActiveEnum.UNACTIVE.getActive()){
            return;
        }

//        mMapController.setMarker(deviceInfo,index);

    }

    /**
     * 地图显示设备位置
     *  默认地图上显示第一个设备
     */
    private void centerLocation(List<DeviceInfo> deviceInfos){
        //1. 多个设备，用用户之前已有选择的设备，定位到用户选择的设备
        if(!TextUtils.isEmpty(SharedPreferenceUtil.loadLatitude()) &&
                !TextUtils.isEmpty(SharedPreferenceUtil.loadLongitude())){
            //第一次定位设备最近定位为空，定位到用户所在位置
            mMapController.location(mDeviceController.loadLatLng());
            return;
        }

        //2. 多个设备，若用户之前没有选择设备，且有设备上报位置，有设备未上报，则定位到第一个上报位置且激活的设备
        for(DeviceInfo deviceInfo :deviceInfos){
            if(hasLatLng(deviceInfo) && mDeviceController.isActive(deviceInfo)){
                LatLng center = new LatLng(deviceInfo.getLatitude(),deviceInfo.getLongitude());
                mMapController.location(center);
                return;
            }
        }

        //3. 如果多个设备都未上报数据，定位到用户所在位置
        mMapController.centerToUserLocation();
    }

    /**
     * 设备是否有经纬度
     * @param deviceInfo
     * @return
     */
    private boolean hasLatLng(DeviceInfo deviceInfo){
        return deviceInfo.getLatitude()!=0 && deviceInfo.getLongitude()!=0;
    }



    @Override
    public void onHiddenChanged(boolean hidden) {

        if(!hidden){
            mMapController = BaiduMapController.getInstance();
            mMapController.init(v,mActivity);
            mMapController.resume();
            mMapController.setOnMapClickListener();
        }else{
//          hideDeviceInfoWindow();
            mMapController.pause();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void refresh() {
        refreshEvent();
    }

    @Override
    public void locateDevice(DeviceInfo deviceInfo) {
        //地图定位设备位置
        doLocate(deviceInfo);
    }

    private void doLocate(DeviceInfo deviceInfo){
        if(deviceInfo != null && deviceInfo.getLatitude() != 0.0 && deviceInfo.getLongitude() != 0.0){
            mMapController.location(new LatLng(deviceInfo.getLatitude(),deviceInfo.getLongitude()));
        }
    }

    /**
     * 显示指定设备弹窗
     */
    private void showTargetInfoWindow(DeviceInfo deviceInfo){
        mMapController.showInfoWindow(deviceInfo);
    }

    /**
     * 隐藏设备弹窗
     */
    private void hideDeviceInfoWindow(){
        mMapController.hideInfoWindow();
    }

    /**
     * 网络请求设备列表并刷新地图
     */
    private void refreshEvent(){

        mMapController.clearStatus();
        mProgressDialog.show();
        RequestParams params = new RequestParams();
        params.setParamsValue("phone",getUserPhone());
        action.getDeviceList(params, new RequestCallback<List<DeviceInfo>>() {
            @Override
            public void onSuccess(List<DeviceInfo> data) {
                mProgressDialog.dismiss();
                mDeviceInfos.clear();
                mDeviceInfos = data;

                if(data == null || data.size() == 0){
                    ToastManager.showNoDeviceBind();
                    return;
                }

                for(int i=0;i<data.size();i++){
                    refreshDeviceMarkerOnMap(data.get(i),i);
                    //设置最新剩余时间
                    mDeviceController.setActiveTimeLeft(data.get(i));
                }

                //保存最近访问设备最新坐标
                for(DeviceInfo deviceInfo : data){
                    if(hasLatLng(deviceInfo) && deviceInfo.getImei().equals(mDeviceController.getSavedImei())){
                        mDeviceController.saveActiveDeviceInfo(deviceInfo);
                    }
                }

                centerLocation(data);
                handleTargetDevices(data);
            }

            @Override
            public void onFailed(String errorMsg) {
                super.onFailed(errorMsg);
            }
        });

    }

    /**
     * 设置弹窗和图标
     * @param deviceInfos
     */
    private void handleTargetDevices(List<DeviceInfo> deviceInfos){
        DeviceInfo target = mDeviceController.findNeedShowDeviceInfo(deviceInfos);
        if(target == null){
            return;
        }

        mMapController.setMarker(target);
        showTargetInfoWindow(target);
    }


    @Override
    public void onDestroy() {
        super.onStop();
        //关闭定位
        mMapController.stop();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开启定位
        mMapController.start();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.d(TAG,"requestCode=" +  requestCode + ", resultCode="+ resultCode + ",data=" + data);
        switch (requestCode) {
            case REQUEST_CODE_RESULT:
                mDeviceController.refresh();
            break;
        }
    }
}
