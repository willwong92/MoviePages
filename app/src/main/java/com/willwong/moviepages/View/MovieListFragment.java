package com.willwong.moviepages.View;

import androidx.lifecycle.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.*;

import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.Model.MoviesResponse;
import com.willwong.moviepages.View.viewmodel.MovieListActivityViewModel;
import com.willwong.moviepages.View.viewmodel.MovieListActivityViewModelFactory;
import com.willwong.moviepages.data.database.network.MovieAPIService;
import com.willwong.moviepages.data.database.network.RetrofitClient;
import com.willwong.moviepages.utilities.InjectorUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WillWong on 11/15/18.
 */

public class MovieListFragment extends Fragment {
    private static final String TAG = MovieListFragment.class.getSimpleName();
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    public static final String SORTING_PREFERENCES = "sortPrefs";
    private SharedPreferences pref;

    MovieListActivityViewModel viewModel;

    MovieAPIService service;
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

        //pref = getActivity().getSharedPreferences(SORTING_PREFERENCES, Context.MODE_PRIVATE);

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


        MovieListActivityViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(getActivity());
        viewModel = ViewModelProviders.of(this,factory).get(MovieListActivityViewModel.class);

       LiveData<List<Movie>> response  = viewModel.getUpcomingMoviesList();

       response.observe(this, new androidx.lifecycle.Observer<List<Movie>>() {
           @Override
           public void onChanged(@Nullable List<Movie> movies) {
               if (movies != null) {
                   mAdapter.setmDataset(movies);
               } else {
                   Log.d(TAG, "Fragment : Empty retrieval");
               }
       }});



           /*Retrofit retrofit = RetrofitClient.getsInstance();

           service = retrofit.create(MovieAPIService.class);

           Observable<MoviesResponse> call = service.topRatedMovies(RestApi.NUM_PAGES);

           call.subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Observer<MoviesResponse>() {
                       @Override
                       public void onSubscribe(Disposable d) {

                       }

                       @Override
                       public void onNext(MoviesResponse moviesResponse) {
                            if(moviesResponse != null && moviesResponse.getMovieList() != null) {
                                Log.i(TAG,"onNext: Request successful!");
                                movies = moviesResponse.getMovieList();
                            }
                       }

                       @Override
                       public void onError(Throwable e) {
                            Log.e(TAG,"onError " + e.getMessage());
                            System.out.println("you are here");
                       }

                       @Override
                       public void onComplete() {
                           mAdapter.setmDataset(movies);
                       }
                   });*/


        return rootView;
    }
    public void updateListOfMoviesSearch(String query) {
        if (query != null) {
            service = RetrofitClient.getsInstance().create(MovieAPIService.class);
            Call<MoviesResponse> call = service.searchMovies(query);

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.i(TAG, "code :" + response.code());
                        return;
                    }
                    MoviesResponse movieList = response.body();

                    movies = movieList.getMovieList();
                    mAdapter.setmDataset(movies);


                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                }
            });
        }


    }
    public void updateListOfMoviesSort(int sort_id) {
        Observable<MoviesResponse> call;
        LiveData<List<Movie>> response;
        switch (sort_id) {
            case SORT_ORDER_POPULAR:
                response = viewModel.getPopularMoviesList();
                response.observe(this, new androidx.lifecycle.Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        for (Movie movie : movies) {
                            System.out.println("popular movies " + movie.getPopularity_id());
                        }
                        if (movies == null || movies.isEmpty()) {
                            Log.e(TAG, "Empty retrieval");
                        } else {
                            mAdapter.setmDataset(movies);
                        }
                    }
                });
                /*service = RetrofitClient.getsInstance().create(MovieAPIService.class);

                call = service.popularMovies(RestApi.NUM_PAGES);

                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MoviesResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(MoviesResponse moviesResponse) {
                                if(moviesResponse != null && moviesResponse.getMovieList() != null) {
                                    Log.i(TAG,"onNext: Popular movies sort successful");
                                    mAdapter.setmDataset(moviesResponse.getMovieList());

                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG,"onError " + e.getMessage());
                                System.out.println("you are here");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });*/
            case SORT_ORDER_UPCOMING:
                /*
                response = viewModel.getUpcomingMoviesList();
                response.observe(this, new android.arch.lifecycle.Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        for (Movie movie : movies) {
                            System.out.println("upcoming " + movie.getUpcoming_id());
                        }
                        if (movies == null || movies.isEmpty()) {
                            Log.e(TAG, "upcoming: Empty retrieval");
                        } else {
                            mAdapter.setmDataset(movies);
                        }
                    }
                });*/
                service = RetrofitClient.getsInstance().create(MovieAPIService.class);

                call = service.upcomingMovies();

                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MoviesResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(MoviesResponse moviesResponse) {
                                if(moviesResponse != null && moviesResponse.getMovieList() != null) {
                                    Log.i(TAG,"onNext: upcoming movies successful");
                                    mAdapter.setmDataset(moviesResponse.getMovieList());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG,"onError" + e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            case SORT_ORDER_TOPRATED:
                response = viewModel.getTopRatedMoviesList();
                response.observe(this, new androidx.lifecycle.Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        for (Movie movie : movies) {
                            System.out.println("toprated " + movie.getTop_rated_id());
                        }
                        if (movies == null || movies.isEmpty()) {
                            Log.e(TAG, "upcoming: Empty retrieval");
                        } else {
                            mAdapter.setmDataset(movies);
                        }
                    }
                });
            case SORT_ORDER_NOWPLAYING:
                response = viewModel.getNowPlayingMoviesList();
                response.observe(this, new androidx.lifecycle.Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        for (Movie movie : movies) {
                            System.out.println("nowplaying " + movie.getNowplaying_id());
                        }
                        if (movies == null || movies.isEmpty()) {
                            Log.e(TAG, "upcoming: Empty retrieval");
                        } else {
                            mAdapter.setmDataset(movies);
                        }
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
