package com.example.napijsie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    public static final String DATABASE_NAME = "data";

    private Context mContext;
    private MyDBHelper mDbHelper;
    private SQLiteDatabase mSqliteDatabase;
    private int DATABASE_VERSION = 1;

    public DBAdapter(Context context){
        this.mContext = context;
        mDbHelper = new MyDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        mSqliteDatabase = mDbHelper.getWritableDatabase();
    }

    public class MyDBHelper extends SQLiteOpenHelper{
        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            String query = "CREATE TABLE app_setup(id integer primary key autoincrement, sex integer, age integer, weight real, phys_activity_type integer, calc_daily_water_demand_ml real);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            String query = "DROP TABLE IF EXISTS app_setup;";
            db.execSQL(query);
            onCreate(db);
        }
    }


    public String selectAppSetupString(String col_name){
        String col_value = new String();

        Cursor cursor = mSqliteDatabase.query("app_setup", new String[]{col_name}, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            col_value = cursor.getString(cursor.getColumnIndex(col_name));
        }
        return col_value;
    }

    public Double selectAppSetupReal(String col_name){
        Double col_value = new Double(-1);

        Cursor cursor = mSqliteDatabase.query("app_setup", new String[]{col_name}, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            col_value = cursor.getDouble(cursor.getColumnIndex(col_name));
        }
        return col_value;
    }

    public Integer selectAppSetupInt(String col_name){
        Integer col_value = new Integer(-1);

        Cursor cursor = mSqliteDatabase.query("app_setup", new String[]{col_name}, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            col_value = cursor.getInt(cursor.getColumnIndex(col_name));
        }
        return col_value;
    }

    public List<String> selectSetup(){
        List<String> setupData = new ArrayList<>();
        Cursor cursor = mSqliteDatabase.query("app_setup", null, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            do {
                setupData.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return setupData;
    }


    public Integer selectSetupCnt(){
        String countQuery = "SELECT * FROM app_setup";
        Cursor cursor = mSqliteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void insertSetup(Integer sex, Integer age, Double weight, Integer phys_activity_type, Double calc_daily_water_demand_ml){
        ContentValues cv = new ContentValues();
        cv.put("sex", sex);
        cv.put("age", age);
        cv.put("weight", weight);
        cv.put("phys_activity_type", phys_activity_type);
        cv.put("calc_daily_water_demand_ml", calc_daily_water_demand_ml);
        mSqliteDatabase.insert("app_setup", null, cv);
    }


    public void clearSetup(){
        String countQuery = "delete FROM app_setup";
        Cursor cursor = mSqliteDatabase.rawQuery(countQuery, null);
        cursor.close();
    }
}