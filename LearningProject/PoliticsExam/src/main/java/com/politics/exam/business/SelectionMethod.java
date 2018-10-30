package com.politics.exam.business;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.db.operator.BaseOperator;
import com.politics.exam.entity.OptionInfo;
import com.politics.exam.entity.QuestionInfo;
import com.politics.exam.entity.WrongQuestionInfo;
import com.politics.exam.util.Logger;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malijie on 2017/7/27.
 */

public class SelectionMethod implements ISelectionMethod {
    private static final String OPTION_A = "A";
    private static final String OPTION_B = "B";
    private static final String OPTION_C = "C";
    private static final String OPTION_D = "D";
    public  static final String SINGLE_SELECTION = "1";
    public static final String MULTI_SELECTION = "2";
    private Button mButtonCommit;

    protected ImageView mImageSelectionA = null;
    protected ImageView mImageSelectionB = null;
    protected ImageView mImageSelectionC = null;
    protected ImageView mImageSelectionD = null;

    protected TextView mTextChoiceA = null;
    protected TextView mTextChoiceB = null;
    protected TextView mTextChoiceC = null;
    protected TextView mTextChoiceD = null;

    private ISelectionMethod choiceMethod;
    public List<OptionInfo> mOptions = new ArrayList<>();
    private QuestionInfo mQuestionInfo = null;
    protected BaseOperator mOperator = new BaseOperator();

    public SelectionMethod(){

    }

    public SelectionMethod(View view, QuestionInfo questionInfo,List<OptionInfo> options){
        mOptions = options;
        mQuestionInfo = questionInfo;

        mImageSelectionA = (ImageView) view.findViewById(R.id.id_question_detail_image_A);
        mImageSelectionB = (ImageView) view.findViewById(R.id.id_question_detail_image_B);
        mImageSelectionC = (ImageView) view.findViewById(R.id.id_question_detail_image_C);
        mImageSelectionD = (ImageView) view.findViewById(R.id.id_question_detail_image_D);

        mTextChoiceA = (TextView) view.findViewById(R.id.id_question_detail_text_choiceA);
        mTextChoiceB = (TextView) view.findViewById(R.id.id_question_detail_text_choiceB);
        mTextChoiceC = (TextView) view.findViewById(R.id.id_question_detail_text_choiceC);
        mTextChoiceD = (TextView) view.findViewById(R.id.id_question_detail_text_choiceD);

        mButtonCommit = (Button)view.findViewById(R.id.id_question_detail_button_commit);

        mTextChoiceA.setOnClickListener(choiceAOnClickListener);
        mTextChoiceB.setOnClickListener(choiceBOnClickListener);
        mTextChoiceC.setOnClickListener(choiceCOnClickListener);
        mTextChoiceD.setOnClickListener(choiceDOnClickListener);

        mTextChoiceA.setText(mOptions.get(0).getValue());
        mTextChoiceB.setText(mOptions.get(1).getValue());
        mTextChoiceC.setText(mOptions.get(2).getValue());
        mTextChoiceD.setText(mOptions.get(3).getValue());
    }

    public String getMultiSelectionType(){
        return MULTI_SELECTION;
    }

    public String getSingleSelectionType(){
        return SINGLE_SELECTION;
    }

    //选择A
    private View.OnClickListener choiceAOnClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            choice(OPTION_A);
        }
    };

    //选择B
    private View.OnClickListener choiceBOnClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            choice(OPTION_B);
        }
    };

    //选择C
    private View.OnClickListener choiceCOnClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            choice(OPTION_C);
        }
    };

    //选择D
    private View.OnClickListener choiceDOnClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            choice(OPTION_D);
        }
    };


    public void setSelectionMethod(ISelectionMethod choiceMethod){
        this.choiceMethod = choiceMethod;
    }

    @Override
    public void choice(String option) {
        choiceMethod.choice(option);
    }

    @Override
    public void clearData() {
        choiceMethod.clearData();
    }

    @Override
    public String getSelectionType() {
        return choiceMethod.getSelectionType();
    }

    @Override
    public void checkAnswers(String answer) {
        choiceMethod.checkAnswers(answer);
    }

    @Override
    public String getSelection() {
        return choiceMethod.getSelection();
    }

    @Override
    public void saveAnswers(int id,String options) {
        saveHistoryAnswers(id,options);
    }

    private void saveHistoryAnswers(int id,String option) {
        mOperator.saveHistoryAnswer(id,option);
    }

    public boolean isCompleteQuestion(int id){
        return mOperator.isCompleteQuestion(id);
    }

    public void handleSelectionUI(boolean clickable){
        mTextChoiceA.setClickable(clickable);
        mTextChoiceB.setClickable(clickable);
        mTextChoiceC.setClickable(clickable);
        mTextChoiceD.setClickable(clickable);
        mButtonCommit.setClickable(clickable);
    }

    public void clearOptionsUI(){
        mImageSelectionA.setImageResource(R.mipmap.choice_a);
        mImageSelectionB.setImageResource(R.mipmap.choice_b);
        mImageSelectionC.setImageResource(R.mipmap.choice_c);
        mImageSelectionD.setImageResource(R.mipmap.choice_d);
    }

    public void saveWrongQuestion(WrongQuestionInfo wrongQuestionInfo){
        mOperator.saveWrongQuestion(wrongQuestionInfo);
    }

    /**
     * 创建错题实例
     * @param questionInfo
     * @return
     */
    public WrongQuestionInfo createWrongQuestionInfo(QuestionInfo questionInfo){
        WrongQuestionInfo wrongQuestionInfo = new WrongQuestionInfo();
        wrongQuestionInfo.setQuestionId(questionInfo.getQuestionId());
        wrongQuestionInfo.setChapter(mOperator.getChapterTitleById(questionInfo.getChapterId()));
        wrongQuestionInfo.setTitle(questionInfo.getTitle());

        wrongQuestionInfo.setOptionA(mOptions.get(0).getValue());
        wrongQuestionInfo.setOptionB(mOptions.get(1).getValue());
        wrongQuestionInfo.setOptionC(mOptions.get(2).getValue());
        wrongQuestionInfo.setOptionD(mOptions.get(3).getValue());

        wrongQuestionInfo.setAnswer(questionInfo.getAnswer());
        wrongQuestionInfo.setExplain(questionInfo.getExplain());
        wrongQuestionInfo.setType(questionInfo.getType());
        wrongQuestionInfo.setRestore(questionInfo.getRestore());
        return wrongQuestionInfo;
    }

}
