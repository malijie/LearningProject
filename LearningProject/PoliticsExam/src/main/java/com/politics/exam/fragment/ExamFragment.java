package com.politics.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.politics.exam.R;
import com.politics.exam.activity.ExamDetailActivity;
import com.politics.exam.activity.SearchActivity;
import com.politics.exam.db.operator.BaseOperator;
import com.politics.exam.entity.ExamInfo;
import com.politics.exam.util.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by malijie on 2016/12/8.
 */

public class ExamFragment extends Fragment {
    private static final int START_YEAR = 2012;
    private static final int END_YEAR = 2017;
    private ListView lv = null;
    private List<String> examList = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exam_fragment, container, false);

        initData();
        initViews(v);

        return v;
    }

    private void initData() {
        for(int i=END_YEAR;i>=START_YEAR;i--){
            String title = i + "年全国考研政治真题";
            examList.add(title);
        }

    }

    private void initViews(View v) {
        lv = (ListView) v.findViewById(R.id.id_exam_lv);
        lv.setAdapter(new ExamAdapter(examList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(),ExamDetailActivity.class);
                i.putExtra("year",2017-position + "");
                startActivity(i);
            }
        });
    }

    private class ExamAdapter extends BaseAdapter{
        private List<String> mEexamInfos = new ArrayList<>();
        public ExamAdapter(List<String> examInfos){
            mEexamInfos = examInfos;
        }


        @Override
        public int getCount() {
            return examList.size();
        }

        @Override
        public Object getItem(int position) {
            return examList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView = Utils.getView(R.layout.exam_item);
                holder = new ViewHolder();
                holder.mTextTitle = (TextView) convertView.findViewById(R.id.id_exam_item_text_title);
                holder.mTextContent = (TextView) convertView.findViewById(R.id.id_exam_item_text_content);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTextTitle.setText(examList.get(position));
            holder.mTextContent.setText("一、单项选择题：1-16小题，每小题1分，共16分。下列每题给出的四个选项中，只有一个选项是符合题目要求的。请在答题卡上将所选项的字母涂黑。");

            return convertView;
        }

        private class ViewHolder{
            public TextView mTextTitle = null;
            public TextView mTextContent = null;
        }
    }

}