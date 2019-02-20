package com.willwong.moviepages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.willwong.moviepages.model.Movie;
import com.willwong.moviepages.model.MoviesWrapper;
import com.willwong.moviepages.network.MovieAPI;
import com.willwong.moviepages.network.NetworkApiRequest;
import com.willwong.moviepages.utilities.RestApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by WillWong on 11/15/18.
 */

public class MovieListFragment extends Fragment {
    private static final String TAG = MovieListFragment.class.getSimpleName();
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private static final int SORT_ORDER_POPULAR = 1;
    private static final int SORT_ORDER_UPCOMING = 2;
    private static final int SORT_ORDER_TOPRATED = 3;
    private static final int SORT_ORDER_NOWPLAYING = 4;
    protected RecyclerView mRecyclerView;
    protected MovieListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    List<Movie> movies = new ArrayList<>(25);
    //Callback to pass movie to parent activity
    MovieCallBackListener movieListener;


    public interface MovieCallBackListener {
        public void getMovie(Movie movie);
    }
    //Empty constructor for modularity and fragment reuse
    public MovieListFragment() {

    }
    public static MovieListFragment getInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setArguments(args);
        return movieListFragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            movieListener = (MovieCallBackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement MovieCallListener in Activity");
        }
    }

    // This method is called when the Fragment instance is being created, or re-created.
    //Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retrieve arguements from newInstance
        setRetainInstance(true);

    }
    //Called when Fragment shuold create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(com.willwong.moviepages.R.layout.fragment_recycler_view, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(com.willwong.moviepages.R.id.my_recycler_view);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        int col = 2;
        mLayoutManager = new GridLayoutManager(getActivity(), col);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieListAdapter(movies, getContext(), movieListener);
        //Set MovieListAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);



           Call<MoviesWrapper> call = NetworkApiRequest.popularMovieRequest();


            call.enqueue(new Callback<MoviesWrapper>() {
                @Override
                public void onResponse(Call<MoviesWrapper> call, Response<MoviesWrapper> response) {
                    if (!response.isSuccessful()) {
                        Log.i(TAG, "code :" + response.code());
                        return;
                    }
                    MoviesWrapper movieList = response.body();

                    movies = movieList.getMovieList();
                    mAdapter.setmDataset(movies);


                }

                @Override
                public void onFailure(Call<MoviesWrapper> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                }
            });


        return rootView;
    }
    public void updateListOfMoviesSearch(String query) {
        if (query != null) {

            Call<MoviesWrapper> call = NetworkApiRequest.searchMovieRequest(query);

            call.enqueue(new Callback<MoviesWrapper>() {
                @Override
                public void onResponse(Call<MoviesWrapper> call, Response<MoviesWrapper> response) {
                    if (!response.isSuccessful()) {
                        Log.i(TAG, "code :" + response.code());
                        return;
                    }
                    MoviesWrapper movieList = response.body();

                    movies = movieList.getMovieList();
                    mAdapter.setmDataset(movies);


                }

                @Override
                public void onFailure(Call<MoviesWrapper> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                }
            });
        }


    }
    public void updateListOfMoviesSort(int sort_id) {
        switch (sort_id) {
            case SORT_ORDER_POPULAR:
                NetworkApiRequest.popularMovieRequest()
                .enqueue(new Callback<MoviesWrapper>() {
                    @Override
                    public void onResponse(Call<MoviesWrapper> call, Response<MoviesWrapper> response) {
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "code :" + response.code());
                            return;
                        }
                        MoviesWrapper movieList = response.body();

                        movies = movieList.getMovieList();
                        mAdapter.setmDataset(movies);


                    }

                    @Override
                    public void onFailure(Call<MoviesWrapper> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
            case SORT_ORDER_UPCOMING:
                NetworkApiRequest.upcomingMovieRequest()
                .enqueue(new Callback<MoviesWrapper>() {
                    @Override
                    public void onResponse(Call<MoviesWrapper> call, Response<MoviesWrapper> response) {
                        if (!response.isSuccessful()) {
                            Log.i(TAG, "code :" + response.code());
                            return;
                        }
                        MoviesWrapper movieList = response.body();

                        movies = movieList.getMovieList();
                        mAdapter.setmDataset(movies);


                    }

                    @Override
                    public void onFailure(Call<MoviesWrapper> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
            case SORT_ORDER_TOPRATED:
            NetworkApiRequest.topRatedMovieRequest()
                    .enqueue(new Callback<MoviesWrapper>() {
                        @Override
                        public void onResponse(Call<MoviesWrapper> call, Response<MoviesWrapper> response) {
                            if (!response.isSuccessful()) {
                                Log.i(TAG, "code :" + response.code());
                                return;
                            }
                            MoviesWrapper movieList = response.body();

                            movies = movieList.getMovieList();
                            mAdapter.setmDataset(movies);


                        }

                        @Override
                        public void onFailure(Call<MoviesWrapper> call, Throwable t) {
                            Log.d(TAG, t.getMessage());
                        }
                    });
            case SORT_ORDER_NOWPLAYING:
                NetworkApiRequest.nowPlayingMovieRequest()
                        .enqueue(new Callback<MoviesWrapper>() {
                            @Override
                            public void onResponse(Call<MoviesWrapper> call, Response<MoviesWrapper> response) {
                                if (!response.isSuccessful()) {
                                    Log.i(TAG, "code :" + response.code());
                                    return;
                                }
                                MoviesWrapper movieList = response.body();

                                movies = movieList.getMovieList();
                                mAdapter.setmDataset(movies);


                            }

                            @Override
                            public void onFailure(Call<MoviesWrapper> call, Throwable t) {
                                Log.d(TAG, t.getMessage());
                            }
                        });
                }

    }
    //This event is triggered soon after onCreateView().
    //onCreatedView() is only called if the view returned from onCreateView is non-null.
    //Any view setup should occur here. E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





    }
    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    // This method is called when the fragment is no longer connected to the Activity
    // Any references saved in onAttach should be nulled out here to prevent memory leaks.
    @Override
    public void onDetach() {
        super.onDetach();

    }


}
