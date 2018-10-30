package com.politics.exam.business;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.entity.QuestionInfo;
import com.politics.exam.util.ToastManager;
import com.politics.exam.util.Utils;

/**
 * Created by malijie on 2017/8/10.
 */

public class AnswerMethod implements IAnswerMethod{
    private LinearLayout mLayout = null;
    private TextView mTextAnswer = null;
    private TextView mTextOutline = null;
    private TextView mTextDetail = null;

    private QuestionInfo mCurrentInfo = null;

    public AnswerMethod(View view, QuestionInfo questionInfo){
        mLayout = (LinearLayout) view.findViewById(R.id.id_explain_detail_layout);
        mTextAnswer = (TextView) view.findViewById(R.id.id_explain_detail_text_answer);
        mTextOutline = (TextView) view.findViewById(R.id.id_explain_detail_text_outline);
        mTextDetail = (TextView) view.findViewById(R.id.id_explain_detail_text_detail);

        mCurrentInfo = questionInfo;
    }

    public AnswerMethod(Activity activity, QuestionInfo questionInfo){
        mLayout = (LinearLayout) activity.findViewById(R.id.id_explain_detail_layout);
        mTextAnswer = (TextView) activity.findViewById(R.id.id_explain_detail_text_answer);
        mTextOutline = (TextView) activity.findViewById(R.id.id_explain_detail_text_outline);
        mTextDetail = (TextView) activity.findViewById(R.id.id_explain_detail_text_detail);

        mCurrentInfo = questionInfo;
    }


    @Override
    public void showAnswer() {
        mTextAnswer.setText(getContentStyle("【正确答案】" + mCurrentInfo.getAnswer()));
    }

    @Override
    public void showOutlineAnswer() {
        mTextOutline.setText(getContentStyle("【大纲还原】" + mCurrentInfo.getRestore()));
    }

    @Override
    public void showAnswerDetail() {
        mTextDetail.setText(getContentStyle("【答案解析】" + mCurrentInfo.getExplain()));
    }

    @Override
    public void showAnswerUI(boolean isShow) {
        if(isShow){
            mLayout.setVisibility(View.VISIBLE);
            showAnswer();
            showOutlineAnswer();
            showAnswerDetail();
        }
    }

    public SpannableString getContentStyle(String content){
        SpannableString textSpan = new SpannableString (content);

        int start = content.indexOf("【");
        int end = content.indexOf("】");


        textSpan.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.font_red)),
                start,end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textSpan.setSpan(new AbsoluteSizeSpan(40),start,end+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return textSpan;
    }
}
