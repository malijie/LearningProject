package com.politics.exam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.business.AnswerMethod;
import com.politics.exam.business.MultiSelectionMethod;
import com.politics.exam.business.SelectionMethod;
import com.politics.exam.business.SingleSelectionMethod;
import com.politics.exam.db.operator.BaseOperator;
import com.politics.exam.db.operator.ChapterMYDBOperator;
import com.politics.exam.db.operator.ChapterMZTDBOperator;
import com.politics.exam.db.operator.ChapterSGDBOperator;
import com.politics.exam.db.operator.ChapterSXYFJDBOperator;
import com.politics.exam.db.operator.ChapterSZDBOperator;
import com.politics.exam.db.operator.IDBOperator;
import com.politics.exam.entity.OptionInfo;
import com.politics.exam.entity.QuestionInfo;
import com.politics.exam.util.IntentManager;
import com.politics.exam.util.Logger;
import com.politics.exam.util.SharedPreferenceUtil;
import com.politics.exam.util.ToastManager;
import com.politics.exam.util.Utils;
import com.politics.exam.widget.CustomDialog;
import com.politics.exam.widget.DepthPageTransformer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by malijie on 2017/5/27.
 */

public class QuestionDetailActivity extends BaseActivity{
    private static final int MSG_SAVE_PROGRESS = 0X0001;

    private ViewPager mViewPager = null;
    private List<View> mViews = null;
    private TextView mTextQuestionTitle = null;
    private TextView mTextChapterTitle = null;
    private TextView mTextChapter = null;

    private String chapterTitle;
    private ImageButton mButtonBack;
    private ImageButton mButtonRevert;
    private Button mButtonCommit = null;

    private QuestionInfo mCurrentQuestionInfo;
    private List<OptionInfo> mOptions = new ArrayList<>();
    private boolean isFirstIn = true;
    private SelectionMethod mSelectionMethod = null;

    public IDBOperator mOperator = null;
    private List<QuestionInfo> mQuestionInfos;
    private static int groupPosition;
    private static int childPosition;
    private MyHandler handler = null;
    private AnswerMethod mAnswerMethod = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_detail);
        initData();
        initViews();
    }


    @Override
    public void initData() {
        Intent i = getIntent();
        groupPosition = i.getIntExtra("groupPosition",0);
        childPosition = i.getIntExtra("childPosition",0);
        chapterTitle = i.getStringExtra("chapterTitle");
        mOperator = getOperator(groupPosition);
        mQuestionInfos = mOperator.getQuestionsByChapterId(getChapterId(groupPosition,childPosition));
        handler = new MyHandler(this);

    }


    @Override
    public void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_question_detail_view_pager);
        mTextChapter = (TextView) findViewById(R.id.id_title_bar_text_title);
        mButtonRevert = (ImageButton) findViewById(R.id.id_title_bar_button_revert);
        mButtonRevert.setVisibility(View.VISIBLE);
        mTextChapterTitle = (TextView) findViewById(R.id.id_question_detail_text_character);
        mTextChapterTitle.setText(chapterTitle);

        mViews = new ArrayList<>();
        for(int i=0;i<mQuestionInfos.size();i++){
            View v = Utils.getView(R.layout.question_detail_item);
            mViews.add(v);

        }

        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                updateContent(position);

                Message msg = Message.obtain();
                msg.what = MSG_SAVE_PROGRESS;
                msg.arg1 = position;
                handler.dispatchMessage(msg);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mButtonBack = (ImageButton) findViewById(R.id.id_title_bar_button_back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.finishActivity(QuestionDetailActivity.this);
            }
        });
        mButtonRevert.setOnClickListener(dialogOnClickListener);

        int position = SharedPreferenceUtil.loadProgress(groupPosition,childPosition);
        updateContent(position);

    }


    private void updateContent(int position){
        mOptions.clear();

        mTextQuestionTitle = (TextView) mViews.get(position).findViewById(R.id.id_question_detail_text_title);
        mButtonCommit = (Button) mViews.get(position).findViewById(R.id.id_question_detail_button_commit);
        mButtonCommit.setOnClickListener(commitOnClickListener);

        mTextChapter.setText(mQuestionInfos.get(position).getSubjectName());
        mOptions = mOperator.getOptionsByQuestionId(mQuestionInfos.get(position).getQuestionId());

        mCurrentQuestionInfo = mQuestionInfos.get(position);
        mViewPager.setCurrentItem(position);

        mSelectionMethod = new SelectionMethod();

        if(mCurrentQuestionInfo.getType().equals(mSelectionMethod.getMultiSelectionType())){
            //多选
            mTextQuestionTitle.setText(getContentStyle(position + 1,mQuestionInfos.get(position).getNumber(),mQuestionInfos.get(position).getTitle()) + " (多选)");
            mSelectionMethod.setSelectionMethod(new MultiSelectionMethod(mViews.get(position),mQuestionInfos.get(position),mOptions));

        }else if(mCurrentQuestionInfo.getType().equals(mSelectionMethod.getSingleSelectionType())){
            //单选
            mTextQuestionTitle.setText(getContentStyle(position + 1,mQuestionInfos.get(position).getNumber(),mQuestionInfos.get(position).getTitle()));
            mSelectionMethod.setSelectionMethod(new SingleSelectionMethod(mViews.get(position),mQuestionInfos.get(position),mOptions));
        }

        mAnswerMethod = new AnswerMethod(mViews.get(position),mCurrentQuestionInfo);

        if(mDB.isCompleteQuestion(mCurrentQuestionInfo.getQuestionId())){
            mSelectionMethod.checkAnswers(mDB.getHistoryAnswers(mCurrentQuestionInfo.getQuestionId()));
            mAnswerMethod.showAnswerUI(true);
        }
    }


    PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
            mSelectionMethod.clearData();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }
    };



    private IDBOperator getOperator(int groupPosition){
        IDBOperator operator = null;
        switch (groupPosition){
            case 0:
                operator = new ChapterMYDBOperator();
                break;
            case 1:
                operator = new ChapterMZTDBOperator();
                break;
            case 2:
                operator = new ChapterSGDBOperator();
                break;
            case 3:
                operator = new ChapterSXYFJDBOperator();
                break;
            case 4:
                operator = new ChapterSZDBOperator();
                break;
            default:
                operator = new ChapterMYDBOperator();
        }
        return operator;
    }

    private int getChapterId(int groupPosition,int childPosition){
        int chapterId = 0;
        if(groupPosition == 0 && childPosition == 0){
            chapterId = 101;
        }else if(groupPosition == 0 && childPosition == 1){
            chapterId = 102;
        }else if(groupPosition == 0 && childPosition == 2){
            chapterId = 103;
        }else if(groupPosition == 0 && childPosition == 3){
            chapterId = 104;
        }else if(groupPosition == 0 && childPosition == 4){
            chapterId = 105;
        }else if(groupPosition == 0 && childPosition == 5){
            chapterId = 106;
        }else if(groupPosition == 0 && childPosition == 6){
            chapterId = 107;
        }else if(groupPosition == 0 && childPosition == 7){
            chapterId = 108;


        }else if(groupPosition == 1 && childPosition == 0){
            chapterId = 201;
        }else if(groupPosition == 1 && childPosition == 1){
            chapterId = 202;
        }else if(groupPosition == 1 && childPosition == 2){
            chapterId = 203;
        }else if(groupPosition == 1 && childPosition == 3){
            chapterId = 204;
        }else if(groupPosition == 1 && childPosition == 4){
            chapterId = 205;
        }else if(groupPosition == 1 && childPosition == 5){
            chapterId = 206;
        }else if(groupPosition == 1 && childPosition == 6){
            chapterId = 207;
        }else if(groupPosition == 1 && childPosition == 7){
            chapterId = 208;
        }else if(groupPosition == 1 && childPosition == 8){
            chapterId = 209;
        }else if(groupPosition == 1 && childPosition == 9){
            chapterId = 210;
        }else if(groupPosition == 1 && childPosition == 10){
            chapterId = 211;
        }else if(groupPosition == 1 && childPosition == 11){
            chapterId = 212;
        }


        else if(groupPosition == 2 && childPosition == 0){
            chapterId = 301;
        }else if(groupPosition == 2 && childPosition == 1){
            chapterId = 302;
        }else if(groupPosition == 2 && childPosition == 2){
            chapterId = 303;
        }else if(groupPosition == 2 && childPosition == 3){
            chapterId = 304;
        }else if(groupPosition == 2 && childPosition == 4){
            chapterId = 305;
        }else if(groupPosition == 2 && childPosition == 5){
            chapterId = 306;
        }else if(groupPosition == 2 && childPosition == 6){
            chapterId = 307;
        }else if(groupPosition == 2 && childPosition == 7){
            chapterId = 308;
        }else if(groupPosition == 2 && childPosition == 8){
            chapterId = 309;
        }else if(groupPosition == 2 && childPosition == 9){
            chapterId = 310;


        }else if(groupPosition == 3 && childPosition == 0){
            chapterId = 401;
        }else if(groupPosition == 3 && childPosition == 1){
            chapterId = 402;
        }else if(groupPosition == 3 && childPosition == 2){
            chapterId = 403;
        }else if(groupPosition == 3 && childPosition == 3){
            chapterId = 404;
        }else if(groupPosition == 3 && childPosition == 4){
            chapterId = 405;
        }else if(groupPosition == 3 && childPosition == 5){
            chapterId = 406;
        }else if(groupPosition == 3 && childPosition == 6){
            chapterId = 407;
        }else if(groupPosition == 3 && childPosition == 7){
            chapterId = 408;
        }else if(groupPosition == 3 && childPosition == 8){
            chapterId = 409;
        }


        else if(groupPosition == 4 && childPosition == 0){
            chapterId = 410;
        }else if(groupPosition == 4 && childPosition == 1){
            chapterId = 411;
        }else if(groupPosition == 4 && childPosition == 2){
            chapterId = 412;
        }else if(groupPosition == 4 && childPosition == 3){
            chapterId = 413;
        }else if(groupPosition == 4 && childPosition == 4){
            chapterId = 414;
        }else if(groupPosition == 4 && childPosition == 5){
            chapterId = 415;
        }else if(groupPosition == 4 && childPosition == 6){
            chapterId = 416;
        }else if(groupPosition == 4 && childPosition == 7){
            chapterId = 417;
        }else if(groupPosition == 4 && childPosition == 8){
            chapterId = 418;
        }else if(groupPosition == 4 && childPosition == 9){
            chapterId = 419;
        }else if(groupPosition == 4 && childPosition == 10){
            chapterId = 420;
        }
        return chapterId;
    }


    private View.OnClickListener commitOnClickListener  = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if(mSelectionMethod.getSelectionType().equals(mSelectionMethod.getMultiSelectionType())){
                mSelectionMethod.checkAnswers(null);

            }else if(mSelectionMethod.getSelectionType().equals(mSelectionMethod.getSingleSelectionType())){
                mSelectionMethod.checkAnswers(mSelectionMethod.getSelection());
            }

            mSelectionMethod.saveAnswers(mCurrentQuestionInfo.getQuestionId(),mSelectionMethod.getSelection());

            if(!TextUtils.isEmpty(mSelectionMethod.getSelection())){
                mAnswerMethod.showAnswerUI(true);
            }
        }


    };

    @Override
    protected void onStop() {
        super.onStop();
        int lastPosition= mViewPager.getCurrentItem();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    private static class MyHandler extends Handler{
        private WeakReference<Activity> ref = null;
        public MyHandler(Activity activity){
            ref = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SAVE_PROGRESS:
                    SharedPreferenceUtil.saveProgress(groupPosition,childPosition,msg.arg1);
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private View.OnClickListener dialogOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final CustomDialog dialog = new CustomDialog(QuestionDetailActivity.this,"一键重学？","重学后所有学习进度将重头开始");
            dialog.setButtonClickListener(new CustomDialog.DialogButtonListener() {
                @Override
                public void onConfirm() {
                    SharedPreferenceUtil.saveProgress(groupPosition,childPosition,0);
                    mDB.clearHistoryAnswersByChapterId(mCurrentQuestionInfo.getChapterId());
                    finish();
                }

                @Override
                public void onCancel() {
                    dialog.dissmiss();
                }
            });
            dialog.show();
        }
    };


}
