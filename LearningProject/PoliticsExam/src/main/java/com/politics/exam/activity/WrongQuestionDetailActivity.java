package com.politics.exam.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.business.IAnswerMethod;
import com.politics.exam.business.SelectionMethod;
import com.politics.exam.entity.WrongQuestionInfo;
import com.politics.exam.util.ToastManager;
import com.politics.exam.util.Utils;
import com.politics.exam.widget.CustomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malijie on 2018/10/05.
 */

public class WrongQuestionDetailActivity extends BaseActivity implements IAnswerMethod{
    public static final String SELECT_TYPE_SINGLE = "1";
    public static final String SELECT_TYPE_MULTI = "2";

    private TextView mTextChapter = null;
    private TextView mTextTitle = null;
    private TextView mTextOptionA = null;
    private TextView mTextOptionB = null;
    private TextView mTextOptionC = null;
    private TextView mTextOptionD = null;
    private TextView mTextSubject = null;
    private ImageView mImageSelectionA;
    private ImageView mImageSelectionB;
    private ImageView mImageSelectionC;
    private ImageView mImageSelectionD;
    private ImageButton mBtnBack = null;
    private Button mBtnNext = null;
    private ImageButton mBtnDelete = null;
    private LinearLayout mLayoutAnswer = null;
    private TextView mTextAnswer = null;
    private TextView mTextExplain = null;
    private TextView mTextOutline = null;

    private SelectionMethod mSelectionMethod = null;
    private WrongQuestionInfo wrongQuestionInfo = null;
    private List<WrongQuestionInfo> wrongQuestionInfos;
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrong_question_detail);

        initData();
        initViews();
        updateContent();

    }

    private void updateContent() {
        mTextChapter.setText(wrongQuestionInfo.getChapter());
        mTextTitle.setText(wrongQuestionInfo.getQuestionId() + ". " +wrongQuestionInfo.getTitle());
        mTextOptionA.setText(wrongQuestionInfo.getOptionA());
        mTextOptionB.setText(wrongQuestionInfo.getOptionB());
        mTextOptionC.setText(wrongQuestionInfo.getOptionC());
        mTextOptionD.setText(wrongQuestionInfo.getOptionD());

        showAnswerUI(true);

        if (wrongQuestionInfo.getType().equals(SELECT_TYPE_SINGLE)) {
            String answer = wrongQuestionInfo.getAnswer();
            selectRightAnswer(answer);
        } else {
            List<String> rightAnswers = getRightAnswers();
            for (int i = 0; i < rightAnswers.size(); i++) {
                switch (rightAnswers.get(i)){
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

    }

    private List<String> getRightAnswers() {
        ArrayList<String> answerList = new ArrayList<>();
        String strAnswers = wrongQuestionInfo.getAnswer();
        String answers[] = strAnswers.split(",");
        for (int i = 0; i < answers.length; i++) {
            answerList.add(answers[i]);
        }
        return answerList;
    }


    private void selectRightAnswer(String answer) {
        switch (answer) {
            case "A":
                mImageSelectionA.setImageResource(R.mipmap.answer_right);
                mImageSelectionB.setImageResource(R.mipmap.choice_b);
                mImageSelectionC.setImageResource(R.mipmap.choice_c);
                mImageSelectionD.setImageResource(R.mipmap.choice_d);

                break;
            case "B":
                mImageSelectionA.setImageResource(R.mipmap.choice_a);
                mImageSelectionB.setImageResource(R.mipmap.answer_right);
                mImageSelectionC.setImageResource(R.mipmap.choice_c);
                mImageSelectionD.setImageResource(R.mipmap.choice_d);
                break;
            case "C":
                mImageSelectionA.setImageResource(R.mipmap.choice_a);
                mImageSelectionB.setImageResource(R.mipmap.choice_b);
                mImageSelectionC.setImageResource(R.mipmap.answer_right);
                mImageSelectionD.setImageResource(R.mipmap.choice_d);
                break;
            case "D":
                mImageSelectionA.setImageResource(R.mipmap.choice_a);
                mImageSelectionB.setImageResource(R.mipmap.choice_b);
                mImageSelectionC.setImageResource(R.mipmap.choice_c);
                mImageSelectionD.setImageResource(R.mipmap.answer_right);
                break;
        }
    }

    @Override
    public void initData() {
        wrongQuestionInfos = (List<WrongQuestionInfo>) getIntent().getSerializableExtra("wrong_questions");
        index = getIntent().getIntExtra("position",0);
        wrongQuestionInfo = wrongQuestionInfos.get(index);
    }

    @Override
    public void initViews() {
        mTextChapter = (TextView) findViewById(R.id.id_question_detail_text_character);
        mTextTitle = (TextView) findViewById(R.id.id_question_detail_text_title);
        mTextOptionA = (TextView) findViewById(R.id.id_question_detail_text_choiceA);
        mTextOptionB = (TextView) findViewById(R.id.id_question_detail_text_choiceB);
        mTextOptionC = (TextView) findViewById(R.id.id_question_detail_text_choiceC);
        mTextOptionD = (TextView) findViewById(R.id.id_question_detail_text_choiceD);
        mTextSubject = (TextView) findViewById(R.id.id_title_bar_text_title);
        mBtnBack = (ImageButton) findViewById(R.id.id_title_bar_button_back);
        mBtnDelete = (ImageButton) findViewById(R.id.id_title_bar_button_delete);
        mLayoutAnswer = (LinearLayout) findViewById(R.id.id_explain_detail_layout);

        mImageSelectionA = (ImageView) findViewById(R.id.id_question_detail_image_A);
        mImageSelectionB = (ImageView) findViewById(R.id.id_question_detail_image_B);
        mImageSelectionC = (ImageView) findViewById(R.id.id_question_detail_image_C);
        mImageSelectionD = (ImageView) findViewById(R.id.id_question_detail_image_D);
        mTextAnswer = (TextView) findViewById(R.id.id_explain_detail_text_answer);
        mTextExplain = (TextView) findViewById(R.id.id_explain_detail_text_detail);
        mTextOutline = (TextView) findViewById(R.id.id_explain_detail_text_outline);

        mBtnDelete.setVisibility(View.VISIBLE);
        mBtnNext = (Button) findViewById(R.id.id_question_detail_button_next);
        mTextSubject.setText("我的错题");



        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext();
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(WrongQuestionDetailActivity.this);
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialog dialog = new CustomDialog(WrongQuestionDetailActivity.this,
                        "删除？","骚年，确定要删除此错题吗？");
                dialog.setButtonClickListener(new CustomDialog.DialogButtonListener() {
                    @Override
                    public void onConfirm() {
                        dialog.dissmiss();
                        deleteQuestion();
                        showNext();
                        ToastManager.showShortMsg("删除成功");
                    }

                    @Override
                    public void onCancel() {
                        dialog.dissmiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private void deleteQuestion(){
        mDB.deleteWrongQuestionById(wrongQuestionInfo.getQuestionId());
    }

    private void showNext(){
        if(index == wrongQuestionInfos.size()-1){
            ToastManager.showShortMsg("已是最后一题");
            return;
        }

        wrongQuestionInfo = wrongQuestionInfos.get(++index);
        updateContent();
    }

    @Override
    public void showAnswer() {
        mTextAnswer.setText(getContentStyle("【正确答案】" + wrongQuestionInfo.getAnswer()));
    }

    @Override
    public void showOutlineAnswer() {
        mTextOutline.setText(getContentStyle("【大纲还原】" + wrongQuestionInfo.getRestore()));
    }

    @Override
    public void showAnswerDetail() {
        mTextExplain.setText(getContentStyle("【答案解析】" + wrongQuestionInfo.getExplain()));
    }

    @Override
    public void showAnswerUI(boolean isShow) {
        mLayoutAnswer.setVisibility(View.VISIBLE);
        showAnswer();
        showOutlineAnswer();
        showAnswerDetail();
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
