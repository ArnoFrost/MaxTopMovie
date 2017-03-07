package com.arno.myapplication.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by david on 2017/3/6 0006.
 */

public class TestProvider extends AndroidTestCase {
    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                MovieProvider.class.getName());
        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            assertEquals("Error MovieProvider registered with authority:" + providerInfo.authority +
                            "instead of authoriy: " + MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    private void deleteAllRecords() {
        deleteAllRecordsFromDB();
    }

    private void deleteAllRecordsFromDB() {
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(MovieContract.MovieEntry.TABLE_NAME, null, null);
        db.close();
    }

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Movie table during delete", 0, cursor.getCount());
        cursor.close();

    }

    public void testGetType() {
        String type = mContext.getContentResolver().getType(MovieContract.MovieEntry.CONTENT_URI);
        assertEquals("Error: the MovieEntry CONTENT_URI should return MoiveEntry.CONTENT_TYPE",
                MovieContract.MovieEntry.CONTENT_TYPE, type);
    }

    public void testBasicMovieQuery() {
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long movieRowId = 328111;

        ContentValues movieValues = TestUtilities.createMovieValues(movieRowId);

        long testId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, movieValues);
        assertTrue("Unable to Insert MovieEntry into the Database", testId != -1);
        db.close();

        Cursor movieCursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        TestUtilities.validateCursor("testBasicMovieQuery", movieCursor, movieValues);
    }

    public void testInsertReadProvider() {
        long id = 12345;
        ContentValues testValues = TestUtilities.createMovieValues(id);
        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();

        mContext.getContentResolver().registerContentObserver(MovieContract.MovieEntry.CONTENT_URI, true, tco);

        Uri movieInsertUri = mContext.getContentResolver()
                .insert(MovieContract.MovieEntry.CONTENT_URI, testValues);
        assertTrue(movieInsertUri != null);

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);
        // A cursor is your primary interface to the query results.
        Cursor weatherCursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null // columns to group by
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error validating MovieEntry insert.",
                weatherCursor, testValues);


    }

    public void testUpadteMovie() {
        ContentValues values = TestUtilities.createMovieValues(123456);

        Uri movieUri = mContext.getContentResolver()
                .insert(MovieContract.MovieEntry.CONTENT_URI, values);
        long id = ContentUris.parseId(movieUri);

        assertTrue(id != -1);
        Log.d(LOG_TAG, "testUpadteMovie: " + id);

        ContentValues updateValues = new ContentValues(values);
        updateValues.put(MovieContract.MovieEntry._ID, id);
        updateValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, "Hellotest！！！");

        Cursor movieCursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        movieCursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(
                MovieContract.MovieEntry.CONTENT_URI, updateValues, MovieContract.MovieEntry._ID + "=? ",
                new String[]{Long.toString(id)}
        );
        assertEquals(count, 1);
        // Test to make sure our observer is called.  If not, we throw an assertion.
        //
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();

        movieCursor.unregisterContentObserver(tco);
        movieCursor.close();

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,   // projection
                MovieContract.MovieEntry._ID + " = " + id,
                null,   // Values for the "where" clause
                null    // sort order
        );

        TestUtilities.validateCursor("testUpdateLocation.  Error validating location entry update.",
                cursor, updateValues);

        cursor.close();
    }

    public void testDeleteRecords() {
        testInsertReadProvider();

        TestUtilities.TestContentObserver movieObserber = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieContract.MovieEntry.CONTENT_URI, true, movieObserber);

        deleteAllRecordsFromProvider();

        movieObserber.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(movieObserber);
    }

    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;

    static ContentValues[] createBulkInsertMovieValues(long movieId) {
        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues movieValues = new ContentValues();
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieId);
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, "Hello");
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_DATE, "2017-03-06");
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE, 8.2F);
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, "Poster Path is here");
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, "Overview is here");
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VIDEOS, "Trailer is here");
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_REVIEW, "Comment is here");
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TIME, 1236.12F);
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE, 0);
            returnContentValues[i] = movieValues;
        }
        return returnContentValues;
    }

//    public void testBulkInsert() {
//
//        ContentValues[] bulkInsertContentValues = createBulkInsertMovieValues(1);
//        TestUtilities.TestContentObserver movieObserver = TestUtilities.getTestContentObserver();
//        mContext.getContentResolver().registerContentObserver(MovieContract.MovieEntry.CONTENT_URI, true, movieObserver);
//        int insertCount = mContext.getContentResolver().bulkInsert(
//                MovieContract.MovieEntry.CONTENT_URI, bulkInsertContentValues
//        );
//        movieObserver.waitForNotificationOrFail();
//        mContext.getContentResolver().unregisterContentObserver(movieObserver);
//
//
//        assertEquals(insertCount, BULK_INSERT_RECORDS_TO_INSERT);
//
//
//        Cursor cursor = mContext.getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//
//        assertEquals(cursor.getCount(), BULK_INSERT_RECORDS_TO_INSERT);
//
//        cursor.moveToNext();
//
//        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext()) {
//            TestUtilities.validateCursor("testBulkInsert. Error validating MovieEntry" + i,
//                    cursor, bulkInsertContentValues[i]);
//
//        }
//        cursor.close();
//
//
//    }
}
