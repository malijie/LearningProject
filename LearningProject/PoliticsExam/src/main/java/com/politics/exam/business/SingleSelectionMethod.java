package com.politics.exam.business;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.politics.exam.R;
import com.politics.exam.entity.OptionInfo;
import com.politics.exam.entity.QuestionInfo;
import com.politics.exam.entity.WrongQuestionInfo;
import com.politics.exam.util.Logger;
import com.politics.exam.util.ToastManager;
import com.politics.exam.util.Utils;

import java.util.List;

/**
 * Created by malijie on 2017/7/27.
 */

public class SingleSelectionMethod extends SelectionMethod implements ISelectionMethod{
    private String mSelectionAnswer;
    private QuestionInfo mQuestionInfo;

    public SingleSelectionMethod(View view , QuestionInfo questionInfo, List<OptionInfo> options) {
        super(view,questionInfo,options);
//        clearOptionsUI();
        mQuestionInfo = questionInfo;
    }

    @Override
    public void choice(String option) {
        mSelectionAnswer = option;
        clearOptionsUI();
        updateSelectionUI(option);

    }


    private void updateSelectionUI(String option){
        switch (option){
            case "A":
                mImageSelectionA.setImageResource(R.mipmap.option_selected);
                break;

            case "B":
                mImageSelectionB.setImageResource(R.mipmap.option_selected);
                break;

            case "C":
                mImageSelectionC.setImageResource(R.mipmap.option_selected);
                break;

            case "D":
                mImageSelectionD.setImageResource(R.mipmap.option_selected);
                break;
        }

    }

    @Override
    public void clearData() {
        mSelectionAnswer = "";
    }

    @Override
    public String getSelectionType() {
        return SINGLE_SELECTION;
    }

    @Override
    public void checkAnswers(String selection) {
        if(TextUtils.isEmpty(selection)){
            ToastManager.showAnswerNotNullMsg();
            return;
        }

        handleSelectionUI(false);

        if(selection.equals(mQuestionInfo.getAnswer())){
            showRightOption(selection);
        }else{
            if(!mOperator.checkIsWrongQuestionExistById(mQuestionInfo.getQuestionId())){
                saveWrongQuestion(createWrongQuestionInfo(mQuestionInfo));
            }
            updateWrongOptionUI(selection);
            showRightOption(mQuestionInfo.getAnswer());
        }

    }




    @Override
    public String getSelection() {
        return mSelectionAnswer;
    }

    private void showRightOption(String answer) {
        switch (answer){
            case "A":
                mImageSelectionA.setImageResource(R.mipmap.answer_right);
                mTextChoiceA.setTextColor(Utils.getColor(R.color.font_green));
                break;

            case "B":
                mImageSelectionB.setImageResource(R.mipmap.answer_right);
                mTextChoiceB.setTextColor(Utils.getColor(R.color.font_green));
                break;

            case "C":
                mImageSelectionC.setImageResource(R.mipmap.answer_right);
                mTextChoiceC.setTextColor(Utils.getColor(R.color.font_green));
                break;

            case "D":
                mImageSelectionD.setImageResource(R.mipmap.answer_right);
                mTextChoiceD.setTextColor(Utils.getColor(R.color.font_green));
                break;
        }
    }

    private void updateWrongOptionUI(String option){
        switch (option){
            case "A":
                mImageSelectionA.setImageResource(R.mipmap.answer_wrong);
                mTextChoiceA.setTextColor(Utils.getColor(R.color.font_red));
                break;

            case "B":
                mImageSelectionB.setImageResource(R.mipmap.answer_wrong);
                mTextChoiceB.setTextColor(Utils.getColor(R.color.font_red));
                break;

            case "C":
                mImageSelectionC.setImageResource(R.mipmap.answer_wrong);
                mTextChoiceC.setTextColor(Utils.getColor(R.color.font_red));
                break;

            case "D":
                mImageSelectionD.setImageResource(R.mipmap.answer_wrong);
                mTextChoiceD.setTextColor(Utils.getColor(R.color.font_red));
                break;
        }
    }

}
