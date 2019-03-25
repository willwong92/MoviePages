package com.willwong.moviepages.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.willwong.moviepages.AppExecutors;
import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.Model.MoviesResponse;
import com.willwong.moviepages.data.database.network.MovieNetworkDataSource;
import com.willwong.moviepages.utilities.RestApi;
import com.willwong.moviepages.utilities.Topics;

import java.util.List;

/**
 * Created by WillWong on 3/8/19.
 */

public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();

    //For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieRepository sInstance;
    private final MovieDAO mMovieDao;
    private final MovieNetworkDataSource mMovieNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private MovieRepository(MovieDAO movieDao,
                               MovieNetworkDataSource weatherNetworkDataSource,
                               AppExecutors executors) {
        mMovieDao = movieDao;
        mMovieNetworkDataSource = weatherNetworkDataSource;
        mExecutors = executors;

        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        LiveData<List<Movie>> networkData = mMovieNetworkDataSource.getCurrentMovies();
        networkData.observeForever(newMoviesFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deletes old historical data
                deleteOldData();
                Log.d(TAG, "Old movie deleted");
                // Insert our new movie data into MoviePages database
                mMovieDao.insertMovies(newMoviesFromNetwork);
                Log.d(TAG, "New values inserted");
            });
        });
    }

    public synchronized static MovieRepository getInstance(MovieDAO movieDAO, MovieNetworkDataSource movieNetworkDataSource,
                                                           AppExecutors appExecutors) {
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieRepository(movieDAO, movieNetworkDataSource, appExecutors);
                Log.d(TAG, "Created new repository");
            }
        }
        return sInstance;
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    public synchronized void initializeData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        // This method call triggers Sunshine to create its task to synchronize weather data
        // periodically.
        mMovieNetworkDataSource.scheduleRecurringFetchMovieSync();

        mExecutors.diskIO().execute(() -> {
                startFetchMovieService();

        });
    }
    public void deleteOldData() {
        mMovieDao.deleteAllMovies();
    }

    private void startFetchMovieService() {
        mMovieNetworkDataSource.startFetchMovieService();
    }

    public LiveData<List<Movie>> getMovieList() {
        initializeData();
        return mMovieDao.getAllMovies();

    }

    public LiveData<Movie> getMovieData(int id){
        initializeData();
        return mMovieDao.getMovieByTitle(id);

    }

    public LiveData<List<Movie>> getPopularMoviesList() {
        initializeData();
        return mMovieDao.getPopularMovies(Topics.POPULAR_ORDER);

    }
    public LiveData<List<Movie>> getTopRatedMoviesList(){
        initializeData();
        return mMovieDao.getTopRatedMovies(Topics.TOP_RATED_ORDER);
    }
    public LiveData<List<Movie>> getUpcomingMoviesList() {
        initializeData();
        return mMovieDao.getUpcomingMovies(Topics.UPCOMING_ORDER);

    }
    public LiveData<List<Movie>> getNowPlayingMovies() {
        initializeData();
        return mMovieDao.getNowPlayingMovies(Topics.NOW_PLAYING_ORDER);
    }



}
