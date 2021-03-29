package com.willwong.moviepages.View;


import android.content.SharedPreferences;
import android.os.PersistableBundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.willwong.moviepages.R;
import com.willwong.moviepages.Model.Movie;
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
    private FragmentManager fragmentManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.willwong.moviepages.R.layout.activity_movie_list);

        fragmentManager = getSupportFragmentManager();
        MovieListFragment activeFragment = (MovieListFragment) fragmentManager.findFragmentByTag(FRAGMENT_LIST);
        if (activeFragment == null) {
            addFragment(new MovieListFragment());
            Log.d(TAG, "Launching fragment");
        }


    }

    @Override
    public void getMovie(Movie movie) {
        if (movie != null) {
            addFragment(new MovieInfoFragment(),movie);
        } else {
            Log.d(TAG, "Movie is null");
        }


    }
    public void addFragment(Fragment fragment ){
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,fragment,fragment.getTag()).
                addToBackStack(null).
                commit();
    }
    public void addFragment(Fragment fragment, Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MovieProperties.Movie, movie);
        fragment.setArguments(bundle);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,fragment,fragment.getTag()).
                addToBackStack(null).
                commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
