package com.arno.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("detail_title")) {
            String detail_title = intent.getStringExtra("detail_title");
            String detail_poster = intent.getStringExtra("detail_poster");
            String detail_releaseDate = intent.getStringExtra("detail_releaseDate");
            double detail_vote = intent.getDoubleExtra("detail_vote", 1.00);
            String detail_overView = intent.getStringExtra("detail_overView");
            ((TextView) rootView.findViewById(R.id.detail_title)).setText(detail_title);
            ((TextView) rootView.findViewById(R.id.detail_vote_average)).setText(String.valueOf(detail_vote));
            ((TextView) rootView.findViewById(R.id.detail_title)).setText(detail_title);
//             Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
            Picasso.with(getContext()).load(detail_poster)
                    .into(((ImageView) rootView.findViewById(R.id.detail_poster)));
            ((TextView) rootView.findViewById(R.id.detail_overView)).setText(detail_overView);

        }
        return rootView;
    }
}
