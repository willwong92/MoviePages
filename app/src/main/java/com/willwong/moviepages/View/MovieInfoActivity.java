package com.willwong.moviepages.View;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.willwong.moviepages.R;
import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.View.viewmodel.MovieInfoActivityViewModel;
import com.willwong.moviepages.View.viewmodel.MovieInfoActivityViewModelFactory;
import com.willwong.moviepages.utilities.InjectorUtils;
import com.willwong.moviepages.utilities.MovieProperties;
import com.willwong.moviepages.utilities.RestApi;

public class MovieInfoActivity extends AppCompatActivity {
    private static final String TAG = "MovieInfoActivity";
    MovieInfoActivityViewModel movieInfoActivityViewModel;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView backdrop;
    TextView overview;
    ImageView votingBackground;
    TextView votingText;
    Movie movie;
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

        MovieInfoActivityViewModelFactory factory = InjectorUtils.provideDetailViewModelFactory(this, movie);

        movieInfoActivityViewModel = ViewModelProviders.of(this,factory).get(MovieInfoActivityViewModel.class);


        final Observer<Movie> movieObserver = new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                Picasso.get().load(RestApi.getBackdropPath(movie.getBackDrop())).placeholder(R.drawable.image_uploading).
                        error(R.drawable.image_not_found).
                        into(backdrop);

                overview.setText("Movie overview: "+movie.getOverview());

                votingText.setText("Rating :"+new Double(movie.getReviewAverage()).toString());

                Log.d(TAG, "In movie");

            }
        };
        movieInfoActivityViewModel.getMovie().observe(this, movieObserver);



        /*Bundle bundle = getIntent().getExtras();

        Movie movie = bundle.getParcelable(MovieProperties.Movie);


        Picasso.get().load(RestApi.getBackdropPath(movie.getBackDrop())).placeholder(R.drawable.image_uploading).
                error(R.drawable.image_not_found).
                into(backdrop);

        overview.setText("Movie overview: "+movie.getOverview());

        votingText.setText("Rating :"+new Double(movie.getReviewAverage()).toString());*/



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
