package com.chinamobile.xiaoyi;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.chinamobile.xiaoyi.http.http.URLContainer;
import com.chinamobile.xiaoyi.util.BitmapUtil;
import com.chinamobile.xiaoyi.util.CommonUtil;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by malijie on 2017/9/7.
 */

public class XiaoYiHelpApplication  extends Application {
    public static Context mContext = null;


    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    private LocRequest locRequest = null;

    private Notification notification = null;



    public SharedPreferences trackConf = null;

    /**
     * 轨迹客户端
     */
    public LBSTraceClient mClient = null;

    /**
     * 轨迹服务
     */
    public Trace mTrace = null;

    /**
     * 轨迹服务ID
     */
    public long serviceId = 149940;

    /**
     * Entity标识
     */
    public String entityName = "myTrace";

    public boolean isRegisterReceiver = false;

    /**
     * 服务是否开启标识
     */
    public boolean isTraceStarted = false;

    /**
     * 采集是否开启标识
     */
    public boolean isGatherStarted = false;

    public static int screenWidth = 0;

    public static int screenHeight = 0;

//    private RefWatcher refWatcher;

    private static boolean debug = true;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SDKInitializer.initialize(mContext);
        BitmapUtil.init();
        setDebugMode(true);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        refWatcher = LeakCanary.install(this);


        //二维码初始化
        ZXingLibrary.initDisplayOpinion(this);

        entityName = CommonUtil.getImei(this);

        // 若为创建独立进程，则不初始化成员变量
        if ("com.baidu.track:remote".equals(CommonUtil.getCurProcessName(mContext))) {
            return;
        }

        mClient = new LBSTraceClient(mContext);
        mTrace = new Trace(serviceId, entityName);
        mTrace.setNotification(notification);

        trackConf = getSharedPreferences("track_conf", MODE_PRIVATE);
        locRequest = new LocRequest(serviceId);

        mClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
            @Override
            public Map<String, String> onTrackAttributeCallback() {
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                return map;
            }
        });

        clearTraceStatus();
    }

    /**
     * 清除Trace状态：初始化app时，判断上次是正常停止服务还是强制杀死进程，根据trackConf中是否有is_trace_started字段进行判断。
     * <p>
     * 停止服务成功后，会将该字段清除；若未清除，表明为非正常停止服务。
     */
    private void clearTraceStatus() {
        if (trackConf.contains("is_trace_started") || trackConf.contains("is_gather_started")) {
            SharedPreferences.Editor editor = trackConf.edit();
            editor.remove("is_trace_started");
            editor.remove("is_gather_started");
            editor.apply();
        }
    }

    /**
     * 设置正式线、测试线开关
     * @param isDebug
     */
    private void setDebugMode(boolean isDebug){
        debug = isDebug;
        URLContainer.setBaseURL(isDebug);
    }

    /**
     * 生产线，测试线切换
     * @return
     */
    public static boolean getDebugMode(){
        return debug;
    }

}