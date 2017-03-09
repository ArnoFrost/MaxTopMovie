package com.arno.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.arno.myapplication.sync.MovieSyncAdapter;
/*
*   MainActivity
*   @author arno
*   create at 2017/3/9 0009 10:49
*/

public class MainActivity extends AppCompatActivity implements MainActivityFragment.mCallback {

    private Context mContext;
    private String mSortType;
    private SharedPreferences prefs;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        mContext = this;

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mSortType = prefs.getString(getString(R.string.pref_sortType_key), getString(R.string.pref_sortType_default));

        MovieSyncAdapter.initializeSyncAdapter(mContext);

    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * load MovieList is Popular or TopRates
         */
        String sortType = prefs.getString(getString(R.string.pref_sortType_key), getString(R.string.pref_sortType_default));

        if (sortType != null && !sortType.equals(mSortType)) {
            MainActivityFragment movieListFragment = (MainActivityFragment) getFragmentManager().findFragmentById(R.id.fragment_movie_list);
            if (null != movieListFragment) {
                movieListFragment.onSortTypeChanged();
            }
            mSortType = sortType;
        }
    }

    @Override
    public void onItemSelect(Uri movieUri) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_URI, movieUri);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.setData(movieUri);
            startActivity(intent);
        }
    }

}
