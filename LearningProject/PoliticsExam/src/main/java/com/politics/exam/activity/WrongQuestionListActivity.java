package com.politics.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.entity.WrongQuestionInfo;
import com.politics.exam.util.IntentManager;
import com.politics.exam.util.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by malijie on 2018/10/5.
 */

public class WrongQuestionListActivity extends BaseActivity{
    private ListView mLv = null;
    private List<WrongQuestionInfo> wrongQuestionInfoList = null;
    private WrongQuestionAdapter adapter = null;
    private TextView mTitleBar = null;
    private ImageButton mBtnBack = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrong_question_list_layout);
        initData();
        initViews();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        mLv = (ListView) findViewById(R.id.id_wrong_question_list);
        mBtnBack = (ImageButton) findViewById(R.id.id_title_bar_button_back);
        mTitleBar = (TextView) findViewById(R.id.id_title_bar_text_title);

        mTitleBar.setText("我的错题");
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.finishActivity(WrongQuestionListActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        wrongQuestionInfoList = mDB.getAllWrongQuestions();
        adapter = new WrongQuestionAdapter(wrongQuestionInfoList);
        adapter.notifyDataSetChanged();
        mLv.setAdapter(adapter);

    }

    private class WrongQuestionAdapter extends BaseAdapter{
        List<WrongQuestionInfo> items = null;

        public WrongQuestionAdapter(List<WrongQuestionInfo> wrongQuestionInfos){
            this.items = wrongQuestionInfos;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                convertView = Utils.getView(R.layout.wrong_question_item);
                holder.mLayout = (LinearLayout) convertView.findViewById(R.id.id_wrong_item_layout);
                holder.mTextChapter = (TextView) convertView.findViewById(R.id.id_wrong_item_chapter);
                holder.mTextTitle = (TextView) convertView.findViewById(R.id.id_wrong_item_title);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTextChapter.setText(items.get(position).getChapter());
            holder.mTextTitle.setText(items.get(position).getTitle());
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =  new Intent();
                    i.putExtra("position",position);
                    i.putExtra("wrong_questions", (Serializable) wrongQuestionInfoList);
                    IntentManager.startActivity(i,WrongQuestionDetailActivity.class);
                }
            });


            return convertView;
        }

        private class ViewHolder{
            public LinearLayout mLayout = null;
            public TextView mTextChapter = null;
            public TextView mTextTitle = null;
        }
    }
}
