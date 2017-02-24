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

import com.arno.myapplication.bean.MovieBean;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @BindView(R.id.detail_title)
    TextView detail_title;
    @BindView(R.id.detail_vote_average)
    TextView detail_vote;
    @BindView(R.id.datail_release_date)
    TextView detail_date;
    @BindView(R.id.detail_poster)
    ImageView detail_poster;
    @BindView(R.id.detail_overView)
    TextView detail_overView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
//       分数显示后缀
        final String ADD_VOTE = "/10";
//        从Intent中获取到需要的数据
        final Intent intent = getActivity().getIntent();
        if (intent != null) {


            MovieBean.ResultsBean movie = (MovieBean.ResultsBean) intent.getSerializableExtra("Movie");
            if (movie != null) {
                //           显示内容
                detail_title.setText(movie.getTitle());
                detail_vote.setText(String.valueOf(movie.getVote_average()) + ADD_VOTE);
                detail_date.setText(movie.getRelease_date());
                detail_overView.setText(movie.getOverview());

//            使用Picasso加载图片
//             Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
                Picasso.with(getContext())
                        .load(movie.getPoster_path())
                        .placeholder(R.drawable.loading1)
                        .into(detail_poster, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });

            }


        }

        return rootView;
    }
}
