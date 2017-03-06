package com.arno.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by david on 2017/3/6 0006.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + "(" +
                //ROW_ID
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY," +
                //电影ID
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                //电影名
                MovieContract.MovieEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL," +
                //上映日期
                MovieContract.MovieEntry.COLUMN_MOVIE_DATE + " TEXT NOT NULL," +
                //评分
                MovieContract.MovieEntry.COLUMN_MOVIE_VOTE + " REAL NOT NULL," +
                //海报地址
                MovieContract.MovieEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL," +
                //简介
                MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL," +
                //预告片地址
                MovieContract.MovieEntry.COLUMN_MOVIE_VIDEOS + " TEXT NOT NULL," +
                //评论
                MovieContract.MovieEntry.COLUMN_MOVIE_REVIEW + " TEXT NOT NULL," +
                //时长
                MovieContract.MovieEntry.COLUMN_MOVIE_TIME + " REAL NOT NUll," +
                //是否收藏
                MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE + " INTEGER NOT NULL DEFAULT 0" +
                ");";
        //创建数据库
        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
