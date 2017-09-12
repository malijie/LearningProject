package com.chinamobile.xiaoyi.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.lib.base.AppBaseActivity;
import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.ui.fragment.ConfigFragment;
import com.chinamobile.xiaoyi.ui.fragment.DeviceFragment;
import com.chinamobile.xiaoyi.ui.fragment.MessageFragment;
import com.chinamobile.xiaoyi.util.CommonUtil;

/**
 * Created by malijie on 2017/9/12.
 */

public class MainActivity extends AppBaseActivity implements View.OnClickListener{

    private static final int TAB_DEVICE_INDEX = 0;
    private static final int TAB_MSG_INDEX = 1;
    private static final int TAB_CONFIG_INDEX = 2;

    private DeviceFragment mDeviceFragment;
    private MessageFragment mMsgFragment;
    private ConfigFragment mConfigFragment;

    private View mDeviceLayout;
    private View mMsgLayout;
    private View mConfigLayout;

    private ImageView mDeviceImage;
    private ImageView mMsgImage;
    private ImageView mConfigImage;

    private TextView mDeviceText;
    private TextView mMsgText;
    private TextView mConfigCarText;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化布局元素
        initViews();
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_bottom_device_layout:
                // 当点击了分答tab时，选中第1个tab
                setTabSelection(TAB_DEVICE_INDEX);

                break;
            case R.id.id_bottom_msg_layout:
                // 当点击了活动tab时，选中第2个tab
                setTabSelection(TAB_MSG_INDEX);
                break;
            case R.id.id_bottom_config_layout:
                // 当点击了拼车tab时，选中第3个tab
                setTabSelection(TAB_CONFIG_INDEX);

                break;
            default:
                break;
        }
    }


    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case TAB_DEVICE_INDEX:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                mDeviceImage.setImageResource(R.mipmap.tab_device_selected);
                mDeviceText.setTextColor(CommonUtil.getColor(R.color.app_red));
                if (mDeviceFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mDeviceFragment = new DeviceFragment(getSupportFragmentManager());
                    transaction.add(R.id.id_layout_content, mDeviceFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mDeviceFragment);
                }
                break;
            case TAB_MSG_INDEX:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                mMsgImage.setImageResource(R.mipmap.tab_device_selected);
                mMsgText.setTextColor(CommonUtil.getColor(R.color.app_red));
                if (mMsgFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    mMsgFragment = new MessageFragment(getSupportFragmentManager());
                    transaction.add(R.id.id_layout_content, mMsgFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(mMsgFragment);
                }
                break;
            case TAB_CONFIG_INDEX:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                mConfigImage.setImageResource(R.mipmap.tab_device_selected);
                mConfigCarText.setTextColor(CommonUtil.getColor(R.color.app_red));
                if (mConfigFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    mConfigFragment = new ConfigFragment(getSupportFragmentManager());
                    transaction.add(R.id.id_layout_content, mConfigFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(mConfigFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        mDeviceImage.setImageResource(R.mipmap.tab_device_normal);
        mDeviceText.setTextColor(CommonUtil.getColor(R.color.tab_black));

        mMsgImage.setImageResource(R.mipmap.tab_device_normal);
        mMsgText.setTextColor(CommonUtil.getColor(R.color.tab_black));

        mConfigImage.setImageResource(R.mipmap.tab_device_normal);
        mConfigCarText.setTextColor(CommonUtil.getColor(R.color.tab_black));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mDeviceFragment != null) {
            transaction.hide(mDeviceFragment);
        }
        if (mMsgFragment != null) {
            transaction.hide(mMsgFragment);
        }
        if (mConfigFragment != null) {
            transaction.hide(mConfigFragment);
        }
    }

    @Override
    protected int getContentViewId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.main_layout;
    }

    @Override
    public void initViews() {
        mDeviceLayout = findViewById(R.id.id_bottom_device_layout);
        mMsgLayout = findViewById(R.id.id_bottom_msg_layout);
        mConfigLayout = findViewById(R.id.id_bottom_config_layout);


        mDeviceImage = (ImageView) findViewById(R.id.id_bottom_bar_image_device);
        mMsgImage = (ImageView) findViewById(R.id.id_bottom_bar_image_msg);
        mConfigImage = (ImageView) findViewById(R.id.id_bottom_bar_image_config);

        mDeviceText = (TextView) findViewById(R.id.id_bottom_bar_text_device);
        mMsgText = (TextView) findViewById(R.id.id_bottom_bar_text_msg);
        mConfigCarText = (TextView) findViewById(R.id.id_bottom_bar_text_config);

        mDeviceLayout.setOnClickListener(this);
        mMsgLayout.setOnClickListener(this);
        mConfigLayout.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }
}
