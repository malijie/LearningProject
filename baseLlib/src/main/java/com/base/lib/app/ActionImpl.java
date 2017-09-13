package com.base.lib.app;

import android.os.AsyncTask;

import com.base.lib.api.Api;
import com.base.lib.api.ApiImpl;
import com.base.lib.entity.ApiResponse;
import com.base.lib.entity.WelfareInfo;
import com.base.lib.http.DefaultThreadPool;
import com.base.lib.http.RequestParams;
import com.base.lib.utils.Logger;
import com.base.lib.utils.ToastManager;
import com.base.lib.utils.Util;

import java.util.Date;
import java.util.List;

/**
 * Created by malijie on 2017/7/31.
 */

public class ActionImpl implements AppAction{
    private Api api = null;
    private DefaultThreadPool mThreadPool = null;

    public ActionImpl(){
        api = new ApiImpl();
        mThreadPool = DefaultThreadPool.getInstance();
    }

    @Override
    public void listWelfareInfo(final RequestParams requestParams, final ActionCallback<List<WelfareInfo>> callback) {
        if(!requestParams.isValidate()){
            ToastManager.showShortMsg("参数错误");
            return;
        }

        if(!Util.hasInternet()){
            callback.onFailed("当前无网络");
            return;
        }

        mThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                new AsyncTask<Void, Void, ApiResponse<List<WelfareInfo>>>() {
                    @Override
                    protected ApiResponse<List<WelfareInfo>> doInBackground(Void... params) {
                        return api.getWelfareInfo(requestParams);
                    }
                    @Override
                    protected void onPostExecute(ApiResponse<List<WelfareInfo>> listApiResponse) {
                        if (!listApiResponse.hasError()) {
                            callback.onSuccess(listApiResponse.getResults());
                        } else {
                            callback.onFailed("数据返回失败");
                        }
                    }
                }.execute();
            }
        });

    }
}
