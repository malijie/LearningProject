package com.politics.exam.business;

import android.text.TextUtils;
import android.view.View;

import com.politics.exam.R;
import com.politics.exam.entity.OptionInfo;
import com.politics.exam.entity.QuestionInfo;
import com.politics.exam.util.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malijie on 2017/7/27.
 */

public class MultiSelectionMethod extends SelectionMethod implements ISelectionMethod {
    private List<String> mChoiceMultiAnswers = new ArrayList<>();
    private List<String> mRightAnswers = new ArrayList<>();
    private QuestionInfo mQuestionInfo = null;

    public MultiSelectionMethod(View view, QuestionInfo questionInfo, List<OptionInfo> options) {
        super(view,questionInfo,options);
        mQuestionInfo = questionInfo;
        mRightAnswers = getRightAnswers(questionInfo);
    }

    @Override
    public void choice(String option) {
        handleSelectionAnswer(option);
        updateSelectionUI(option);
    }

    private List<String> getRightAnswers(QuestionInfo questionInfo){
        ArrayList<String> answerList = new ArrayList<>();
        String strAnswers = questionInfo.getAnswer();
        String answers[] = strAnswers.split(",");
        for(int i=0;i<answers.length;i++){
            answerList.add(answers[i]);
        }
        return answerList;
    }

    private void updateSelectionUI(String option) {
        if(mChoiceMultiAnswers.contains(option)){
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
        }else{
            switch (option){
                case "A":
                    mImageSelectionA.setImageResource(R.mipmap.choice_a);
                    break;
                case "B":
                    mImageSelectionB.setImageResource(R.mipmap.choice_b);
                    break;
                case "C":
                    mImageSelectionC.setImageResource(R.mipmap.choice_c);
                    break;
                case "D":
                    mImageSelectionD.setImageResource(R.mipmap.choice_d);
                    break;
            }
        }

    }

    @Override
    public void clearData() {
        mChoiceMultiAnswers.clear();
    }

    @Override
    public String getSelectionType() {
        return MULTI_SELECTION;
    }

    @Override
    public void checkAnswers(String answers) {
        checkMultiAnswers(answers);
    }

    public void checkMultiAnswers(String answers){
        if(mChoiceMultiAnswers.size() == 0 && TextUtils.isEmpty(answers)){
            ToastManager.showAnswerNotNullMsg();
            return;
        }

        handleSelectionUI(!isCompleteQuestion(mQuestionInfo.getQuestionId()));

        if(!TextUtils.isEmpty(answers)){
            String[] strAnswers = answers.split(",");
            for(int i=0;i<strAnswers.length;i++){
                mChoiceMultiAnswers.add(strAnswers[i]);
            }
        }

        handleSelectionUI(false);

        if(isSelectionsRight(mChoiceMultiAnswers)){
            showRightSelectionUI();
        }else{
            if(!mOperator.checkIsWrongQuestionExistById(mQuestionInfo.getQuestionId())){
                saveWrongQuestion(createWrongQuestionInfo(mQuestionInfo));
            }
            showRightSelectionUI();
            showWrongSelectionUI(mChoiceMultiAnswers);
        }
    }

    private void showWrongSelectionUI(List<String> selections) {
        for(int i=0;i<selections.size();i++){
            if(!mRightAnswers.contains(selections.get(i))){
                switch (selections.get(i)){
                    case "A":
                        mImageSelectionA.setImageResource(R.mipmap.answer_wrong);
                        break;

                    case "B":
                        mImageSelectionB.setImageResource(R.mipmap.answer_wrong);
                        break;

                    case "C":
                        mImageSelectionC.setImageResource(R.mipmap.answer_wrong);
                        break;

                    case "D":
                        mImageSelectionD.setImageResource(R.mipmap.answer_wrong);
                        break;
                }
            }
        }
    }

    private void showRightSelectionUI() {
        for(int i=0;i<mRightAnswers.size();i++){
            switch (mRightAnswers.get(i)){
                case "A":
                    mImageSelectionA.setImageResource(R.mipmap.answer_right);
                    break;

                case "B":
                    mImageSelectionB.setImageResource(R.mipmap.answer_right);
                    break;

                case "C":
                    mImageSelectionC.setImageResource(R.mipmap.answer_right);
                    break;

                case "D":
                    mImageSelectionD.setImageResource(R.mipmap.answer_right);
                    break;
            }
        }
    }

    private boolean isSelectionsRight(List<String> selections){
        if(selections.size() != mRightAnswers.size()){
            return false;
        }

        for(int i=0;i<mRightAnswers.size();i++){
            if(!mRightAnswers.contains(selections.get(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getSelection() {
        String selections = "";
        for(String str:mChoiceMultiAnswers){
            selections += str +",";
        }

        if(mChoiceMultiAnswers.size() == 0){
            return "";
        }

        return selections.substring(0,selections.length()-1);
    }


    private List<String> handleSelectionAnswer(String answer){
        if(!mChoiceMultiAnswers.contains(answer)){
            mChoiceMultiAnswers.add(answer);
        }else{
            mChoiceMultiAnswers.remove(answer);
        }
        return mChoiceMultiAnswers;
    }

}
