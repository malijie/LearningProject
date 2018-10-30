package com.politics.exam.db.operator;

import android.database.Cursor;

import com.politics.exam.db.SQLContainer;
import com.politics.exam.entity.QuestionInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by malijie on 2017/6/2.
 */

public class ChapterSGDBOperator extends BaseOperator implements IDBOperator{

    @Override
    public List<QuestionInfo> getChapterQuestions() {
        List<QuestionInfo> questionInfos = new ArrayList<>();
        if (mDB != null) {
            Cursor cursor = mDB.rawQuery(SQLContainer.queryChapterQuestions(CHAPTER_SHIGANG), null);
            while (cursor.moveToNext()) {
                QuestionInfo questionInfo = new QuestionInfo();
                questionInfo.setQuestionId(cursor.getInt(cursor.getColumnIndex("QUESTION_ID")));
                questionInfo.setChapterParentId(cursor.getInt(cursor.getColumnIndex("CHAPTER_PARENT_ID")));
                questionInfo.setChapterId(cursor.getInt(cursor.getColumnIndex("CHAPTER_ID")));
                questionInfo.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
                questionInfo.setTitleImage(cursor.getString(cursor.getColumnIndex("TITLE_IMG")));
                questionInfo.setsNum(cursor.getInt(cursor.getColumnIndex("S_NUM")));
                questionInfo.setUnit(cursor.getString(cursor.getColumnIndex("UNIT")));
                questionInfo.setNumber(cursor.getString(cursor.getColumnIndex("NUMBER")));
                questionInfo.setYear(cursor.getString(cursor.getColumnIndex("YEAR")));
                questionInfo.setNumberNumber(cursor.getInt(cursor.getColumnIndex("NUMBER_NUMBER")));
                questionInfo.setNumberType(cursor.getString(cursor.getColumnIndex("NUMBER_TYPE")));
                questionInfo.setRestore(cursor.getString(cursor.getColumnIndex("RESTORE")));
                questionInfo.setExplain(cursor.getString(cursor.getColumnIndex("EXPLAIN")));
                questionInfo.setMediaUrl(cursor.getString(cursor.getColumnIndex("MEDIA_URL")));
                questionInfo.setMediaImage(cursor.getString(cursor.getColumnIndex("MEDIA_IMG")));
                questionInfo.setMediaId(cursor.getString(cursor.getColumnIndex("MEDIA_ID")));
                questionInfo.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                questionInfo.setAnswer(cursor.getString(cursor.getColumnIndex("ANSWER")));
                questionInfo.setSubjectName(cursor.getString(cursor.getColumnIndex("SUBJECT_NAME")));
                questionInfos.add(questionInfo);
            }
        }

        return questionInfos;
    }

    @Override
    public int getQuestionCount() {
        Cursor cursor = mDB.rawQuery(SQLContainer.queryChapterQuestions(CHAPTER_SHIGANG), null);
        return cursor.getCount();

    }



}
