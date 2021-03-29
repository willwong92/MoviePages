package com.willwong.moviepages.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.willwong.moviepages.R;
import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.View.viewmodel.MovieListActivityViewModel;
import com.willwong.moviepages.View.viewmodel.MovieListActivityViewModelFactory;
import com.willwong.moviepages.utilities.InjectorUtils;
import com.willwong.moviepages.utilities.MovieProperties;
import com.willwong.moviepages.utilities.RestApi;

public class MovieInfoFragment extends Fragment {
    private static final String TAG = "MovieInfoActivity";
    MovieListActivityViewModel movieListActivityViewModel;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView backdrop;
    TextView overview;
    ImageView votingBackground;
    TextView votingText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_info, container, false);

        initToolBar(rootView);

        setHasOptionsMenu(true);

        backdrop =  rootView.findViewById(R.id.backdrop_id);
        overview = rootView.findViewById(R.id.overview_id);
        votingBackground = rootView.findViewById(R.id.voting_background);
        votingText = rootView.findViewById(R.id.voting_average_id);

        Bundle bundle = getArguments();

        Movie movie = bundle.getParcelable(MovieProperties.Movie);

        MovieListActivityViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(getActivity());

        movieListActivityViewModel = new ViewModelProvider(requireActivity(),factory).get(MovieListActivityViewModel.class);

        movieListActivityViewModel.getMovie(movie).observe(getActivity(), new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                if (movie != null) {
                    overview.setText("Movie overview: " + movie.getOverview());

                    votingText.setText("Rating :" + new Double(movie.getReviewAverage()).toString());

                    Picasso.get().load(RestApi.getBackdropPath(movie.getBackDrop())).placeholder(R.drawable.image_uploading).
                            error(R.drawable.image_not_found).
                            into(backdrop);
                    Log.d(TAG, "In movie");
                }
            }
        });

        return rootView;

    }

    public void initToolBar(View rootView) {
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Overview");
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        collapsingToolbarLayout.setTitleEnabled(true);

        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
