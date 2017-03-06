package com.arno.myapplication.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.arno.myapplication.data.MovieContract;
import com.arno.myapplication.data.MovieProvider;

/**
 * Created by david on 2017/3/6 0006.
 */

public class TestUriMatcher extends AndroidTestCase {
    private static final String MOVIE_QUERY = "";

    private static final Uri TEST_MOVIE_DIR = MovieContract.MovieEntry.CONTENT_URI;


    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The Moive URI was matched incrrectly.",
                testMatcher.match(TEST_MOVIE_DIR), MovieProvider.MOVIE);
    }
}
