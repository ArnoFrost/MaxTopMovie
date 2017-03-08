package com.arno.myapplication.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/*
* Created by david on 17-03-08
* */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.arno.myapplication";
    public static final Uri CONTENT_BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String CONTENT_BASE_URI_STRING = "content://com.arno.myapplication/movie";
    public static final String CONTENT_FAVORITE_BASE_URI = "content://com.arno.myapplication/favorite";
    public static final String DATABASE_NAME = "movie.db";

    static final String PATH_MOVIE = "movie";
    static final String PATH_FAVORITE = "favorite";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                CONTENT_BASE_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;



        public static final String TABLE_NAME = "movieList";
        public static final String COLUMN_ID = "_id";
        public static final String COLUME_TITLE = "title";
        public static final String COLUME_IMAGE = "image";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_RELASE_DATE = "releaseDate";
        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_GET_TYPE = "getType";
        public static final String GET_TYPE_VALUE_POP = "pop";
        public static final String GET_TYPE_VALUE_TOP = "top";

        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_VIDEOS = "videos";
        public static final String COLUMN_REVIEWS = "reviews";

    }

    public static final class FavorEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                CONTENT_BASE_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_ID = "_id";
        public static final String COLUME_TITLE = "title";
        public static final String COLUME_IMAGE = "image";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_RELASE_DATE = "releaseDate";
        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_GET_TYPE = "getType";
        public static final String GET_TYPE_VALUE_POP = "pop";
        public static final String GET_TYPE_VALUE_TOP = "top";

        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_VIDEOS = "videos";
        public static final String COLUMN_REVIEWS = "reviews";
    }
}
