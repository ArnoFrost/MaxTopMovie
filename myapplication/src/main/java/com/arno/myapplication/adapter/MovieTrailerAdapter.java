package com.arno.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.arno.myapplication.R;
import com.arno.myapplication.bean.MovieTrailer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
*   MovieTrailerAdapter
*   @author arno
*   create at 2017/3/9 0009 10:35
*/

public class MovieTrailerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MovieTrailer> movieTrailerArrayList;

    public MovieTrailerAdapter(Context context, ArrayList<MovieTrailer> movieTrailers) {
        this.context = context;
        this.movieTrailerArrayList = movieTrailers;
    }

    @Override
    public int getCount() {
        return movieTrailerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieTrailerArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        //        TextView tvType;
//        TextView tvName;
//        TextView tvSize;
        @BindView(R.id.trailer_name_tv)
        TextView tvName;
        @BindView(R.id.trailer_type_tv)
        TextView tvType;
        @BindView(R.id.trailer_size_tv)
        TextView tvSize;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_trailer, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MovieTrailer trailer = movieTrailerArrayList.get(position);
        holder.tvName.setText(trailer.name);
        holder.tvType.setText("[" + trailer.type + "]");
        holder.tvSize.setText(trailer.size);

        return convertView;
    }
}
