package com.politics.exam.util;

import android.content.Context;

import com.politics.exam.PoliticsApplication;

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
		PoliticsApplication.sContext.getSharedPreferences(SP_QUESTION_PROGRESS, Context.MODE_PRIVATE).edit()
				.putInt(SP_QUESTION_PROGRESS_KEY + groupId + childId,progress).commit();
	}

	/**
	 * 读取课程进度
	 * @return
	 */
	public static int loadProgress(int groupId,int childId){
		return PoliticsApplication.sContext.getSharedPreferences(SP_QUESTION_PROGRESS, Context.MODE_PRIVATE)
				.getInt(SP_QUESTION_PROGRESS_KEY + groupId + childId,0);
	}

	public static void saveFirstInit(boolean init) {
		PoliticsApplication.sContext.getSharedPreferences(SP_QUESTION_PROGRESS, Context.MODE_PRIVATE).edit()
				.putBoolean(SP_QUESTION_INIT,init).commit();
	}

	public static boolean loadFirstInit(){
		return PoliticsApplication.sContext.getSharedPreferences(SP_QUESTION_PROGRESS, Context.MODE_PRIVATE)
				.getBoolean(SP_QUESTION_INIT,true);
	}

	public static void savePayedQuestionStatus(boolean isPayed){
		PoliticsApplication.sContext.getSharedPreferences(SP_PAY_INFO, Context.MODE_PRIVATE).edit()
				.putBoolean(PAYED_VIP_KEY,isPayed).commit();
	}

	public static boolean loadPayedQuestionStatus(){
		return PoliticsApplication.sContext.getSharedPreferences(SP_PAY_INFO, Context.MODE_PRIVATE)
				.getBoolean(PAYED_VIP_KEY,false);
	}

	public static void savePayedVideoStatus(boolean isPayed){
		PoliticsApplication.sContext.getSharedPreferences(SP_PAY_INFO, Context.MODE_PRIVATE).edit()
				.putBoolean(PAYED_VIDEO_KEY,isPayed).commit();
	}

	public static boolean loadPayedVideoStatus(){
		return PoliticsApplication.sContext.getSharedPreferences(SP_PAY_INFO, Context.MODE_PRIVATE)
				.getBoolean(PAYED_VIDEO_KEY,false);
	}

	public static void saveStringVideoInfo(String key,String value){
		PoliticsApplication.sContext.getSharedPreferences(VIDEO_INFO, Context.MODE_PRIVATE).edit()
				.putString(key,value).commit();

	}

	public static String getStringVideoInfo(String key){
		return PoliticsApplication.sContext.getSharedPreferences(VIDEO_INFO, Context.MODE_PRIVATE)
				.getString(key,"");
	}





}
