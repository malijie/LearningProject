package com.base.lib.api;

import com.base.lib.entity.ApiResponse;
import com.base.lib.entity.WelfareInfo;
import com.base.lib.http.RequestParams;

import java.util.List;

/**
 * Created by malijie on 2017/7/31.
 */

public interface Api {
    ApiResponse<List<WelfareInfo>> getWelfareInfo(RequestParams params);
}
