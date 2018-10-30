package com.politics.exam.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.db.operator.BaseOperator;
import com.politics.exam.db.operator.IDBOperator;
import com.politics.exam.util.IntentManager;
import com.politics.exam.util.SharedPreferenceUtil;
import com.politics.exam.util.Utils;

import java.util.List;

/**
 * Created by malijie on 2017/5/25.
 */

public abstract class BaseActivity extends Activity{
    private ImageButton mButtonBack = null;
    public BaseOperator mDB =  new BaseOperator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public int getResColor(int resId){
        return Utils.getColor(resId);
    }

    public abstract void initData();
    public abstract void initViews();


    public SpannableString getContentStyle(int questionNo, String textFrom, String textTitle){
        String content= questionNo  + "." + textFrom + textTitle;
        SpannableString textSpan = new SpannableString (content);

        int start = content.indexOf("（");
        int end = content.indexOf("）");
        if(start<=0 || end<=0){
            return textSpan;
        }


        textSpan.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.font_yellow)),
                start,end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textSpan.setSpan(new AbsoluteSizeSpan(40),start,end+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return textSpan;
    }

    public void saveLastProgress(int groupId,int itemId,int position){
        SharedPreferenceUtil.saveProgress(groupId,itemId,position);
    }

    public void finish(Activity activity){
        IntentManager.finishActivity(activity);
    }


}
