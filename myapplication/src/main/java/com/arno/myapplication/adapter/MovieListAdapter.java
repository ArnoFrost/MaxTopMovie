package com.arno.myapplication.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/*
*   MovieListAdapter
*   @author arno
*   create at 2017/3/9 0009 10:20
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

    static class ViewHolder {
//        public final ImageView imageView;
//        public final TextView tvTitle;
//        public final RatingBar rbScore;
//        public final TextView tvScore;

        @BindView(R.id.item_image_iv)
        ImageView imageView;
        @BindView(R.id.item_title_tv)
        TextView tvTitle;
        @BindView(R.id.item_score_rb_low)
        RatingBar rbScore_low;
        @BindView(R.id.item_score_rb_mid)
        RatingBar rbScore_mid;
        @BindView(R.id.item_score_high)
        RatingBar rbScore_high;
        @BindView(R.id.item_score_tv)
        TextView tvScore;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.tvTitle.setText(cursor.getString(MainActivityFragment.COL_MOVIE_TITLE));

        String urlStr = cursor.getString(MainActivityFragment.COL_MOVIE_IMAGE);
        String imageUrl = BaseConfig.IMAGE_BASE_URL + urlStr;
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.mipmap.bg_loading)
                .error(R.mipmap.bg_error)
                .into(holder.imageView);
        //      样式控制
        //      解决整数后小数点不显示
        float score = Float.parseFloat(cursor.getString(MainActivityFragment.COL_MOVIE_VOTE_AVERAGE));
        Float vote = Float.parseFloat(
                cursor.getString(MainActivityFragment.COL_MOVIE_VOTE_AVERAGE));
        holder.tvScore.setText(vote.toString());
        if (vote < 5.0) {
            //@color/ratingbar_low
            holder.tvScore.setTextColor(Color.argb(255, 121, 85, 72));
            holder.rbScore_low.setRating(score / 2);
            setView(holder.rbScore_low, holder.rbScore_mid, holder.rbScore_high);
        } else if ((vote >= 5.0) && (vote < 8.0)) {
            //@color/ratingbar_mid
            holder.tvScore.setTextColor(Color.argb(255, 0, 150, 136));
            holder.rbScore_mid.setRating(score / 2);
            setView(holder.rbScore_mid, holder.rbScore_low, holder.rbScore_high);
        } else {
            //@color/ratingbar_high
            holder.tvScore.setTextColor(Color.argb(255, 255, 110, 64));
            holder.rbScore_high.setRating(score / 2);
            setView(holder.rbScore_high, holder.rbScore_mid, holder.rbScore_low);
        }
    }

    public void setView(RatingBar show, RatingBar hide1, RatingBar hide2) {
        show.setVisibility(View.VISIBLE);
        hide1.setVisibility(View.GONE);
        hide2.setVisibility(View.GONE);
    }

}
