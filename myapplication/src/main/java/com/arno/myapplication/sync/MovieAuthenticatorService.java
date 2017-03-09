package com.arno.myapplication.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/*
*   MovieAuthenticatorService
*   @author arno
*   create at 2017/3/9 0009 10:51
*/

public class MovieAuthenticatorService extends Service {

    private MovieAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new MovieAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
