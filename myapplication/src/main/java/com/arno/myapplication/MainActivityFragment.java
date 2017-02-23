package com.arno.myapplication;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.arno.myapplication.bean.MovieBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.arno.myapplication.JsonUtil.getObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    //    private ArrayAdapter<String> mMovieAdapter;
    private GridAdapter gridAdapter;
    private GridView gridView;
    MovieBean movieBean = new MovieBean();


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            FetchMovieTask movieTask = new FetchMovieTask();
            movieTask.execute("popular");
//          top_rated
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        String[] data = {"请连接网络并刷新"};

//        List<String> movieTop = new ArrayList<String>(Arrays.asList(data));
//        mMovieAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                R.layout.item_movie,
//                R.id.list_item_movie_textview,
//                movieTop
//        );
//        mMovieAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                R.layout.main_item,
//                R.id.main_imageView,
//                movieTop
//        );


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        ListView listView = (ListView) rootView.findViewById(R.id.listview);
//        listView.setAdapter(mMovieAdapter);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
//        gridAdapter = new GridAdapter(getActivity(), movieBean.getResults());
        gridView.setAdapter(gridAdapter);

        return rootView;
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<MovieBean.ResultsBean>> {

        private final String LOG_TAG = this.getClass().getSimpleName();

        @Override
        protected List<MovieBean.ResultsBean> doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;
            String language = "zh";
//            String key = "4fdda96b2d325b623e85842ddce5ddd4";
//            http://api.themoviedb.org/3/movie/popular?language=zh&api_key=
//            http://api.themoviedb.org/3/movie/top_rated?language=zh&api_key=
            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                final String MOVIE_BASEURL = "https://api.themoviedb.org/3/movie/";
                String USE_URL = MOVIE_BASEURL + params[0] + "?";
                final String LANGUAGE_PARAM = "language";
                final String API_STR = "api_key";


                Uri builtUri = Uri.parse(USE_URL).buildUpon()
                        .appendQueryParameter(LANGUAGE_PARAM, language)
                        .appendQueryParameter(API_STR, BuildConfig.MyApiKey)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.d(LOG_TAG, "doInBackground: " + url);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
//                TODO 解决Gson问题
//                getMovieDetail(movieJsonStr);
//                Log.d(LOG_TAG, "doInBackground: " + forecastJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
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

        @Override
        protected void onPostExecute(List<MovieBean.ResultsBean> result) {
            if (result != null) {
//                gridAdapter.clear();
                gridAdapter = new GridAdapter(getActivity(), result);
                gridView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();

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


        MovieBean movieBean = getObject(movieJsonstr, MovieBean.class);
//        TODO Picasso加载问题待解决
//        Log.d("test", "getMovieDetail: " + movieBean.getResults());
        List<MovieBean.ResultsBean> resultBean = movieBean.getResults();
//        String[] resultStrs = new String[resultBean.size()];
        for (int i = 0; i < resultBean.size(); i++) {
            MovieBean.ResultsBean useResultBean = resultBean.get(i);
            title = useResultBean.getTitle();
            poster_path = useResultBean.getPoster_path();
            overview = useResultBean.getOverview();
            vote_average = useResultBean.getVote_average();
            release_date = useResultBean.getRelease_date();
//            resultStrs[i] = poster_path;
            Log.d("test", "getMovieDetail: " + poster_path);
        }
        return resultBean;
    }


}
