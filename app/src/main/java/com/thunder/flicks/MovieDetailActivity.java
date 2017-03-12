package com.thunder.flicks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thunder.flicks.models.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;
    @BindView(R.id.ivMovieImage)
    ImageView ivMovieImage;
    @BindView(R.id.rbVote)
    RatingBar rbVote;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        Picasso.with(this).load(movie.getBackdropPath())
                .transform(new RoundedCornersTransformation(10,10))
                .placeholder(R.drawable.land_placeholder)
                .into(ivMovieImage);
        tvTitle.setText(movie.getTitle());
        rbVote.setRating((float) (movie.getVoteAverage()/2.0));
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText("Release Date: " + movie.getReleaseDate());
    }

    public void playVideo(View view) {
        Intent intent = new Intent(MovieDetailActivity.this, QuickPlayActivity.class);
        intent.putExtra("id", movie.getId());
        startActivity(intent);
    }
}
