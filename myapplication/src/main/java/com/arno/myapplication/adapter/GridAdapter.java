package com.arno.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.arno.myapplication.R;
import com.arno.myapplication.bean.MovieBean;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by david on 2017/2/23 0023.
 * 自定义Picasso的GridView适配器
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInfalter;
    private List<MovieBean.ResultsBean> mList;

    public GridAdapter(Context context, List<MovieBean.ResultsBean> list) {
        mContext = context;
        mInfalter = LayoutInflater.from(mContext);
        mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        ViewHolder holder;
        if (converView == null) {
            converView = mInfalter.inflate(R.layout.main_item, parent, false);
            holder = new ViewHolder(converView);
            converView.setTag(holder);
        } else {
            holder = (ViewHolder) converView.getTag();
        }
//        Picasso.with(mContext).load(mList.get(position).getPoster_path()).into(holder.image);

        Picasso.with(mContext)
                .load(mList.get(position).getPoster_path())
                .placeholder(R.drawable.loading1)
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Toast.makeText(mContext, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
        return converView;
    }


    //    Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    static class ViewHolder {
        @BindView(R.id.main_imageView)
        ImageView image;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}