package com.base.lib.base;

import android.app.ProgressDialog;

import com.base.lib.app.ActionCallback;
import com.base.lib.http.DefaultThreadPool;
import com.base.lib.utils.ToastManager;

/**
 * Created by malijie on 2017/8/2.
 */

public abstract class AppBaseActivity extends BaseActivity {
    private static final String TAG = AppBaseActivity.class.getSimpleName();
    public ProgressDialog mProgressDialog = null;
    public DefaultThreadPool mThreadPool =  DefaultThreadPool.getInstance();

    public abstract class RequestCallback<T> implements ActionCallback<T>{
        @Override
        public void onFailed(String errorMsg) {
            mProgressDialog.dismiss();
            ToastManager.showNoNetwork();
        }

        @Override
        public void onSuccess(T data) {

        }
    }

    public abstract void initViews();
    public abstract void initData();
}
