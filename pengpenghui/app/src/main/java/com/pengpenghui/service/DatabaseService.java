package com.pengpenghui.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pengpenghui.domain.entity.DataBaseTable;

/**
 * Created by 杨超chao on 2015/6/17.
 */
public class DatabaseService extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "database";
    public static final String USER_TABLE_NAME = "user";
    public static final String BROMESSAGE_TABLE_NAME = "bromessage";

    private static final int DATABASE_VISION = 1;

    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VISION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseTable.create_user_table);
        db.execSQL(DataBaseTable.create_message_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS "+BROMESSAGE_TABLE_NAME);
        onCreate(db);
    }
}
