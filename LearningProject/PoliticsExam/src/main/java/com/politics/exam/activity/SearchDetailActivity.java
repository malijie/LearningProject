package com.politics.exam.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.business.AnswerMethod;
import com.politics.exam.business.MultiSelectionMethod;
import com.politics.exam.business.SelectionMethod;
import com.politics.exam.business.SingleSelectionMethod;
import com.politics.exam.db.operator.BaseOperator;
import com.politics.exam.entity.OptionInfo;
import com.politics.exam.entity.QuestionInfo;
import com.politics.exam.util.IntentManager;
import com.politics.exam.util.ToastManager;

import java.util.List;

/**
 * Created by malijie on 2017/8/21.
 */

public class SearchDetailActivity extends BaseActivity {
    private ImageButton mButtonBack = null;
    private Button mButtonCommit = null;
    private QuestionInfo mQuestionInfo = null;
    private List<OptionInfo> mOptionInfos = null;
    private TextView mTextTitle = null;
    private TextView mTextChapter = null;
    private TextView mTextOptionA = null;
    private TextView mTextOptionB = null;
    private TextView mTextOptionC = null;
    private TextView mTextOptionD = null;
    private SelectionMethod mSelectionMethod;
    private AnswerMethod mAnswerMethod;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);
        initData();
        initViews();
    }

    @Override
    public void initData() {
        mQuestionInfo = (QuestionInfo) getIntent().getSerializableExtra("question_info");
        mOptionInfos = mDB.getOptionsByQuestionId(mQuestionInfo.getQuestionId());
    }

    @Override
    public void initViews() {
        mButtonBack = (ImageButton) findViewById(R.id.id_title_bar_button_back);
        mTextTitle = (TextView) findViewById(R.id.id_question_detail_text_title);
        mButtonCommit = (Button) findViewById(R.id.id_question_detail_button_commit);
        mTextChapter = (TextView) findViewById(R.id.id_search_detail_text_character);
        mTextOptionA = (TextView) findViewById(R.id.id_question_detail_text_choiceA);
        mTextOptionB = (TextView) findViewById(R.id.id_question_detail_text_choiceB);
        mTextOptionC = (TextView) findViewById(R.id.id_question_detail_text_choiceC);
        mTextOptionD = (TextView) findViewById(R.id.id_question_detail_text_choiceD);

        mButtonCommit.setVisibility(View.GONE);

        mTextChapter.setText(mDB.getChapterTitleById(mQuestionInfo.getChapterId()));
        mTextTitle.setText(mQuestionInfo.getTitle());
        mTextOptionA.setText(mOptionInfos.get(0).getValue());
        mTextOptionB.setText(mOptionInfos.get(1).getValue());
        mTextOptionC.setText(mOptionInfos.get(2).getValue());
        mTextOptionD.setText(mOptionInfos.get(3).getValue());

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(SearchDetailActivity.this);
            }
        });

        mSelectionMethod = new SelectionMethod();

        View v = LayoutInflater.from(this).inflate(R.layout.search_detail,null);

        if(mQuestionInfo.getType().equals(mSelectionMethod.getMultiSelectionType())){
            //多选
            mTextTitle.setText(getContentStyle (1,mQuestionInfo.getNumber(),mQuestionInfo.getTitle()) + " (多选)");
            mSelectionMethod.setSelectionMethod(new MultiSelectionMethod(v, mQuestionInfo,mOptionInfos));

        }else if(mQuestionInfo.getType().equals(mSelectionMethod.getSingleSelectionType())){
            //单选
            mTextTitle.setText(getContentStyle(1,mQuestionInfo.getNumber(),mQuestionInfo.getTitle()));
            mSelectionMethod.setSelectionMethod(new SingleSelectionMethod(v,mQuestionInfo,mOptionInfos));
        }



        mAnswerMethod = new AnswerMethod(this,mQuestionInfo);
        mAnswerMethod.showAnswerUI(true);

    }
}
