package com.common.lib.util;

import android.content.Context;

import com.common.lib.base.CommonBase;


public class SharedPreferenceUtil {
	private static final String SP_QUESTION_PROGRESS = "question_progress";
	private static final String SP_QUESTION_PROGRESS_KEY = "item";
	private static final String SP_QUESTION_INIT = "init";
	private static final String SP_PAY_INFO = "pay_info";
	private static final String PAYED_VIP_KEY = "payed_vip";
	private static final String PAYED_VIDEO_KEY = "payed_video";

	private static final String VIDEO_INFO = "VIDEO_INFO";
	private static final String KEY_PLAY_PROCESS = "KEY_PLAY_PROCESS";


	/**
	 * 保存课程学习进度
	 * @param progress
	 */
	public static void saveProgress(int groupId,int childId,int progress){
		CommonBase.mContext.getSharedPreferences(SP_QUESTION_PROGRESS, Context.MODE_PRIVATE).edit()
				.putInt(SP_QUESTION_PROGRESS_KEY + groupId + childId,progress).commit();
	}

	/**
	 * 读取课程进度
	 * @return
	 */
	public static int loadProgress(int groupId,int childId){
		return CommonBase.mContext.getSharedPreferences(SP_QUESTION_PROGRESS, Context.MODE_PRIVATE)
				.getInt(SP_QUESTION_PROGRESS_KEY + groupId + childId,0);
	}

	public static void saveFirstInit(boolean init) {
		CommonBase.mContext.getSharedPreferences(SP_QUESTION_PROGRESS, Context.MODE_PRIVATE).edit()
				.putBoolean(SP_QUESTION_INIT,init).commit();
	}

	public static boolean loadFirstInit(){
		return CommonBase.mContext.getSharedPreferences(SP_QUESTION_PROGRESS, Context.MODE_PRIVATE)
				.getBoolean(SP_QUESTION_INIT,true);
	}

	public static void savePayedQuestionStatus(boolean isPayed){
		CommonBase.mContext.getSharedPreferences(SP_PAY_INFO, Context.MODE_PRIVATE).edit()
				.putBoolean(PAYED_VIP_KEY,isPayed).commit();
	}

	public static boolean loadPayedQuestionStatus(){
		return CommonBase.mContext.getSharedPreferences(SP_PAY_INFO, Context.MODE_PRIVATE)
				.getBoolean(PAYED_VIP_KEY,false);
	}

	public static void savePayedVideoStatus(boolean isPayed){
		CommonBase.mContext.getSharedPreferences(SP_PAY_INFO, Context.MODE_PRIVATE).edit()
				.putBoolean(PAYED_VIDEO_KEY,isPayed).commit();
	}

	public static boolean loadPayedVideoStatus(){
		return CommonBase.mContext.getSharedPreferences(SP_PAY_INFO, Context.MODE_PRIVATE)
				.getBoolean(PAYED_VIDEO_KEY,false);
	}

	public static void saveStringVideoInfo(String key,String value){
		CommonBase.mContext.getSharedPreferences(VIDEO_INFO, Context.MODE_PRIVATE).edit()
				.putString(key,value).commit();

	}

	public static String getStringVideoInfo(String key){
		return CommonBase.mContext.getSharedPreferences(VIDEO_INFO, Context.MODE_PRIVATE)
				.getString(key,"");
	}





}
