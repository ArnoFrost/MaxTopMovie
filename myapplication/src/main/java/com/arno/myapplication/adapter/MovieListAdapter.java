package com.arno.myapplication.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.arno.myapplication.MainActivityFragment;
import com.arno.myapplication.R;
import com.arno.myapplication.data.BaseConfig;

import com.squareup.picasso.Picasso;

/**
 * Created by dh on 16-12-4.
 */

public class MovieListAdapter extends CursorAdapter {
    private Cursor cursor;

    public MovieListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_movies, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    public static class ViewHolder{
        public final ImageView imageView;
        public final TextView tvTitle;
        public final RatingBar rbScore;
        public final TextView tvScore;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.item_image_iv);
            tvTitle = (TextView) view.findViewById(R.id.item_title_tv);
            rbScore = (RatingBar)view.findViewById(R.id.item_score_rb);
            tvScore = (TextView) view.findViewById(R.id.item_score_tv);
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.tvTitle.setText(cursor.getString(MainActivityFragment.COL_MOVIE_TITLE));
        holder.tvScore.setText(cursor.getString(MainActivityFragment.COL_MOVIE_VOTE_AVERAGE));
        float score = Float.parseFloat(cursor.getString(MainActivityFragment.COL_MOVIE_VOTE_AVERAGE));
        holder.rbScore.setRating(score/2);
        String urlStr = cursor.getString(MainActivityFragment.COL_MOVIE_IMAGE);
        String imageUrl = BaseConfig.IMAGE_BASE_URL + urlStr;
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.mipmap.bg_loading)
                .error(R.mipmap.bg_error)
                .into(holder.imageView);
    }

}
