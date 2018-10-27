package com.chinamobile.xiaoyi.ui.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.xiaoyi.R;
import com.chinamobile.xiaoyi.XiaoYiHelpApplication;
import com.chinamobile.xiaoyi.http.app.ActionCallback;
import com.chinamobile.xiaoyi.http.app.ActionImpl;
import com.chinamobile.xiaoyi.http.app.AppAction;
import com.chinamobile.xiaoyi.http.http.DefaultThreadPool;
import com.chinamobile.xiaoyi.control.DeviceController;
import com.chinamobile.xiaoyi.util.SharedPreferenceUtil;
import com.chinamobile.xiaoyi.util.ToastManager;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by malijie on 2017/9/12.
 */

public abstract class  BaseFragment extends Fragment{
    private ImageView mButtonBack =  null;
    private ImageView mButtonAddDevice = null;
    private LinearLayout mLayoutDevice = null;
    private TextView mTextTitle = null;
    private TextView mTextOption = null;

    public DeviceController mDeviceController = null;
    public AppAction action = null;
    public DefaultThreadPool mThreadPool =  DefaultThreadPool.getInstance();
    public ProgressDialog mProgressDialog = null;

    public BaseFragment(){
        init();
    }

    public View onCreateView(View view) {

        mButtonBack = view.findViewById(R.id.id_title_bar_button_back);
        mLayoutDevice = view.findViewById(R.id.id_title_bar_layout_device);
        mTextTitle = view.findViewById(R.id.id_title_bar_text_title);
        mTextOption = view.findViewById(R.id.id_title_bar_text_option);
        mButtonAddDevice = view.findViewById(R.id.id_title_bar_button_add_device);

        return view;
    }



    public void init() {
        mDeviceController = DeviceController.getInstance();
        action = new ActionImpl();
    }

    public abstract class RequestCallback<T> implements ActionCallback<T> {
        @Override
        public void onFailed(String errorMsg) {
            if(mProgressDialog!=null){
                mProgressDialog.dismiss();
            }
            ToastManager.showShortMsg(errorMsg);
        }

        @Override
        public void onSuccess(T data) {

        }
    }

    public void showBackButton(){
        mButtonBack.setVisibility(View.VISIBLE);
    }

    public void hideBackButton(){
        mButtonBack.setVisibility(View.GONE);
    }

    public void showAddButton(){
        mButtonAddDevice.setVisibility(View.VISIBLE);
    }

    public void hideAddButton(){
        mButtonAddDevice.setVisibility(View.GONE);
    }

    public void showSubTitleLayout(){
        mLayoutDevice.setVisibility(View.VISIBLE);
    }

    public void hideSubTitleLayout(){
        mLayoutDevice.setVisibility(View.GONE);

    }

    public void showTitle(){
        mTextTitle.setVisibility(View.VISIBLE);
    }

    public void hideOption(){
        mTextOption.setVisibility(View.GONE);
    }

    public void showOption(){
        mTextOption.setVisibility(View.VISIBLE);
    }

    public void hideTitle(){
        mTextTitle.setVisibility(View.GONE);
    }

    public void setTitle(String title){
        mTextTitle.setText(title);
    }

    public void setBtnAddDeviceListener(View.OnClickListener listener){
        mButtonAddDevice.setOnClickListener(listener);
    }


    public abstract void initViews(View v);
    public abstract void initData();

    public String getUserPhone(){
        return SharedPreferenceUtil.loadPhone();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = XiaoYiHelpApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }
}
