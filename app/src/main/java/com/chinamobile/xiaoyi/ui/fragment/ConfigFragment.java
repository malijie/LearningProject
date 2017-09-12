package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chinamobile.xiaoyi.R;


/**
 * Created by malijie on 2016/12/8.
 */

public class ConfigFragment extends BaseFragment {
    private static final String CONIF_TITLE_TEXT = "设置";
    private FragmentManager mSupportFragmentManager;

    public ConfigFragment(FragmentManager supportFragmentManager) {
        this.mSupportFragmentManager = supportFragmentManager;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.config_layout, container, false);
        super.onCreateView(v);

        initViews(v);
        initData();
        return v;
    }

    @Override
    public void initViews(View v) {
        hideBackButton();
        hideOption();
        hideSubTitleLayout();
        showTitle();
        setTitle(CONIF_TITLE_TEXT);
    }

    @Override
    public void initData() {

    }
}