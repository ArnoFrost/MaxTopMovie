package com.arno.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
*   MovieListDatabaseOpenHelper
*   @author arno
*   create at 2017/3/9 0009 10:52
*/

public class MovieListDatabaseOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public MovieListDatabaseOpenHelper(Context context) {
        super(context, MovieContract.MovieEntry.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUME_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUME_IMAGE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RELASE_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_GET_TYPE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RUNTIME + " INTEGER, " +
                MovieContract.MovieEntry.COLUMN_VIDEOS + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_REVIEWS + " TEXT " +
                ");";
        db.execSQL(sql);

        String sql1 = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME_FAVORITE + " (" +
                MovieContract.MovieEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUME_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUME_IMAGE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RELASE_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_GET_TYPE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RUNTIME + " INTEGER, " +
                MovieContract.MovieEntry.COLUMN_VIDEOS + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_REVIEWS + " TEXT " +
                ");";
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists movieList");
        onCreate(db);
    }
}
