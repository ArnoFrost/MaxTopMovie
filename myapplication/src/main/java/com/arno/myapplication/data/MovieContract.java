package com.arno.myapplication.data;

import android.provider.BaseColumns;

/*
*   MovieContract
*   @author arno
*   create at 2017/3/9 0009 10:52
*/

public class MovieContract implements BaseColumns {

    public static final String CONTENT_AUTHORITY = "com.arno.myapplication";
    public static final String CONTENT_BASE_URI = "content://com.arno.myapplication/movie";
    public static final String CONTENT_FAVORITE_BASE_URI = "content://com.arno.myapplication/favorite";

    public static final class MovieEntry implements BaseColumns {

        public static final String DATABASE_NAME = "movie.db";
        public static final String TABLE_NAME = "movieList";
        public static final String TABLE_NAME_FAVORITE = "favorite";
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
