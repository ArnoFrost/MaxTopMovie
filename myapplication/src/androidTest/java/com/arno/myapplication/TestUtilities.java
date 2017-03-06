package com.arno.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.arno.myapplication.data.MovieContract;

import java.util.Map;
import java.util.Set;

/**
 * Created by david on 2017/3/6 0006.
 */

public class TestUtilities extends AndroidTestCase {
    static final String TEST_NAME = "Hello";
    static final String TEST_DATE = "2017-03-06";

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createMovieValues() {
        ContentValues movieValues = new ContentValues();

        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, "Hello");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_DATE, "2017-03-06");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE, 8.0);
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, "Poster Path is here");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, "Overview is here");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TRAILER, "Trailer is here");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_COMMENT, "Comment is here");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE, 0);
        return movieValues;
    }
}
