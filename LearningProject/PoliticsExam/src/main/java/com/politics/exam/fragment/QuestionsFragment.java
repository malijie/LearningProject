package com.politics.exam.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.activity.SearchActivity;
import com.politics.exam.activity.ExamDetailActivity;
import com.politics.exam.db.DBManager;
import com.politics.exam.util.SharedPreferenceUtil;
import com.politics.exam.util.ToastManager;
import com.politics.exam.widget.MyExpandableListAdapter;


/**
 * Created by malijie on 2016/12/8.
 */


public class QuestionsFragment extends Fragment {
    public static final int RESULT_CODE_UPDATE_PROCESS = 0x0001;
    private EditText mEditTextSearch = null;
    private ExpandableListView mExpandListView;
    private MyExpandableListAdapter mAdapter =  null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.question_fragment, container, false);
        initData(messageLayout);
        return messageLayout;
    }

    private void initData(final View v) {
        if(SharedPreferenceUtil.loadFirstInit()){
            DBManager.copyDB2Phone(new DBManager.CopyDBListener() {
                @Override
                public void onSuccess() {
                    initViews(v);
                    SharedPreferenceUtil.saveFirstInit(false);
                }

                @Override
                public void onFailed() {
                    SharedPreferenceUtil.saveFirstInit(true);

                }
            });

        }else{
            initViews(v);
        }

    }

    private void initViews(View messageLayout) {
        mExpandListView = (ExpandableListView) messageLayout.findViewById(R.id.expand_list_view);
        mEditTextSearch = (EditText) messageLayout.findViewById(R.id.id_home_edit_search);
        mAdapter = new MyExpandableListAdapter(mExpandListView,this);


        mExpandListView.setAdapter(mAdapter);
        mExpandListView.setGroupIndicator(null);

        mEditTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String keyword = mEditTextSearch.getText().toString();
                    if(TextUtils.isEmpty(keyword)){
                        ToastManager.showShortMsg("请输入搜索内容");
                        return false;
                    }


                    Intent i = new Intent(getActivity(), SearchActivity.class);
                    i.putExtra("keyword",keyword);
                    startActivity(i);
                }

                return false;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case RESULT_CODE_UPDATE_PROCESS:

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.refresh();

                    }
                });

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}