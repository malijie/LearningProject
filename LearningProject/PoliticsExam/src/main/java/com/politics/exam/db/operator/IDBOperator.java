package com.politics.exam.db.operator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.politics.exam.db.DBHelper;
import com.politics.exam.db.SQLContainer;
import com.politics.exam.entity.OptionInfo;
import com.politics.exam.entity.QuestionInfo;

import java.util.List;

/**
 * Created by malijie on 2017/6/2.
 */

public interface IDBOperator {

       String CHAPTER_MAYUAN = "马原";
       String CHAPTER_MAOZHONGTE = "毛中特";
       String CHAPTER_SHIGANG = "史纲";
       String CHAPTER_SIXIUYUFAJI = "思修与法基";
       String CHAPTER_SHIZHENG = "时政";


    List<QuestionInfo> getChapterQuestions();
    int getQuestionCount();
    List<QuestionInfo> getQuestionsByChapterId(int chapterId);
    List<OptionInfo> getOptionsByQuestionId(int questionId);

}
