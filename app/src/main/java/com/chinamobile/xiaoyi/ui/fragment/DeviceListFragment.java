package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.http.entity.DeviceInfo;
import com.chinamobile.xiaoyi.http.http.RequestParams;
import com.chinamobile.xiaoyi.ui.activity.AddDeviceActivity;
import com.chinamobile.xiaoyi.ui.activity.PayActiveActivity;
import com.chinamobile.xiaoyi.ui.activity.UserDetailActivity;
import com.chinamobile.xiaoyi.ui.adapter.DeviceInfoAdapter;
import com.chinamobile.xiaoyi.ui.base.IDeviceUIAction;
import com.chinamobile.xiaoyi.util.CommonUtil;
import com.chinamobile.xiaoyi.util.Logger;
import com.chinamobile.xiaoyi.util.ToastManager;
import com.chinamobile.xiaoyi.util.enums.ActiveEnum;

import java.util.List;

import static com.chinamobile.xiaoyi.ui.base.ActivityResultCode.REQUEST_CODE_RESULT;
import static com.chinamobile.xiaoyi.ui.fragment.LocationFragment.REQUEST_CODE_REFRESH;

/**
 * Created by malijie on 2017/9/12.
 */

public class DeviceListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,IDeviceUIAction {
    private static final String TAG = DeviceListFragment.class.getSimpleName();
    private ListView mListView = null;
    private ImageView mImageRefresh = null;
    private DeviceInfoAdapter mAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private List<DeviceInfo> mDeviceInfos;

    private Activity mActivity = null;
    private RelativeLayout mEmptyLayout = null;
    private Button mButtonAddDevice = null;

    public DeviceListFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.device_list, container, false);
        mActivity = getActivity();

        initViews(v);
        initData();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void initViews(View v) {
        mProgressDialog = CommonUtil.createProgressDialog(getActivity(),getResources().getString(R.string.loading_progress_tip));
        mListView = v.findViewById(R.id.id_device_list_view);
        mSwipeRefreshLayout = v.findViewById(R.id.id_device_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mEmptyLayout = v.findViewById(R.id.id_empty_layout);
        mButtonAddDevice = v.findViewById(R.id.id_empty_button_add);
        mImageRefresh = v.findViewById(R.id.id_empty_device_image);


        mButtonAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(mActivity,AddDeviceActivity.class),REQUEST_CODE_REFRESH);
            }
        });

        mImageRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });

    }

    @Override
    public void initData() {
        mDeviceController.addDeviceActionPage(this);
        mDeviceInfos = (List<DeviceInfo>) mActivity.getIntent().getExtras().getSerializable("loginInfoList");

        if(mDeviceInfos != null){
            mAdapter = new DeviceInfoAdapter(getActivity(), mDeviceInfos);
            mListView.setAdapter(mAdapter);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               DeviceInfo deviceInfo = mDeviceInfos.get(position);

                //激活中或激活失败，返回
                if(deviceInfo.getActivateStatus() == ActiveEnum.ACTIVE_FAILED.getActive() ||
                        deviceInfo.getActivateStatus() == ActiveEnum.ACTIVING.getActive()){
                    return;
                }

                //还未支付跳转到支付界面
                if(deviceInfo.getActivateStatus() == ActiveEnum.UNACTIVE.getActive()){
                    Bundle bundle = new Bundle();
                    bundle.putString("imei",deviceInfo.getImei());
                    Intent i = new Intent(getActivity(),PayActiveActivity.class);
                    i.putExtras(bundle);
                    startActivityForResult(i,REQUEST_CODE_RESULT);
                    return;
                }

                //坐标异常提示
                if(deviceInfo.getLatitude() == 0 || deviceInfo.getLongitude() == 0){
                    ToastManager.showLatLngErrorTip();
                }else{
                    //记录用户最近选择的设备
                    mDeviceController.saveActiveDeviceInfo(mDeviceInfos.get(position));
                }

                //跳转到详情页
                go2DeviceDetailPage(deviceInfo);
//              mDeviceController.locateDevice(mDeviceInfos.get(position));
            }
        });

        if(mDeviceInfos.size() == 0){
            showEmptyLayout();
        }

    }


    @Override
    public void onRefresh() {
        mDeviceController.refresh();
    }

    private void refreshData(){
        RequestParams params = new RequestParams();
        params.setParamsValue("phone",getUserPhone());
        action.getDeviceList(params, new RequestCallback<List<DeviceInfo>>() {
            @Override
            public void onSuccess(List<DeviceInfo> data) {
                if(data.size() == 0){
                    showEmptyLayout();
                }else{
                    showListLayout();
                    mDeviceInfos.clear();
                    for (int i=0;i<data.size();i++){
                        mDeviceInfos.add(data.get(i));
                        //设置最新剩余时间
                        mDeviceController.setActiveTimeLeft(data.get(i));
                    }
                }

                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(String errorMsg) {
                mSwipeRefreshLayout.setRefreshing(false);
                super.onFailed(errorMsg);
            }
        });
    }

    @Override
    public void refresh() {
        refreshData();
        mAdapter.removeMsg();
    }

    @Override
    public void locateDevice(DeviceInfo deviceInfo) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.d(TAG,"requestCode=" +  requestCode + ", resultCode="+ resultCode + ",data=" + data);
        switch (requestCode) {
            case REQUEST_CODE_RESULT:
                if (mAdapter != null && mDeviceInfos != null) {
                    mDeviceController.refresh();
                    break;
                }
        }

    }


    private void showEmptyLayout(){
        mEmptyLayout.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    private void showListLayout(){
        mEmptyLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 跳转到详情页
     * @param deviceInfo
     */
    private void go2DeviceDetailPage(DeviceInfo deviceInfo){
        Intent i = new Intent(getActivity(),UserDetailActivity.class);
        i.putExtra("deviceInfo",deviceInfo);
        startActivityForResult(i,REQUEST_CODE_RESULT);
    }

}
