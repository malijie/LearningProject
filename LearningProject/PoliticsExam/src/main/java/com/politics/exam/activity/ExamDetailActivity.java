package com.politics.exam.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.db.operator.BaseOperator;
import com.politics.exam.util.IntentManager;

/**
 * Created by malijie on 2017/8/25.
 */

public class ExamDetailActivity extends BaseActivity{

    private TextView mText = null;
    private TextView mTextTitle = null;
    private ImageButton mButtonBack = null;
    private String mYear = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_detail_layout);
        initData();
        initViews();
    }

    @Override
    public void initData() {
        mYear = getIntent().getStringExtra("year");
    }

    @Override
    public void initViews() {
        mText = (TextView) findViewById(R.id.id_exam_content);
        mTextTitle = (TextView) findViewById(R.id.id_title_bar_text_title);
        mButtonBack = (ImageButton) findViewById(R.id.id_title_bar_button_back);
        mText.setText(Html.fromHtml(new BaseOperator().getExamContentByYear(mYear)));
        mTextTitle.setText(mYear + "年全国考研政治真题");

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(ExamDetailActivity.this);
            }
        });
    }
}
