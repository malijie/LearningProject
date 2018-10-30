package com.politics.exam.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.politics.exam.PoliticsApplication;

import static com.politics.exam.PoliticsApplication.sContext;
import static com.politics.exam.db.DBConstants.DB_NAME;
import static com.politics.exam.db.DBConstants.VERSION;

/**
 * Created by malijie on 2017/6/1.
 */

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper() {
        super(sContext, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
