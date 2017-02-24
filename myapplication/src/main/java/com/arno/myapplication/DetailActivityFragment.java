package com.arno.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
//       分数显示后缀
        final String ADD_VOTE = "/10";
//        从Intent中获取到需要的数据
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("detail_title")) {
            String detail_title = intent.getStringExtra("detail_title");
            String detail_poster = intent.getStringExtra("detail_poster");
            String detail_releaseDate = intent.getStringExtra("detail_releaseDate");
            double detail_vote = intent.getDoubleExtra("detail_vote", 1.00);
            String detail_overView = intent.getStringExtra("detail_overView");

//           显示内容
            ((TextView) rootView.findViewById(R.id.detail_title)).setText(detail_title);
            ((TextView) rootView.findViewById(R.id.detail_vote_average)).setText(String.valueOf(detail_vote) + ADD_VOTE);
            ((TextView) rootView.findViewById(R.id.detail_title)).setText(detail_title);
//             Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);

//            Picasso.with(getContext()).load(detail_poster)
//                    .into(((ImageView) rootView.findViewById(R.id.detail_poster)));
//            使用Picasso加载图片
            Picasso.with(getContext())
                    .load(detail_poster)
                    .placeholder(R.drawable.loading1)
                    .into(((ImageView) rootView.findViewById(R.id.detail_poster)), new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
            ((TextView) rootView.findViewById(R.id.detail_overView)).setText(detail_overView);

        }
        return rootView;
    }
}
