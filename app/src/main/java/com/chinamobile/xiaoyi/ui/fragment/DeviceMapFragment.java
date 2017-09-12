package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chinamobile.xiaoyi.R;

/**
 * Created by malijie on 2017/9/12.
 */

public class DeviceMapFragment extends BaseFragment {

    private FragmentManager fragmentManager;

    public DeviceMapFragment(FragmentManager supportFragmentManager) {
        this.fragmentManager = supportFragmentManager;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.device_map, container, false);
        return messageLayout;
    }

    @Override
    public void initViews(View v) {

    }

    @Override
    public void initData() {

    }
}
