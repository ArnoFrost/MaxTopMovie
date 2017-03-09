package com.arno.myapplication.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.arno.myapplication.BuildConfig;
import com.arno.myapplication.R;

import java.net.MalformedURLException;
import java.net.URL;

/*
*   BaseConfig
*   @author arno
*   create at 2017/3/9 0009 10:52
*/

public class BaseConfig {
    public static final String API_KEY = BuildConfig.MyApiKey;
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";
    public static final String GET_MOVIES_POPULAR_BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
    public static final String GET_MOVIES_TOP_RATED_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated?";

    public static final String API_KEY_PARAM = "api_key";
    public static final String PAGE_PARAM = "page";
    public static final String LANGUAGE_PARAM = "language";
    public static final String LANGUAGE_VALUE_ZH = "zh-cn";

    public static final Uri GET_POPULAR_MOVIES_URI = Uri.parse(GET_MOVIES_POPULAR_BASE_URL).buildUpon()
            .appendQueryParameter(API_KEY_PARAM, BaseConfig.API_KEY)
            .appendQueryParameter(PAGE_PARAM, "1")
            .appendQueryParameter(LANGUAGE_PARAM, BaseConfig.LANGUAGE_VALUE_ZH)
            .build();

    public static final Uri GET_TOP_RATED_MOVIES_URI = Uri.parse(GET_MOVIES_TOP_RATED_BASE_URL).buildUpon()
            .appendQueryParameter(API_KEY_PARAM, BaseConfig.API_KEY)
            .appendQueryParameter(PAGE_PARAM, "1")
            .appendQueryParameter(LANGUAGE_PARAM, BaseConfig.LANGUAGE_VALUE_ZH)
            .build();

    public static final String PLAY_VIDEO_BASE_URL = "https://www.youtube.com/watch?v=";

    private static final String MOVIE_DETAILS_BASE_URL = "http://api.themoviedb.org/3/movie/";

    public static String getMovieDetailsUrl(String id) {
        return MOVIE_DETAILS_BASE_URL + id + "?append_to_response=trailers,reviews&api_key=" + API_KEY;
    }

    public static URL getMovieListUrl(Context context) {
        Uri builtUri = getMovieListUri(context);
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static Uri getMovieListUri(Context context) {
        String moviesBaseUrl = "";
        String sortType = getMovieType(context);
        if (MovieContract.MovieEntry.GET_TYPE_VALUE_POP.equals(sortType)) {
            moviesBaseUrl = BaseConfig.GET_MOVIES_POPULAR_BASE_URL;
        } else if (MovieContract.MovieEntry.GET_TYPE_VALUE_TOP.equals(sortType)) {
            moviesBaseUrl = BaseConfig.GET_MOVIES_TOP_RATED_BASE_URL;
        }
        Uri builtUri = Uri.parse(moviesBaseUrl).buildUpon()
                .appendQueryParameter(BaseConfig.API_KEY_PARAM, BaseConfig.API_KEY)
                .appendQueryParameter(BaseConfig.PAGE_PARAM, "1")
                .appendQueryParameter(BaseConfig.LANGUAGE_PARAM, BaseConfig.LANGUAGE_VALUE_ZH)
                .build();
        return builtUri;
    }

    public static String getMovieType(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortType = prefs.getString(context.getString(R.string.pref_sortType_key), context.getString(R.string.pref_sortType_default));
        if ("0".equals(sortType)) {
            return MovieContract.MovieEntry.GET_TYPE_VALUE_POP;
        } else if ("1".equals(sortType)) {
            return MovieContract.MovieEntry.GET_TYPE_VALUE_TOP;
        }
        return null;
    }
}
