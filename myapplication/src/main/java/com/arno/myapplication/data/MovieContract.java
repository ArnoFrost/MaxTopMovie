package com.arno.myapplication.data;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    //Authority
    public static final String CONTENT_AUTHORITY = "com.arno.myapplication";
    //Base
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //数据库位置
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        //表名
        public static final String TABLE_NAME = "movie";
        //电影ID
        public static final String COLUMN_MOVIE_ID = "movie_id";
        //电影名
        public static final String COLUMN_MOVIE_NAME = "movie_name";
        //上映日期
        public static final String COLUMN_MOVIE_DATE = "movie_date";
        //电影评分
        public static final String COLUMN_MOVIE_VOTE = "movie_vote";
        //电影海报
        public static final String COLUMN_MOVIE_POSTER = "movie_poster";
        //电影简介
        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";
        //电影预告
        public static final String COLUMN_MOVIE_VIDEOS = "movie_videos";
        //电影评论
        public static final String COLUMN_MOVIE_REVIEW = "movie_review";
        //电影时长
        public static final String COLUMN_MOVIE_TIME = "movie_time";
        //电影收藏
        public static final String COLUMN_MOVIE_FAVORITE = "movie_favorite";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieName(String movieName) {
            return CONTENT_URI.buildUpon().appendPath(movieName).build();
        }
    }
}
