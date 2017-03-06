package com.arno.myapplication.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;


import com.arno.myapplication.util.PollingCheck;

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

    static ContentValues createMovieValues(long id) {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,id);
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, "Hello");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_DATE, "2017-03-06");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE, 8.2F);
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, "Poster Path is here");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, "Overview is here");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VIDEOS, "Trailer is here");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_REVIEW, "Comment is here");
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TIME, 1236.12F);
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE, 0);
        return movieValues;
    }

    /*
    Students: The functions we provide inside of TestProvider use this utility class to test
    the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
    CTS tests.

    Note that this only tests that the onChange function is called; it does not test that the
    correct Uri is returned.
 */
    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
