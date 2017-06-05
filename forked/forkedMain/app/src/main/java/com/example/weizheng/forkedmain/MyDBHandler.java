package com.example.weizheng.forkedmain;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;                  //database version,update when you update the database
    private static final String DATABASE_NAME = "preferences.db";  //name of database
    public static final String TABLE_PREFERENCES = "preferences";    //name of table
    public static final String COLUMN_CHINESE = "chinese";                //name of all your columns
    public static final String COLUMN_MALAY = "malay";
    public static final String COLUMN_INDIAN = "indian";
    public static final String COLUMN_WESTERN = "western";
    public static final String COLUMN_KOREAN = "korean";
    public static final String COLUMN_SWEET = "sweet";
    public static final String COLUMN_SOUR = "sour";
    public static final String COLUMN_SPICY = "spicy";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_PREFERENCES + "( " +
                COLUMN_CHINESE + "INTEGER " +
                COLUMN_MALAY + "INTEGER " +
                COLUMN_INDIAN + "INTEGER " +
                COLUMN_WESTERN + "INTEGER " +
                COLUMN_KOREAN + "INTEGER " +
                COLUMN_SWEET + "INTEGER " +
                COLUMN_SOUR + "INTEGER " +
                COLUMN_SPICY + "INTEGER " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_PREFERENCES);
        onCreate(db);
    }
}
