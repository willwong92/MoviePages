package com.willwong.moviepages.data.database.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import com.willwong.moviepages.AppExecutors;
import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.Model.MoviesResponse;
import com.willwong.moviepages.utilities.RestApi;
import com.willwong.moviepages.utilities.Topics;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by WillWong on 3/8/19.
 */

public class MovieNetworkDataSource {
    public static final int NUM_ENTRIES = 20;
    private static final String TAG = MovieNetworkDataSource.class.getSimpleName();

    //Interval at which to sync the movie data.
    private static final int SYNC_INTERVAL_HOURS = 2;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static final String MOVIE_SYNC_TAG = "movie-sync";

    //Singleton
    private static final Object LOCK = new Object();
    private static MovieNetworkDataSource sInstance;
    private final Context mContext;

    //LiveData storing the latest downloaded movie data;
    private final MutableLiveData<List<Movie>> mDownloadedMovies;
    private final AppExecutors mExecutors;

    private MovieNetworkDataSource(Context context, AppExecutors mExecutors) {
        mContext = context;
        this.mExecutors = mExecutors;
        mDownloadedMovies = new MutableLiveData<>();
    }

    // Singleton instance
    public static MovieNetworkDataSource getInstance(Context context, AppExecutors mExecutors) {
        Log.d(TAG, "Fetching the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieNetworkDataSource(context.getApplicationContext(), mExecutors);
                Log.d(TAG, "Made new network data source");
            }
        }
        return sInstance;

    }
    public LiveData<List<Movie>> getCurrentMovies() {
        return mDownloadedMovies;
    }


    public void startFetchMovieService() {
        Intent intent = new Intent (mContext, MovieSyncIntentService.class);
        mContext.startService(intent);
        Log.d(TAG, "Service created");
    }


    /**
     * Schedules a repeating job service which fetches the movie data.
     */
    public void scheduleRecurringFetchMovieSync() {
        Driver driver = new GooglePlayDriver(mContext);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        //Create the job to periodically sync MoviePages
        Job syncMovieJob = dispatcher.newJobBuilder()
        // The service that will be used to syn MoviePage's data
        .setService(MovieFirebaseJobService.class)
        //Set the UNIQUE tag used to identify this Job
        .setTag(MOVIE_SYNC_TAG)
        /*
         * Network constraints on which this Job should run. We choose to run on any
         * network, but you can also choose to run only on un-metered networks or when the
          * device is charging. It might be a good idea to include a preference for this,
          * as some users may not want to download any data on their mobile plan. ($$$)
          */
        .setConstraints(Constraint.ON_ANY_NETWORK)
        /*
          * setLifetime sets how long this job should persist. The options are to keep the
          * Job "forever" or to have it die the next time the device boots up.
          */
        .setLifetime(Lifetime.FOREVER)
        /*
         * We want Sunshine's weather data to stay up to date, so we tell this Job to recur.
         */
        .setRecurring(true)
        /*
         * We want the movie data to be synced every 3 to 4 hours. The first argument for
         * Trigger's static executionWindow method is the start of the time frame when the
         * sync should be performed. The second argument is the latest point in time at
         * which the data should be synced. Please note that this end time is not
         * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
         */
        .setTrigger(Trigger.executionWindow(
                SYNC_INTERVAL_SECONDS,
                SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
        /*
         * If a Job with the tag with provided already exists, this new job will replace
         * the old one.
         */
        .setReplaceCurrent(true)
        /* Once the Job is ready, call the builder's build method to return the Job */
        .build();

        // Schedule the Job with the dispatcher
        dispatcher.schedule(syncMovieJob);
        Log.d(TAG, "Job scheduled");
    }

    void fetchMovie() {
        Log.d(TAG, "Fetch movie started");
        mExecutors.networkIO().execute(() -> {
            try {

                Retrofit retrofit = RetrofitClient.getsInstance();

                retrofit.create(MovieAPIService.class).topRatedMovies(RestApi.NUM_PAGES)
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MoviesResponse>() {
                                       List<Movie> movies;

                                       @Override
                                       public void onSubscribe(Disposable d) {

                                       }

                                       @Override
                                       public void onNext(MoviesResponse moviesResponse) {
                                           if (moviesResponse != null && moviesResponse.getMovieList() != null) {
                                               Log.i(TAG, "onNext: Request successful!");
                                               movies = moviesResponse.getMovieList();
                                               for (Movie movie : movies) {
                                                   movie.setTop_rated_id(Topics.TOP_RATED_ORDER);
                                               }
                                           }
                                       }

                                       @Override
                                       public void onError(Throwable e) {
                                           Log.e(TAG, "onError " + e.getMessage());
                                           System.out.println("you are here");
                                       }

                                       @Override
                                       public void onComplete() {
                                           mDownloadedMovies.postValue(movies);
                                       }
                                   });



                /*MovieAPIService service = RetrofitClient.getsInstance().create(MovieAPIService.class);

                Observable<MoviesResponse> r1 = service.topRatedMovies(RestApi.NUM_PAGES)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                Observable<MoviesResponse> r2 = service.popularMovies(RestApi.NUM_PAGES)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                Observable<MoviesResponse> r3 =  service.upcomingMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                Observable<MoviesResponse> r4 = service.nowPlayingMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                Observable.zip(r1, r2, r3, r4, new Function4<MoviesResponse, MoviesResponse, MoviesResponse, MoviesResponse, MoviesResponse>() {
                    @Override
                    public MoviesResponse apply(MoviesResponse moviesResponse, MoviesResponse moviesResponse2, MoviesResponse moviesResponse3, MoviesResponse moviesResponse4) throws Exception {
                        MoviesResponse list = new MoviesResponse();
                        for(Movie movie : moviesResponse.getMovieList()) {
                            movie.setPopularity_id(Topics.POPULAR_ORDER);
                        }
                        list.appendMovieList(moviesResponse.getMovieList());
                        for (Movie movie: moviesResponse2.getMovieList()) {
                            movie.setTop_rated_id(Topics.TOP_RATED_ORDER);
                        }
                        list.appendMovieList(moviesResponse2.getMovieList());
                        for (Movie movie : moviesResponse3.getMovieList()) {
                            movie.setUpcoming_id(Topics.UPCOMING_ORDER);
                        }
                        list.appendMovieList(moviesResponse3.getMovieList());
                        for (Movie movie : moviesResponse4.getMovieList()) {
                            movie.setNowplaying_id(Topics.NOW_PLAYING_ORDER);
                        }
                        list.appendMovieList(moviesResponse4.getMovieList());
                        return list;
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MoviesResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(MoviesResponse moviesResponses) {

                                mDownloadedMovies.postValue(moviesResponses.getMovieList());
                                Log.d(TAG,"OnNext;");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete");
                            }

                            @Override
                            public void onError(Throwable t) {
                                Log.d(TAG, "onError :"+t.getMessage());
                            }
                        });*/
                /*Observable<List<MoviesResponse>> result = Observable.zip(r1.subscribeOn(Schedulers.io()), r2.subscribeOn(Schedulers.io()), r3.subscribeOn(Schedulers.io()),
                        r4.subscribeOn(Schedulers.io()), new Function4<MoviesResponse, MoviesResponse, MoviesResponse, MoviesResponse, List<MoviesResponse>>() {
                            @Override
                            public List<MoviesResponse> apply(MoviesResponse moviesResponse, MoviesResponse moviesResponse2, MoviesResponse moviesResponse3, MoviesResponse moviesResponse4) throws Exception {
                                ArrayList<MoviesResponse> list = new ArrayList<>();
                                for(Movie movie : moviesResponse.getMovieList()) {
                                    movie.setPopularity_id(Topics.POPULAR_ORDER);
                                }
                                list.add(moviesResponse);
                                for (Movie movie: moviesResponse2.getMovieList()) {
                                    movie.setTop_rated_id(Topics.TOP_RATED_ORDER);
                                }
                                list.add(moviesResponse2);
                                for (Movie movie : moviesResponse3.getMovieList()) {
                                    movie.setUpcoming_id(Topics.UPCOMING_ORDER);
                                }
                                list.add(moviesResponse3);
                                for (Movie movie : moviesResponse4.getMovieList()) {
                                    movie.setNowplaying_id(Topics.NOW_PLAYING_ORDER);
                                }
                                list.add(moviesResponse4);
                                return list;
                            }
                        });
                result
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<MoviesResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MoviesResponse> moviesResponses) {
                        for (MoviesResponse response: moviesResponses) {
                            mDownloadedMovies.postValue(response.getMovieList());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
