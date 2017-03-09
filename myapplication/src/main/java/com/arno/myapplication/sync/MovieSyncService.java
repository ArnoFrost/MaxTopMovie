package com.arno.myapplication.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/*
*   MovieSyncService
*   @author arno
*   create at 2017/3/9 0009 10:52
*/

public class MovieSyncService extends Service {
    private static MovieSyncAdapter sMovieSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sMovieSyncAdapter == null) {
                sMovieSyncAdapter = new MovieSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sMovieSyncAdapter.getSyncAdapterBinder();
    }
}
