package com.politics.exam.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.politics.exam.R;

/**
 * Created by malijie on 2017/8/30.
 */

public class AboutActivity extends BaseActivity{
    private TextView mTextAbout = null;
    private ImageButton mButtonBack = null;
    private TextView mTextTitle = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        initData();
        initViews();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        String content =
                "<p>1. 本题库为整理了历年考研政治题库合集</p>" +
                "<p>2. app包含了近5年真题，以备考生随时检测，练习！</p>" +
                "<p>3. 题库参考最新考试大纲，为考生提供精确辅导。</p>" +
                "<p>4. 时政部分由于时效性问题，会在考前1个月前公布。</p>" +
                "<p>5. 联系邮箱：190223629@qq.com</p>";;
        mTextAbout = (TextView) findViewById(R.id.id_about_text_content);
        mButtonBack = (ImageButton) findViewById(R.id.id_title_bar_button_back);
        mTextTitle = (TextView) findViewById(R.id.id_title_bar_text_title);

        mTextAbout.setText(Html.fromHtml(content));
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(AboutActivity.this);
            }
        });
        mTextTitle.setText("关于我们");
    }
}
