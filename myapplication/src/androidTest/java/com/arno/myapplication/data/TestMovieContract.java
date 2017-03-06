package com.arno.myapplication.data;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.arno.myapplication.data.MovieContract;

/**
 * Created by david on 2017/3/6 0006.
 */

public class TestMovieContract extends AndroidTestCase {
    private static final String TEST_MOVIE_NAME = "/Hello";
    private static final long TEST_MOVIE_DATE = 1419003600L;

    public void testBuildMovie() {
        Uri movieUri = MovieContract.MovieEntry.buildMovieName(TEST_MOVIE_NAME);
        assertNotNull("Error: Null Uri returned. You must fill-in buildMovieUri in " +
                "MovieContract.", movieUri);
        assertEquals("Error: Movie Name not properly appended to the end of the Uri",
                TEST_MOVIE_NAME, movieUri.getLastPathSegment());
        assertEquals("Error : Movie name Uri dosen't match our expected result",
                movieUri.toString(),
                "content://com.arno.myapplication/movie/%2FHello");
    }
}
