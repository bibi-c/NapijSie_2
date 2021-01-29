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
                    "curr_water_demand_calc real, " +
                    "reading_datetime integer," +
                     "reading_water_amt real);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            String query = "DROP TABLE IF EXISTS water_readings;";
            db.execSQL(query);
            onCreate(db);
        }

    }


    public void insertReading(Integer reading_datetime, Float reading_water_amt, Float curr_water_demand_calc){
        ContentValues cv = new ContentValues();
        cv.put("curr_water_demand_calc", curr_water_demand_calc);
        cv.put("reading_datetime", reading_datetime);
        cv.put("reading_water_amt", reading_water_amt);
        mSqliteDatabase.insert("water_readings", null, cv);
    }

    public List<String> getReadingsAggDaily(){
        List<String> readingData = new ArrayList<>();

        String query = new String( "select substr(datetime(reading_datetime, 'unixepoch', 'start of day'), 1, 10) reading_datetime, " +
                "sum(reading_water_amt) reading_water_amt, " +
                "max(curr_water_demand_calc), " +
                "sum(reading_water_amt) / max(curr_water_demand_calc) * 100 " +
                "from water_readings " +
                "group by substr(datetime(reading_datetime, 'unixepoch', 'start of day'), 1, 10)" +
                "order by substr(datetime(reading_datetime, 'unixepoch', 'start of day'), 1, 10) desc" );
        Cursor cursor = mSqliteDatabase.rawQuery( query, null );

        if(cursor != null && cursor.moveToFirst()){
            do {
                readingData.add(cursor.getString(0) + ": " + cursor.getString(1) + "ml / " + cursor.getString(2)
                        + "ml (" + cursor.getString(3) + "%)");
            } while (cursor.moveToNext());
        }
        return readingData;
    }


    public Integer selectReadingsCnt(){
        String countQuery = "SELECT * FROM water_readings";
        Cursor cursor = mSqliteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public List<String> getReadingsAggToday(){
        List<String> readingData = new ArrayList<>();

        String query = new String( "select substr(datetime(reading_datetime, 'unixepoch', 'start of day'), 1, 10) reading_datetime, " +
                "sum(reading_water_amt) reading_water_amt, " +
                "max(curr_water_demand_calc), " +
                "sum(reading_water_amt) / max(curr_water_demand_calc) * 100 " +
                "from water_readings " +
                "where substr(datetime(reading_datetime, 'unixepoch', 'start of day'), 1, 10) = date('now', 'start of day')" +
                "group by substr(datetime(reading_datetime, 'unixepoch', 'start of day'), 1, 10)" );
        Cursor cursor = mSqliteDatabase.rawQuery( query, null );

        if(cursor != null && cursor.moveToFirst()){
            do {
                readingData.add(cursor.getString(0) + ": " + cursor.getString(1) + "ml / " + cursor.getString(2)
                                + "ml (" + cursor.getString(3) + "%)");
            } while (cursor.moveToNext());
        }
        return readingData;
    }

}