package com.chinamobile.xiaoyi.util;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by malijie on 2017/9/11.
 */

public class MapUtil {
    private static LatLng sLatlng = null;

    public static LatLng getCurrentLatLng(BDLocation location){
        return new LatLng(location.getLatitude(),location.getLongitude());
    }

    public static double getCurrentLatitude(BDLocation location){
        return getCurrentLatLng(location).latitude;
    }

    public static double getCurrentLongitude(BDLocation location){
        return getCurrentLatLng(location).longitude;
    }

}
