package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.lib.base.AppBaseActivity;
import com.base.lib.utils.Util;
import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.util.CommonUtil;


/**
 * Created by malijie on 2016/12/8.
 */

public class DeviceFragment extends BaseFragment implements View.OnClickListener{
    private Button mButtonMap = null;
    private Button mButtonList = null;

    private DeviceMapFragment mDeviceMapFragment;
    private DeviceListFragment mDeviceListFragment;


    private FragmentManager fragmentManager;

    public DeviceFragment(FragmentManager supportFragmentManager) {
        this.fragmentManager = supportFragmentManager;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.device_layout, container, false);
        super.onCreateView(v);

        initViews(v);
        initData();

        setTabSelection(0);
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_title_bar_button_map:
                setTabSelection(0);
                break;
            case R.id.id_title_bar_button_list:
                setTabSelection(1);
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                mButtonMap.setTextColor(CommonUtil.getColor(R.color.app_red));
                mButtonMap.setBackground(CommonUtil.getDrawable(R.drawable.top_bar_left_button_selected));
                if (mDeviceMapFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mDeviceMapFragment = new DeviceMapFragment(getFragmentManager());
                    transaction.add(R.id.id_layout_content, mDeviceMapFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mDeviceMapFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                mButtonList.setTextColor(CommonUtil.getColor(R.color.app_red));
                mButtonList.setBackground(CommonUtil.getDrawable(R.drawable.top_bar_right_button_selected));

                if (mDeviceListFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    mDeviceListFragment = new DeviceListFragment(getFragmentManager());
                    transaction.add(R.id.id_layout_content, mDeviceListFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(mDeviceListFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mDeviceMapFragment != null) {
            transaction.hide(mDeviceMapFragment);
        }
        if (mDeviceListFragment != null) {
            transaction.hide(mDeviceListFragment);
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        mButtonMap.setBackgroundColor(CommonUtil.getColor(R.color.white));
        mButtonMap.setTextColor(CommonUtil.getColor(R.color.tab_black));
        mButtonMap.setBackground(CommonUtil.getDrawable(R.drawable.top_bar_left_button_normal));

        mButtonList.setBackgroundColor(CommonUtil.getColor(R.color.white));
        mButtonList.setTextColor(CommonUtil.getColor(R.color.tab_black));
        mButtonList.setBackground(CommonUtil.getDrawable(R.drawable.top_bar_right_button_normal));


    }

    @Override
    public void initViews(View v) {
        mButtonMap = v.findViewById(R.id.id_title_bar_button_map);
        mButtonList = v.findViewById(R.id.id_title_bar_button_list);
        showSubTitleLayout();
        showOption();
        hideTitle();
        hideBackButton();
        mButtonMap.setOnClickListener(this);
        mButtonList.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }
}