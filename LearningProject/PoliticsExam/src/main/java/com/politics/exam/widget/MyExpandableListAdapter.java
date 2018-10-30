package com.politics.exam.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.politics.exam.PoliticsApplication;
import com.politics.exam.R;
import com.politics.exam.activity.QuestionDetailActivity;
import com.politics.exam.data.Profile;
import com.politics.exam.db.operator.ChapterMZTDBOperator;
import com.politics.exam.db.operator.ChapterMYDBOperator;
import com.politics.exam.db.operator.ChapterSGDBOperator;
import com.politics.exam.db.operator.ChapterSXYFJDBOperator;
import com.politics.exam.db.operator.ChapterSZDBOperator;
import com.politics.exam.db.operator.IDBOperator;
import com.politics.exam.entity.QuestionInfo;
import com.politics.exam.fragment.QuestionsFragment;
import com.politics.exam.util.IntentManager;
import com.politics.exam.util.Logger;
import com.politics.exam.util.SharedPreferenceUtil;
import com.politics.exam.util.ToastManager;
import com.politics.exam.util.Utils;
import com.politics.exam.wap.PayBaseAction;
import com.politics.exam.wap.PermissionController;
import com.politics.exam.wap.VipPayAction;

import java.util.List;

import static com.politics.exam.data.Profile.MY_01;
import static com.politics.exam.data.Profile.MY_02;

/**
 * Created by malijie on 2017/5/26.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Fragment mFragment = null;
    private ExpandableListView mExpandListView = null;
    private View mGroupView = null;
    private View mChildView = null;
    private TextView mTextQuestionNum = null;
    private TextView mTextSubject = null;
    private TextView mTextCharacter = null;
    private TextView mTextProcess = null;

    private IDBOperator mOperator = null;
    private int mQuestionCounts = 0;
    private int mCurrentGroupId = 0;


    public MyExpandableListAdapter(ExpandableListView expandListView,Fragment fragment){
        mExpandListView = expandListView;
        mFragment = fragment;
    }

    private String[] mSubjects = new String[]{
            "马原", "毛中特", "史纲", "思修与法基","时政"
    };


    private String[][] mCharacter = new Profile().getCharacters();

    private static final int INDEX_MAYUAN = 0;//马原
    private static final int INDEX_MAOZHONGTE = 1;//毛中特
    private static final int INDEX_SHIGANG = 2; //史纲
    private static final int INDEX_SIXIUYUFANGJI = 3;//思修与法基
    private static final int INDEX_SHIZHENG = 4;//时政


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return mSubjects.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCharacter[groupPosition].length;
    }

    @Override
    public String getGroup(int groupPosition) {
        return mSubjects[groupPosition];
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return mCharacter[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        mGroupView = Utils.getView(R.layout.expand_group_view);
        mTextQuestionNum = (TextView) mGroupView.findViewById(R.id.id_expand_group_text_question_num);
        mTextSubject = (TextView) mGroupView.findViewById(R.id.id_expand_group_text_title);

        mQuestionCounts = getGroupQuestionCount(groupPosition);

        mTextQuestionNum.setText(String.valueOf(mQuestionCounts) + "题");
        mTextSubject.setText(mSubjects[groupPosition]);
        return mGroupView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        mCurrentGroupId = (int)getGroupId(groupPosition);
        mChildView = Utils.getView(R.layout.expand_item_view);
        RelativeLayout layout = (RelativeLayout) mChildView.findViewById(R.id.id_expand_item_layout);
        mTextCharacter = (TextView) mChildView.findViewById(R.id.id_expand_item_text_title);
        mTextProcess = (TextView) mChildView.findViewById(R.id.id_expand_item_text_num);

        int questionCount = getQuestionTotalCount(groupPosition,childPosition);
        int process = SharedPreferenceUtil.loadProgress(groupPosition,childPosition);
        mTextProcess.setText( process + 1 + "/"+ questionCount);
        mTextCharacter.setText(mCharacter[groupPosition][childPosition]);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childPosition != 0 && !SharedPreferenceUtil.loadPayedVIPStatus()){
                    showPayTip();
                    return;
                }

                Intent i = new Intent();
                i.setClass(mFragment.getActivity(),QuestionDetailActivity.class);
                i.putExtra("groupPosition",groupPosition);
                i.putExtra("childPosition",childPosition);
                i.putExtra("chapterTitle",mCharacter[groupPosition][childPosition]);
                mFragment.startActivityForResult(i, QuestionsFragment.RESULT_CODE_UPDATE_PROCESS);
            }
        });
        return mChildView;
    }

    private Activity getActivity(){
        return  mFragment.getActivity();
    }

    private void showPayTip() {
        final CustomDialog dialog = new CustomDialog(getActivity(), PayBaseAction.GOODS_NAME_VIP,PayBaseAction.GOODS_DESCR_VIP);
        dialog.setButtonClickListener(new CustomDialog.DialogButtonListener() {
            @Override
            public void onConfirm() {
                if(PermissionController.checkPermission(getActivity())){
                    new VipPayAction(getActivity()).pay();
                    dialog.dissmiss();
                }else{
                    ToastManager.showLongMsg("未打开权限，请到设置-应用中打开相关权限后完成支付");
                    dialog.dissmiss();
                }

            }

            @Override
            public void onCancel() {
                dialog.dissmiss();
            }
        });
        dialog.show();

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }



    @Override
    public void onGroupExpanded(int groupPosition) {
        List<QuestionInfo> questionInfos = null;
        switch (groupPosition){
            case INDEX_MAYUAN:
                mOperator = new ChapterMYDBOperator();
                questionInfos = mOperator.getChapterQuestions();
                break;
            case INDEX_MAOZHONGTE:
                mOperator = new ChapterMZTDBOperator();
                questionInfos = mOperator.getChapterQuestions();

                break;
            case INDEX_SHIGANG:
                mOperator = new ChapterSGDBOperator();
                questionInfos = mOperator.getChapterQuestions();

                break;
            case INDEX_SIXIUYUFANGJI:
                mOperator = new ChapterSXYFJDBOperator();
                questionInfos = mOperator.getChapterQuestions();

                break;
            case INDEX_SHIZHENG:
                mOperator = new ChapterSZDBOperator();
                questionInfos = mOperator.getChapterQuestions();

                break;
        }
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    private int getGroupQuestionCount(int groupIndex){
        switch (groupIndex){
            case INDEX_MAYUAN:
                mOperator = new ChapterMYDBOperator();
                mQuestionCounts = mOperator.getQuestionCount();
                break;
            case INDEX_MAOZHONGTE:
                mOperator = new ChapterMZTDBOperator();
                mQuestionCounts = mOperator.getQuestionCount();

                break;
            case INDEX_SHIGANG:
                mOperator = new ChapterSGDBOperator();
                mQuestionCounts = mOperator.getQuestionCount();

                break;
            case INDEX_SIXIUYUFANGJI:
                mOperator = new ChapterSXYFJDBOperator();
                mQuestionCounts = mOperator.getQuestionCount();

                break;
            case INDEX_SHIZHENG:
                mOperator = new ChapterSZDBOperator();
                mQuestionCounts = mOperator.getQuestionCount();

                break;
        }
        return mQuestionCounts;
    }

    private int getQuestionTotalCount(int groupPosition,int childPosition){
        int count = 0;
        if(groupPosition == 0 && childPosition == 0){
            count = Profile.MY_01;
        }else if(groupPosition == 0 && childPosition == 1){
            count = Profile.MY_02;
        }else if(groupPosition == 0 && childPosition == 2){
            count = Profile.MY_03;
        }else if(groupPosition == 0 && childPosition == 3){
            count = Profile.MY_04;
        }else if(groupPosition == 0 && childPosition == 4){
            count = Profile.MY_05;
        }else if(groupPosition == 0 && childPosition == 5){
            count = Profile.MY_06;
        }else if(groupPosition == 0 && childPosition == 6){
            count = Profile.MY_07;
        }else if(groupPosition == 0 && childPosition == 7){
            count = Profile.MY_08;


        }else if(groupPosition == 1 && childPosition == 0){
            count = Profile.MZT_01;
        }else if(groupPosition == 1 && childPosition == 1){
            count = Profile.MZT_02;
        }else if(groupPosition == 1 && childPosition == 2){
            count = Profile.MZT_03;
        }else if(groupPosition == 1 && childPosition == 3){
            count = Profile.MZT_04;
        }else if(groupPosition == 1 && childPosition == 4){
            count = Profile.MZT_05;
        }else if(groupPosition == 1 && childPosition == 5){
            count = Profile.MZT_06;
        }else if(groupPosition == 1 && childPosition == 6){
            count = Profile.MZT_07;
        }else if(groupPosition == 1 && childPosition == 7){
            count = Profile.MZT_08;
        }else if(groupPosition == 1 && childPosition == 8){
            count = Profile.MZT_09;
        }else if(groupPosition == 1 && childPosition == 9){
            count = Profile.MZT_10;
        }else if(groupPosition == 1 && childPosition == 10){
            count = Profile.MZT_11;
        }else if(groupPosition == 1 && childPosition == 11){
            count = Profile.MZT_12;
        }


        else if(groupPosition == 2 && childPosition == 0){
            count = Profile.SG_01;
        }else if(groupPosition == 2 && childPosition == 1){
            count = Profile.SG_02;
        }else if(groupPosition == 2 && childPosition == 2){
            count = Profile.SG_03;
        }else if(groupPosition == 2 && childPosition == 3){
            count = Profile.SG_04;
        }else if(groupPosition == 2 && childPosition == 4){
            count = Profile.SG_05;
        }else if(groupPosition == 2 && childPosition == 5){
            count = Profile.SG_06;
        }else if(groupPosition == 2 && childPosition == 6){
            count = Profile.SG_07;
        }else if(groupPosition == 2 && childPosition == 7){
            count = Profile.SG_08;
        }else if(groupPosition == 2 && childPosition == 8){
            count = Profile.SG_09;
        }else if(groupPosition == 2 && childPosition == 9){
            count = Profile.SG_10;


        }else if(groupPosition == 3 && childPosition == 0){
            count = Profile.SXYFJ_00;
        }else if(groupPosition == 3 && childPosition == 1){
            count = Profile.SXYFJ_01;
        }else if(groupPosition == 3 && childPosition == 2){
            count = Profile.SXYFJ_02;
        }else if(groupPosition == 3 && childPosition == 3){
            count = Profile.SXYFJ_03;
        }else if(groupPosition == 3 && childPosition == 4){
            count = Profile.SXYFJ_04;
        }else if(groupPosition == 3 && childPosition == 5){
            count = Profile.SXYFJ_05;
        }else if(groupPosition == 3 && childPosition == 6){
            count = Profile.SXYFJ_06;
        }else if(groupPosition == 3 && childPosition == 7){
            count = Profile.SXYFJ_07;
        }else if(groupPosition == 3 && childPosition == 8){
            count = Profile.SXYFJ_08;
        }


        else if(groupPosition == 4 && childPosition == 0){
            count = Profile.SZ_01;
        }else if(groupPosition == 4 && childPosition == 1){
            count = Profile.SZ_02;
        }else if(groupPosition == 4 && childPosition == 2){
            count = Profile.SZ_03;
        }else if(groupPosition == 4 && childPosition == 3){
            count = Profile.SZ_04;
        }else if(groupPosition == 4 && childPosition == 4){
            count = Profile.SZ_05;
        }else if(groupPosition == 4 && childPosition == 5){
            count = Profile.SZ_06;
        }else if(groupPosition == 4 && childPosition == 6){
            count = Profile.SZ_07;
        }else if(groupPosition == 4 && childPosition == 7){
            count = Profile.SZ_08;
        }else if(groupPosition == 4 && childPosition == 8){
            count = Profile.SZ_09;
        }else if(groupPosition == 4 && childPosition == 9){
            count = Profile.SZ_10;
        }else if(groupPosition == 4 && childPosition == 10){
            count = Profile.SZ_11;
        }
        return count;
    }

    public void refresh(){
        mExpandListView.setVisibility(View.GONE);
        notifyDataSetChanged();
        mExpandListView.collapseGroup(getGroupId());
        mExpandListView.expandGroup(getGroupId());
        mExpandListView.setVisibility(View.VISIBLE);
    }

    public int getGroupId(){
        return mCurrentGroupId;
    }


}