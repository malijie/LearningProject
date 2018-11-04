package com.politics.exam.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.lib.util.ToastManager;
import com.common.lib.util.PermissionController;
import com.pay.lib.wap.WapManager;
import com.politics.exam.R;
import com.politics.exam.fragment.QuestionsFragment;
import com.politics.exam.fragment.ExamFragment;
import com.politics.exam.fragment.UserFragment;
import com.politics.exam.fragment.VideoLearningFragment;


/**
 * Created by malijie on 2016/12/8.
 */

public class HomePageActivity extends BaseActivity implements View.OnClickListener{
    private QuestionsFragment mQuestionFragment;
    private VideoLearningFragment mVideoFragment;
    private ExamFragment mExamFragment;
    private UserFragment mUserFragment;
    private View mQuestionsLayout;
    private View mVideoLayout;
    private View mExamLayout;
    private View mUserLayout;
    private ImageView mQuestionsImage;
    private ImageView mVideoImage;
    private ImageView mExamImage;
    private ImageView mUserImage;
    private TextView mTextTitle;
    private TextView mQuestionText;
    private TextView mVideoText;
    private TextView mExamText;
    private TextView mUserText;
    private ImageButton mButtonBack;
    private ImageButton mButtonRevert;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_page);
        // 初始化布局元素
        initData();
        initViews();
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    @Override
    public void initData() {
        if(PermissionController.checkPermission(this)){
            WapManager.getInstance(this);
//            PayConnect.getInstance(this);
        }
    }

    @Override
    public void initViews() {
        mTextTitle = (TextView) findViewById(R.id.id_title_bar_text_title);

        mQuestionsLayout = findViewById(R.id.questions_layout);
        mExamLayout = findViewById(R.id.exam_layout);
        mUserLayout = findViewById(R.id.user_layout);
        mVideoLayout = findViewById(R.id.video_layout);

        mQuestionsImage = (ImageView) findViewById(R.id.message_image);
        mExamImage = (ImageView) findViewById(R.id.contacts_image);
        mVideoImage = (ImageView) findViewById(R.id.video_image);
        mUserImage = (ImageView) findViewById(R.id.news_image);

        mQuestionText = (TextView) findViewById(R.id.message_text);
        mVideoText = (TextView) findViewById(R.id.video_text);
        mExamText = (TextView) findViewById(R.id.contacts_text);
        mUserText = (TextView) findViewById(R.id.news_text);

        mButtonBack = (ImageButton) findViewById(R.id.id_title_bar_button_back);
        mButtonRevert = (ImageButton) findViewById(R.id.id_title_bar_button_revert);

        mQuestionsLayout.setOnClickListener(this);
        mVideoLayout.setOnClickListener(this);
        mExamLayout.setOnClickListener(this);
        mUserLayout.setOnClickListener(this);

        mButtonRevert.setVisibility(View.GONE);
        mButtonBack.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.questions_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.video_layout:
                setTabSelection(1);
                break;
            case R.id.exam_layout:
                // 当点击了联系人tab时，选中第3个tab
                setTabSelection(2);
                break;
            case R.id.user_layout:
                // 当点击了动态tab时，选中第4个tab
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了题库tab时，改变控件的图片和文字颜色
                mQuestionsImage.setImageResource(R.mipmap.question_selected);
                mQuestionText.setTextColor(getResColor(R.color.app_red));
                mTextTitle.setText("题库");
                if (mQuestionFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mQuestionFragment = new QuestionsFragment();
                    transaction.add(R.id.content, mQuestionFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mQuestionFragment);
                }
                break;

            case 1:
                mVideoImage.setImageResource(R.mipmap.play_selected);
                mVideoText.setTextColor(getResColor(R.color.app_red));
                mTextTitle.setText("名师辅导");
                if (mVideoFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    mVideoFragment = new VideoLearningFragment();
                    transaction.add(R.id.content, mVideoFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(mVideoFragment);
                }
                break;
            case 2:
                // 当点击了考试tab时，改变控件的图片和文字颜色
                mExamImage.setImageResource(R.mipmap.exam_selected);
                mExamText.setTextColor(getResColor(R.color.app_red));
                mTextTitle.setText("历年真题");
                if (mExamFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    mExamFragment = new ExamFragment();
                    transaction.add(R.id.content, mExamFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(mExamFragment);
                }
                break;
            case 3:
                // 当点击了用户tab时，改变控件的图片和文字颜色
                mUserImage.setImageResource(R.mipmap.user_selected);
                mUserText.setTextColor(getResColor(R.color.app_red));
                mTextTitle.setText("我的");
                if (mUserFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    mUserFragment = new UserFragment();
                    transaction.add(R.id.content, mUserFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(mUserFragment);
                }
                break;
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                mQuestionsImage.setImageResource(R.mipmap.question_selected);
                mQuestionText.setTextColor(getResColor(R.color.app_red));
                if (mQuestionFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    mQuestionFragment = new QuestionsFragment();
                    transaction.add(R.id.content, mQuestionFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(mQuestionFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        mQuestionsImage.setImageResource(R.mipmap.question_normal);
        mQuestionText.setTextColor(getResColor(R.color.app_grey));
        mVideoImage.setImageResource(R.mipmap.play_unselected);
        mVideoText.setTextColor(getResColor(R.color.app_grey));
        mExamImage.setImageResource(R.mipmap.exam_normal);
        mExamText.setTextColor(getResColor(R.color.app_grey));
        mUserImage.setImageResource(R.mipmap.user_normal);
        mUserText.setTextColor(getResColor(R.color.app_grey));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mQuestionFragment != null) {
            transaction.hide(mQuestionFragment);
        }
        if (mVideoFragment != null) {
            transaction.hide(mVideoFragment);
        }
        if (mExamFragment != null) {
            transaction.hide(mExamFragment);
        }
        if (mUserFragment != null) {
            transaction.hide(mUserFragment);
        }
    }

    private long waitTime = 2000;
    private long touchTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if((currentTime-touchTime)>=waitTime) {
                ToastManager.showExitTipMsg();
                touchTime = currentTime;
            }else {
                WapManager.getInstance(this).close();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
