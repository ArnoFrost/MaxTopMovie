package com.arno.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.arno.myapplication.R;
import com.arno.myapplication.bean.MovieReview;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
*   MovieReviewAdapter
*   @author arno
*   create at 2017/3/9 0009 10:22
*/

public class MovieReviewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MovieReview> movieReviewArrayList;

    public MovieReviewAdapter(Context context, ArrayList<MovieReview> movieReviews) {
        this.context = context;
        this.movieReviewArrayList = movieReviews;
    }

    @Override
    public int getCount() {
        return movieReviewArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieReviewArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
//        TextView tvReviewAuthor;
//        TextView tvReviewContent;

        @BindView(R.id.review_author_tv)
        TextView tvReviewAuthor;
        @BindView(R.id.review_content_tv)
        TextView tvReviewContent;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false);
//            holder = new ViewHolder();
//            holder.tvReviewAuthor = (TextView) convertView.findViewById(R.id.review_author_tv);
//            holder.tvReviewContent = (TextView) convertView.findViewById(R.id.review_content_tv);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MovieReview review = movieReviewArrayList.get(position);
        holder.tvReviewAuthor.setText(review.author);
        holder.tvReviewContent.setText(review.content);
        return convertView;
    }
}
