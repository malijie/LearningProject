package com.base.lib.http;

import android.os.AsyncTask;

import com.base.lib.api.Api;
import com.base.lib.app.ActionCallback;
import com.base.lib.entity.ApiResponse;

/**
 * Created by malijie on 2017/8/3.
 */

public class  HttpAsyncTask<T> extends AsyncTask<RequestParams,Void,ApiResponse<T>> {
    private Api api = null;
    private ActionCallback callback;

    public HttpAsyncTask(Api api, ActionCallback callback){
        this.api= api;
        this.callback = callback;
    }

    @Override
    protected ApiResponse<T> doInBackground(RequestParams... params) {
        return (ApiResponse<T>) api.getWelfareInfo(params[0]);
    }

    @Override
    protected void onPostExecute(ApiResponse<T> apiResponse) {
        super.onPostExecute(apiResponse);
        if (!apiResponse.hasError()) {
            callback.onSuccess(apiResponse.getResults());
        } else {
            callback.onFailed("数据返回失败");
        }
    }
}
