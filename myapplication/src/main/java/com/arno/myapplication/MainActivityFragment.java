package com.arno.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.arno.myapplication.adapter.GridAdapter;
import com.arno.myapplication.bean.MovieBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.arno.myapplication.util.JsonUtil.getObject;


public class MainActivityFragment extends Fragment {
    //    声明全局GridView和Adapter
    private GridAdapter gridAdapter;
//    使用butterknife
    @BindView(R.id.gridView)
    GridView gridView;
    //    声明缓存Bean实体
    List<MovieBean.ResultsBean> useResult = null;


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
//            执行AsyncTask任务
            FetchMovieTask movieTask = new FetchMovieTask();
//          从sharedPrefrence获取数据
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String type = prefs.getString(getString(R.string.pref_type_key),
                    getString(R.string.pref_type_popular));
            movieTask.execute(type);
//          type 值可为 top_rated或popular
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        if (!isOnline()) {
            Toast.makeText(getContext(), "Sorry！No network", Toast.LENGTH_SHORT).show();
        } else {
            //            自动执行AsyncTask任务
            FetchMovieTask movieTask = new FetchMovieTask();
//          从sharedPrefrence获取数据
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String type = prefs.getString(getString(R.string.pref_type_key),
                    getString(R.string.pref_type_popular));
            //          type 值可为 top_rated或popular
            movieTask.execute(type);
        }


        //  为GridView绑定监听
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//              通过Intent传入到详情页面
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("Movie", useResult.get(i));
                startActivity(intent);
            }
        });
        return rootView;
    }

    //      检查网络连接
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    //  获取电影数据异步方法
//    传入Type ，返回数据的实体bean的List
    public class FetchMovieTask extends AsyncTask<String, Void, List<MovieBean.ResultsBean>> {
        //        测试保留标签
        final String LOG_TAG = this.getClass().getSimpleName();

        @Override
        protected List<MovieBean.ResultsBean> doInBackground(String... params) {
//            检查网络连接
            if (!isOnline()) {
                return null;
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.

            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String language = prefs.getString(getString(R.string.pref_language_key), getString(R.string.pref_language_en));

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
                gridAdapter = new GridAdapter(getActivity(), result);
                gridView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();
//              将获取的数据存到页面的Bean中
                useResult = result;


            }
        }

    }

    //  自定义解析电影所需信息方法
    public static List<MovieBean.ResultsBean> getMovieDetail(String movieJsonstr) {
//      海报路径
        String poster_path;
//       剧情简介
        String overview;
//      上映日期
        String release_date;
//      标题
        String title;
//      评分
        double vote_average;
//      将Json字符串存到实体Bean中
        MovieBean movieBean = getObject(movieJsonstr, MovieBean.class);
//      获得到结果Result的List集合
        List<MovieBean.ResultsBean> resultBean = movieBean.getResults();
//       返回结果Bean的List集合
        return resultBean;
    }


}
