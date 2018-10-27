package com.chinamobile.xiaoyi.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.control.PushManager;
import com.chinamobile.xiaoyi.ui.base.AppBaseActivity;
import com.chinamobile.xiaoyi.ui.fragment.LocationFragment;
import com.chinamobile.xiaoyi.ui.fragment.MineFragment;
import com.chinamobile.xiaoyi.ui.fragment.MsgFragment;
import com.chinamobile.xiaoyi.util.CommonUtil;
import com.chinamobile.xiaoyi.util.Constants;
import com.chinamobile.xiaoyi.util.Logger;
import com.chinamobile.xiaoyi.util.ToastManager;

/**
 * Created by malijie on 2017/9/12.
 */

public class MainActivity extends AppBaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int TAB_LOCATION_INDEX = 0;
    private static final int TAB_MSG_INDEX = 1;
    private static final int TAB_MINE_INDEX = 2;

    private LocationFragment mLocationFragment;
    private MsgFragment mMsgFragment;
    private MineFragment mMineFragment;

    private View mDeviceLayout;
    private View mMsgLayout;
    private View mMineLayout;

    private ImageView mLocationImage;
    private ImageView mMsgImage;
    private ImageView mMineImage;

    private TextView mLocationText;
    private TextView mMsgText;
    private TextView mMineText;

    private FragmentManager fragmentManager;
    private long[] arrayQuit;
    private LogoutReceiver logoutReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化布局元素
        initViews();
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        mDeviceController.refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_bottom_location_layout:
                // 当点击了设备tab
                setTabSelection(TAB_LOCATION_INDEX);

                break;
            case R.id.id_bottom_msg_layout:
                // 当点击了消息tab
                setTabSelection(TAB_MSG_INDEX);
                break;
            case R.id.id_bottom_mine_layout:
                // 当点击了设置tab
                setTabSelection(TAB_MINE_INDEX);

                break;
            default:
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
            case TAB_LOCATION_INDEX:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                mLocationImage.setImageResource(R.mipmap.location_selected);
                mLocationText.setTextColor(CommonUtil.getColor(R.color.title_bar_font_yellow));
                if (mLocationFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mLocationFragment = new LocationFragment();
                    transaction.add(R.id.id_layout_content, mLocationFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mLocationFragment);
                }
                break;
            case TAB_MSG_INDEX:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                mMsgImage.setImageResource(R.mipmap.msg_selected);
                mMsgText.setTextColor(CommonUtil.getColor(R.color.title_bar_font_yellow));
                if (mMsgFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    mMsgFragment = new MsgFragment();
                    transaction.add(R.id.id_layout_content, mMsgFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(mMsgFragment);
                }
                break;
            case TAB_MINE_INDEX:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                mMineImage.setImageResource(R.mipmap.mine_selected);
                mMineText.setTextColor(CommonUtil.getColor(R.color.title_bar_font_yellow));
                if (mMineFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.id_layout_content, mMineFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(mMineFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        mLocationImage.setImageResource(R.mipmap.location_normal);
        mLocationText.setTextColor(CommonUtil.getColor(R.color.bottom_font_grey));

        mMsgImage.setImageResource(R.mipmap.msg_normal);
        mMsgText.setTextColor(CommonUtil.getColor(R.color.bottom_font_grey));

        mMineImage.setImageResource(R.mipmap.mine_normal);
        mMineText.setTextColor(CommonUtil.getColor(R.color.bottom_font_grey));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mLocationFragment != null) {
            transaction.hide(mLocationFragment);
        }
        if (mMsgFragment != null) {
            transaction.hide(mMsgFragment);
        }
        if (mMineFragment != null) {
            transaction.hide(mMineFragment);
        }
    }

    @Override
    protected int getContentViewId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.main_layout;
    }

    @Override
    public void initViews() {
        mDeviceLayout = findViewById(R.id.id_bottom_location_layout);
        mMsgLayout = findViewById(R.id.id_bottom_msg_layout);
        mMineLayout = findViewById(R.id.id_bottom_mine_layout);


        mLocationImage = findViewById(R.id.id_bottom_bar_image_location);
        mMsgImage = findViewById(R.id.id_bottom_bar_image_msg);
        mMineImage = findViewById(R.id.id_bottom_bar_image_config);

        mLocationText = findViewById(R.id.id_bottom_bar_text_location);
        mMsgText = findViewById(R.id.id_bottom_bar_text_msg);
        mMineText = findViewById(R.id.id_bottom_bar_text_mine);

        mDeviceLayout.setOnClickListener(this);
        mMsgLayout.setOnClickListener(this);
        mMineLayout.setOnClickListener(this);
    }

    @Override
    public void initData() {
        arrayQuit = new long[]{0, 0};
        IntentFilter filter = new IntentFilter(Constants.LOGOUT_RECEIVER);
        logoutReceiver = new LogoutReceiver();
        registerReceiver(logoutReceiver, filter);
        PushManager.register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(logoutReceiver);
        unregisterReceiver();
        mDeviceController.clearAllActions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        System.arraycopy(arrayQuit, 0, arrayQuit, 1, 1);
        arrayQuit[0] = System.currentTimeMillis();
        Logger.hyw(arrayQuit[0] + " " + arrayQuit[1]);
        if (arrayQuit[0] - arrayQuit[1] < 2000) {
            super.onBackPressed();
        } else {
            ToastManager.showShortMsg("再按一次退出");
        }
    }



    private class LogoutReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            intent.getAction().equals(Constants.LOGOUT_RECEIVER);
            MainActivity.this.finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mMineFragment.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
