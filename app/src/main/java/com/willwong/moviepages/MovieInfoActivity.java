package com.willwong.moviepages;


import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.willwong.moviepages.model.Movie;
import com.willwong.moviepages.utilities.MovieProperties;
import com.willwong.moviepages.utilities.RestApi;

public class MovieInfoActivity extends AppCompatActivity {
    private static final String TAG = "MovieInfoActivity";
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView backdrop;
    TextView overview;
    ImageView votingBackground;
    TextView votingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        initToolBar();

        backdrop = (ImageView) findViewById(R.id.backdrop_id);
        overview = (TextView)findViewById(R.id.overview_id);
        votingBackground = (ImageView)findViewById(R.id.voting_background);
        votingText = (TextView)findViewById(R.id.voting_average_id);



        Bundle bundle = getIntent().getExtras();

        Movie movie = bundle.getParcelable(MovieProperties.Movie);


        Picasso.get().load(RestApi.getBackdropPath(movie.getBackDrop())).placeholder(R.drawable.image_uploading).
                error(R.drawable.image_not_found).
                into(backdrop);

        overview.setText("Movie overview: "+movie.getOverview());

        votingText.setText("Rating :"+new Double(movie.getVoteAverage()).toString());



    }
    public void initToolBar() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Overview");
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        collapsingToolbarLayout.setTitleEnabled(true);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }
}
