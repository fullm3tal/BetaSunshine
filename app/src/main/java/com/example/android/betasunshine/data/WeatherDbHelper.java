package com.example.android.betasunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dakaku on 20/4/17.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weatherDatabase.db";
    public static final int DATABASE_VERSION = 9;

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String DATABASE_CREATE_TABLE = "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME + " (" +
                WeatherContract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeatherContract.WeatherEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_MAX + " TEXT NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_MIN + " TEXT NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_PRESSURE + " TEXT NOT NULL, " +
                WeatherContract.WeatherEntry.COLUMN_HUMIDITY + " TEXT NOT NULL " + ");";
        db.execSQL(DATABASE_CREATE_TABLE);
        Log.v("TABLE ", DATABASE_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + WeatherContract.WeatherEntry.TABLE_NAME);
        onCreate(db);
    }
}
