package com.chinamobile.xiaoyi.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.baidu.mapapi.model.LatLng;
import com.chinamobile.xiaoyi.XiaoYiHelpApplication;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by baidu on 17/1/23.
 */

public class CommonUtil {

    private static DecimalFormat df = new DecimalFormat("######0.00");

    public static final double DISTANCE = 0.0001;

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    /**
     * 获取当前时间戳(单位：秒)
     *
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 校验double数值是否为0
     *
     * @param value
     * @return
     */
    public static boolean isEqualToZero(double value) {
        return Math.abs(value - 0.0) < 0.01 ? true : false;
    }

    /**
     * 经纬度是否为(0,0)点
     *
     * @return
     */
    public static boolean isZeroPoint(double latitude, double longitude) {
        return isEqualToZero(latitude) && isEqualToZero(longitude);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long toTimeStamp(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return date.getTime() / 1000;
    }

    /**
     * 获取时分秒
     *
     * @param timestamp 时间戳（单位：毫秒）
     * @return
     */
    public static String getHMS(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            return sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }

    /**
     * 获取年月日 时分秒
     *
     * @param strTime 时间戳（单位：毫秒）
     * @return
     */
    public static String formatTime(String strTime) {
        long timestamp = Long.parseLong(strTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }

    public static String formatSecond(int second) {
        String format = "%1$,02d:%2$,02d:%3$,02d";
        Integer hours = second / (60 * 60);
        Integer minutes = second / 60 - hours * 60;
        Integer seconds = second - minutes * 60 - hours * 60 * 60;
        Object[] array = new Object[]{hours, minutes, seconds};
        return String.format(format, array);
    }

    public static final String formatDouble(double doubleValue) {
        return df.format(doubleValue);
    }

    /**
     * 计算x方向每次移动的距离
     */
    public static double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }

    /**
     * 根据点和斜率算取截距
     */
    public static double getInterception(double slope, LatLng point) {
        return point.latitude - slope * point.longitude;
    }

    /**
     * 算斜率
     */
    public static double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        return (toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude);
    }

    /**
     * 根据两点算取图标转的角度
     */
    public static double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 0;
            } else {
                return 180;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 180;
        }
        double radio = Math.atan(slope);
        return 180 * (radio / Math.PI) + deltAngle - 90;
    }


    /**
     * 获取设备IMEI码
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        String imei;
        try {
            imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            imei = "myTrace";
        }
        return imei;
    }

    public static int getColor(int res) {
        return XiaoYiHelpApplication.mContext.getResources().getColor(res);
    }

    public static Drawable getDrawable(int res) {
        return ContextCompat.getDrawable(XiaoYiHelpApplication.mContext, res);
    }

    public static boolean hasInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) XiaoYiHelpApplication.mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    public static String getResString(int resId) {
        return XiaoYiHelpApplication.mContext.getResources().getString(resId);
    }

    public static View getView(int resId) {
        return LayoutInflater.from(XiaoYiHelpApplication.mContext).inflate(resId, null);
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static int getCurrentYear() {
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    }

    public static int getCurrentMonth() {
        return Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
    }

    public static int getCurrentDay() {
        return Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
    }


    public static String getCurrentHHmm() {
        return new SimpleDateFormat("hh:mm").format(new Date());
    }

    public static String getCurrentHour() {
        return String.valueOf(new Date().getHours());
//        return new SimpleDateFormat("hh").format(new Date());
    }

    public static String getCurrentMinute() {
        return String.valueOf(new Date().getMinutes());
    }

    public static String getCurrentTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    public static ProgressDialog createProgressDialog(Activity activity, String msg) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(msg);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static int getViewHeight(View v) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        return v.getMeasuredHeight();
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) XiaoYiHelpApplication.mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();

    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) XiaoYiHelpApplication.mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static String getBase64ImageString(String path) {
        FileInputStream fis = null;
        String uploadBuffer = null;
        try {
            fis = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);
            }
            uploadBuffer = new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT));  //进行Base64编码
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadBuffer;
    }

    /**
     * 获取手机厂商版本
     * @return
     */
    public static String getMobileVersion(){
       return android.os.Build.MODEL;
    }

}
