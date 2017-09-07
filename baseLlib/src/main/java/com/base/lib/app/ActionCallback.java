package com.base.lib.app;

/**
 * Created by malijie on 2017/7/31.
 */

public interface ActionCallback<T> {
    void onSuccess(T data);
    void onFailed(String errorMsg);
}
