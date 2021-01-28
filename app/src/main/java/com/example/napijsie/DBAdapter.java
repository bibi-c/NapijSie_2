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
            String query =
                    "CREATE TABLE water_readings(" +
                    "id integer primary key autoincrement," +
                    "sex integer, " +
                    "age integer," +
                     "weight real," +
                     "phys_activity_type integer," +
                      "calc_daily_water_demand_ml real);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            String query = "DROP TABLE IF EXISTS water_readings;";
            db.execSQL(query);
            onCreate(db);
        }
    }

}