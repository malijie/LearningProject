package com.chinamobile.xiaoyi.ui.wedgit;

import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.model.LatLng;
import com.base.lib.utils.ToastManager;
import com.chinamobile.xiaoyi.R;

/**
 * Created by malijie on 2017/9/14.
 */

public class DeviceInfoWindow extends InfoWindow{
    private View v = null;
    private LatLng mLatLng = null;
    private int mOffsetY = 0;

    private LinearLayout mLayoutFence = null;
    private LinearLayout mLayoutTrail = null;
    private LinearLayout mLayoutNavigate = null;
    private LinearLayout mLayoutDetail = null;

    public DeviceInfoWindow(View view, LatLng latLng, int offsetY) {
        super(view, latLng, offsetY);
        this.v = view;
        this.mLatLng = latLng;
        this.mOffsetY = offsetY;

        initView(v);
    }

    public DeviceInfoWindow(BitmapDescriptor bitmapDescriptor, LatLng latLng, int i, OnInfoWindowClickListener onInfoWindowClickListener) {
        super(bitmapDescriptor, latLng, i, onInfoWindowClickListener);
    }

    private void initView(View v) {
        mLayoutFence = v.findViewById(R.id.id_info_window_layout_fence);
        mLayoutTrail =v.findViewById(R.id.id_info_window_layout_trail);
        mLayoutNavigate = v.findViewById(R.id.id_info_window_layout_navigate);
        mLayoutDetail =v.findViewById(R.id.id_info_window_layout_detail);

        mLayoutFence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastManager.showShortMsg("点击围栏");

            }
        });
        mLayoutTrail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastManager.showShortMsg("点击轨迹");

            }
        });

        mLayoutNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastManager.showShortMsg("点击导航");

            }
        });


        mLayoutDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastManager.showShortMsg("点击详情");
            }
        });
    }


}
