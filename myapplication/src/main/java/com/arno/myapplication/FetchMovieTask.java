package com.arno.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.arno.myapplication.adapter.GridAdapter;
import com.arno.myapplication.bean.MovieBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.arno.myapplication.MainActivityFragment.getMovieDetail;

/**
 * Created by david on 2017/3/7 0007.
 */

//  获取电影数据异步方法
//    传入Type ，返回数据的实体bean的List
public class FetchMovieTask extends AsyncTask<String, Void, List<MovieBean.ResultsBean>> {
    //        测试保留标签
    final String LOG_TAG = this.getClass().getSimpleName();
    private final Context mContext;
    private final GridAdapter mAdapter;

    public FetchMovieTask(Context mContext, GridAdapter mAdapter) {
        this.mContext = mContext;
        this.mAdapter = mAdapter;
    }


    @Override
    protected List<MovieBean.ResultsBean> doInBackground(String... params) {
//            检查网络连接
//        if (!isOnline()) {
//            return null;
//        }
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.

        if (params.length == 0) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(mContext);
        String language = sharedPrefs.getString(
                mContext.getString(R.string.pref_language_key),
                mContext.getString(R.string.pref_language_en));
//            http://api.themoviedb.org/3/movie/popular?language=zh&api_key=
//            http://api.themoviedb.org/3/movie/top_rated?language=zh&api_key=
        try {
//               获取电影API请求基本地址
            final String MOVIE_BASEURL = "https://api.themoviedb.org/3/movie/";
//                需要的请求地址后缀
            String USE_URL = MOVIE_BASEURL + params[0] + "?";
//                语言
            final String LANGUAGE_PARAM = "language";
//                Key
            final String API_STR = "api_key";


            Uri builtUri = Uri.parse(USE_URL).buildUpon()
                    .appendQueryParameter(LANGUAGE_PARAM, language)
                    .appendQueryParameter(API_STR, BuildConfig.MyApiKey)
                    .build();

            URL url = new URL(builtUri.toString());
//                保留测试路径正确调试方法
//                Log.d(LOG_TAG, "doInBackground: " + url);

//                创建电影数据的请求,并打开连接
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

//                将读到的数据流写入String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // 如果为空则略过下面步骤
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
//                    关于换行
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // 如果为空则略过下面步骤
                return null;
            }
            movieJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // 如果没有得到信息则不进行下一步转换
            return null;
        } finally {
            if (urlConnection != null) {
//                    关闭网络连接
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
//                        关闭流
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getMovieDetail(movieJsonStr);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return null;
    }

    //      将获取的Bean配置到适配器中并刷新
    @Override
    protected void onPostExecute(List<MovieBean.ResultsBean> result) {
        if (result != null) {
//            gridAdapter = new GridAdapter(getActivity(), result);
//            gridView.setAdapter(gridAdapter);
//            gridAdapter.notifyDataSetChanged();
////              将获取的数据存到页面的Bean中
//            useResult = result;


        }
    }

}
