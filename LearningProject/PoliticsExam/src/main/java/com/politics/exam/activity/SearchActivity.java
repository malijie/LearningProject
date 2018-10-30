package com.politics.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.db.operator.BaseOperator;
import com.politics.exam.entity.QuestionInfo;
import com.politics.exam.util.IntentManager;
import com.politics.exam.util.Logger;
import com.politics.exam.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malijie on 2017/8/20.
 */

public class SearchActivity extends BaseActivity {
    private ListView lv = null;
    private TextView mTextTitle = null;
    private ImageButton mButtonBack = null;
    private List<QuestionInfo> mSearchResult = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        initData();
        initViews();
    }

    @Override
    public void initData() {
        String keyword = getIntent().getStringExtra("keyword");
        mSearchResult = mDB.getSearchResult(keyword);

    }

    @Override
    public void initViews() {
        lv = (ListView) findViewById(R.id.id_search_lv);
        mTextTitle = (TextView) findViewById(R.id.id_title_bar_text_title);
        mButtonBack = (ImageButton) findViewById(R.id.id_title_bar_button_back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.finishActivity(SearchActivity.this);
            }
        });

        mTextTitle.setText("搜索结果");
        lv.setAdapter(new SearchAdapter(mSearchResult));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this,SearchDetailActivity.class);
                i.putExtra("question_info",mSearchResult.get(position));
                startActivity(i);
            }
        });
    }

    private class SearchAdapter extends BaseAdapter{
        private List<QuestionInfo> mQuestionInfos = new ArrayList();

        public SearchAdapter(List<QuestionInfo> questionInfoList){
            mQuestionInfos = questionInfoList;
        }

        @Override
        public int getCount() {
            return mQuestionInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return mQuestionInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView = Utils.getView(R.layout.search_item);
                holder = new ViewHolder();
                holder.mTextSubject = (TextView) convertView.findViewById(R.id.id_search_item_text_subject);
                holder.mTextTitle = (TextView) convertView.findViewById(R.id.id_search_item_text_title);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTextSubject.setText(mQuestionInfos.get(position).getSubjectName());
            holder.mTextTitle.setText(mQuestionInfos.get(position).getTitle());

            return convertView;
        }

        private class ViewHolder{
            public TextView mTextChapter = null;
            public TextView mTextSubject = null;
            public TextView mTextTitle = null;

        }
    }
}
