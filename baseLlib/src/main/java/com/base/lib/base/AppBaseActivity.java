package com.base.lib.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.lib.app.ActionCallback;
import com.base.lib.http.DefaultThreadPool;
import com.base.lib.utils.ToastManager;

/**
 * Created by malijie on 2017/8/2.
 */

public abstract class AppBaseActivity extends BaseActivity {
    public static final String TITLE_ADD_DEVICE = "添加设备";
    public static final String TITLE_REGISTER = "注册会员";
    public static final String TITLE_FORGET_PWD = "忘记密码";


    private static final String TAG = AppBaseActivity.class.getSimpleName();
    public ProgressDialog mProgressDialog = null;
    public DefaultThreadPool mThreadPool =  DefaultThreadPool.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
    }

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

    /**
     * 获取布局文件ID
     *
     * @return 布局文件ID
     */
    protected abstract int getContentViewId();

    public abstract void initViews();
    public abstract void initData();

    public void finish(Activity activity){
        activity.finish();
    }
}
