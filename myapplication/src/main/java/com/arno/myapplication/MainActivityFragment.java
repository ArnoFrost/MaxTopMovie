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
            FetchMovieTask movieTask = new FetchMovieTask(getActivity(),gridAdapter);
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
            FetchMovieTask movieTask = new FetchMovieTask(getActivity(),gridAdapter);
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
