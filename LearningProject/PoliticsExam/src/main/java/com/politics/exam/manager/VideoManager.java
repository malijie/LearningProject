package com.politics.exam.manager;

import com.politics.exam.entity.VideoInfo;
import com.politics.exam.util.SharedPreferenceUtil;
import com.politics.exam.util.SpKey;

/**
 * Created by malijie on 2018/10/30.
 */

public class VideoManager {

    /**
     * 保存当前视频信息
     * @param videoInfo
     */
    public static void saveCurrentInfo(VideoInfo videoInfo){
        SharedPreferenceUtil.saveStringVideoInfo(SpKey.VIDEO_NAME,videoInfo.getName());
        SharedPreferenceUtil.saveStringVideoInfo(SpKey.VIDEO_URL,videoInfo.getUrl());

    }

    public static String getCurrentName(){
        return SharedPreferenceUtil.getStringVideoInfo(SpKey.VIDEO_NAME);
    }

    public static String getCurrentUrl(){
        return SharedPreferenceUtil.getStringVideoInfo(SpKey.VIDEO_URL);
    }

}
