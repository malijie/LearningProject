package com.base.lib.api;

import com.base.lib.entity.ApiResponse;
import com.base.lib.entity.WelfareInfo;
import com.base.lib.http.HttpEngine;
import com.base.lib.http.RequestParams;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by malijie on 2017/7/31.
 */

public class ApiImpl implements Api{

    @Override
    public ApiResponse<List<WelfareInfo>> getWelfareInfo(RequestParams params) {
        Type type = new TypeToken<ApiResponse<List<WelfareInfo>>>(){}.getType();
        try {
            return HttpEngine.getInstance().postHandle(params,type);
        } catch (IOException e) {
            return new ApiResponse(false, "参数错误");
        }
    }
}
