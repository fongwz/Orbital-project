package com.example.weizheng.forkedmain;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.MutableShort;
import android.widget.Toast;

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
    public static final String COLUMN_MEAT = "meat";
    public static final String COLUMN_SEAFOOD = "seafood";
    public static final String COLUMN_VEG = "veg";
    public static final String COLUMN_DESSERT = "dessert";
    public static final String COLUMN_SAVE = "save";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_PREFERENCES + "(" +
                COLUMN_CHINESE + " INTEGER, " +
                COLUMN_MALAY + " INTEGER, " +
                COLUMN_INDIAN + " INTEGER, " +
                COLUMN_WESTERN + " INTEGER, " +
                COLUMN_KOREAN + " INTEGER, " +
                COLUMN_SWEET + " INTEGER, " +
                COLUMN_SOUR + " INTEGER, " +
                COLUMN_SPICY + " INTEGER, " +
                COLUMN_MEAT + " INTEGER, " +
                COLUMN_SEAFOOD + " INTEGER, " +
                COLUMN_VEG + " INTEGER, " +
                COLUMN_DESSERT + " INTEGER, " +
                COLUMN_SAVE + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_PREFERENCES);
        onCreate(db);
    }

    public void addSavePreferences(Preferences preferences){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHINESE, preferences.is_chinese());
        values.put(COLUMN_MALAY, preferences.is_malay());
        values.put(COLUMN_INDIAN, preferences.is_indian());
        values.put(COLUMN_WESTERN, preferences.is_western());
        values.put(COLUMN_KOREAN, preferences.is_korean());
        values.put(COLUMN_SWEET, preferences.is_sweet());
        values.put(COLUMN_SOUR, preferences.is_sour());
        values.put(COLUMN_SPICY, preferences.is_spicy());
        values.put(COLUMN_MEAT, preferences.is_meat());
        values.put(COLUMN_SEAFOOD, preferences.is_seafood());
        values.put(COLUMN_VEG, preferences.is_veg());
        values.put(COLUMN_DESSERT, preferences.is_dessert());
        values.put(COLUMN_SAVE, "true");
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PREFERENCES, null, values);
        db.close();
    }

    public void addTempPreferences(Preferences preferences){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHINESE, preferences.is_chinese());
        values.put(COLUMN_MALAY, preferences.is_malay());
        values.put(COLUMN_INDIAN, preferences.is_indian());
        values.put(COLUMN_WESTERN, preferences.is_western());
        values.put(COLUMN_KOREAN, preferences.is_korean());
        values.put(COLUMN_SWEET, preferences.is_sweet());
        values.put(COLUMN_SOUR, preferences.is_sour());
        values.put(COLUMN_SPICY, preferences.is_spicy());
        values.put(COLUMN_MEAT, preferences.is_meat());
        values.put(COLUMN_SEAFOOD, preferences.is_seafood());
        values.put(COLUMN_VEG, preferences.is_veg());
        values.put(COLUMN_DESSERT, preferences.is_dessert());
        values.put(COLUMN_SAVE, "false");
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PREFERENCES, null, values);
        db.close();
    }

    public void deleteTempPreferences(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PREFERENCES + " WHERE " + COLUMN_SAVE + " = 'false';");
    }

    public void resetPreferences(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE * FROM " + TABLE_PREFERENCES + ";");
    }

    public void updatePreferences(Preferences preferences){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_PREFERENCES + " SET " +
                COLUMN_CHINESE + " = " + preferences.is_chinese() + ", " +
                COLUMN_MALAY + " = " + preferences.is_malay() + ", " +
                COLUMN_INDIAN + " = " + preferences.is_indian() + ", " +
                COLUMN_WESTERN + " = " + preferences.is_western() + ", " +
                COLUMN_KOREAN + " = " + preferences.is_korean() + ", " +
                COLUMN_SWEET + " = " + preferences.is_sweet() + ", " +
                COLUMN_SOUR + " = " + preferences.is_sour() + ", " +
                COLUMN_SPICY + " = " + preferences.is_spicy() + ", " +
                COLUMN_MEAT + " = " + preferences.is_meat() + ", " +
                COLUMN_SEAFOOD + " = " + preferences.is_seafood() + ", " +
                COLUMN_VEG + " = " + preferences.is_veg() + ", " +
                COLUMN_DESSERT + " = " + preferences.is_dessert()
                + " WHERE " +
                COLUMN_SAVE + " = 'true';"
        );
    }

    public int[] getPreferences(){
        SQLiteDatabase db = getWritableDatabase();
        int[] preferences = new int[12];
        int i = 0;


        String query = "SELECT * FROM " + TABLE_PREFERENCES + " WHERE " + COLUMN_SAVE + "='true';";
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast() && i<12){
            preferences[i] = c.getInt(0);
            i++;
            c.moveToNext();
        }

        return preferences;
    }
}
