package com.politics.exam.util;

import android.widget.Toast;

import com.politics.exam.R;


/**
 * Created by Administrator on 2017/2/22.
 */

public class ToastManager {
    public static void showLongMsg(String msg){
        ToastUtil.showMsg(msg, Toast.LENGTH_LONG);
    }

    public static void showShortMsg(String msg){
        ToastUtil.showMsg(msg, Toast.LENGTH_SHORT);
    }

    public static void showAnswerNotNullMsg(){
        ToastUtil.showMsg(Utils.getString(R.string.answer_not_null), Toast.LENGTH_SHORT);
    }

    public static void showExitTipMsg(){
        ToastUtil.showMsg(Utils.getString(R.string.exit_tip), Toast.LENGTH_SHORT);
    }

}
