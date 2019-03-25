package com.willwong.moviepages.View;


import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.willwong.moviepages.R;
import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.utilities.InjectorUtils;
import com.willwong.moviepages.utilities.MovieProperties;
import com.willwong.moviepages.utilities.Topics;

public class MovieListActivity extends AppCompatActivity implements MovieListFragment.MovieCallBackListener {
    public static final String SORTING_PREFERENCES = "sortPrefs";
    public static final String TAG ="MovieListActivity";
    private static final String FRAGMENT_LIST = "list_fragment";
    private static final String CURRENT_MOVIE = "movie_current";
    private SharedPreferences pref;
    private int SORT_ORDER;
    private SearchView searchView;
    private MovieListFragment movieListFragment;
    private FragmentManager fragmentManager;
    private Movie currentMovie;
    private Movie selectMovie;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.willwong.moviepages.R.layout.activity_movie_list);
        initToolBar();


        fragmentManager = getSupportFragmentManager();
        MovieListFragment activeFragment = (MovieListFragment) fragmentManager.findFragmentByTag(FRAGMENT_LIST);
        if (activeFragment == null) {

            movieListFragment = new MovieListFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, movieListFragment, FRAGMENT_LIST)
                    .commit();
            Log.d(TAG, "Launching fragment");
        } else {
            movieListFragment = activeFragment;
        }


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(CURRENT_MOVIE)) {
                selectMovie = savedInstanceState.getParcelable(CURRENT_MOVIE);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void initToolBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public void getMovie(Movie movie) {
        if (movie != null) {
            startMovieInfoActivity(movie);
        } else {
            Log.d(TAG, "Movie is null");
        }


    }
    public void startMovieInfoActivity(Movie movie) {
        currentMovie = movie;
        Bundle bundle = new Bundle();
        Intent intent = new Intent(MovieListActivity.this, MovieInfoActivity.class);
        bundle.putParcelable(MovieProperties.Movie,movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListFragment.updateListOfMoviesSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular_movies:

                movieListFragment.updateListOfMoviesSort(Topics.POPULAR_ORDER);
            return true;
            case R.id.upcoming_movies:

                movieListFragment.updateListOfMoviesSort(Topics.UPCOMING_ORDER);
                return true;
            case R.id.toprated_movies:

                movieListFragment.updateListOfMoviesSort(Topics.TOP_RATED_ORDER);
                return true;
            case R.id.nowplaying_movies:

                movieListFragment.updateListOfMoviesSort(Topics.NOW_PLAYING_ORDER);
                return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (currentMovie != null) {
            outState.putParcelable(CURRENT_MOVIE, currentMovie);
        }
    }


}
