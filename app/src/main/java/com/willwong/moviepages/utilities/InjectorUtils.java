package com.willwong.moviepages.utilities;

import android.content.Context;

import com.willwong.moviepages.AppExecutors;
import com.willwong.moviepages.View.viewmodel.MovieListActivityViewModelFactory;
import com.willwong.moviepages.data.database.MovieDatabase;
import com.willwong.moviepages.data.database.MovieRepository;
import com.willwong.moviepages.data.database.network.MovieNetworkDataSource;

/**
 * Created by WillWong on 3/10/19.
 */

public class InjectorUtils {
    public static MovieRepository provideRepository(Context context) {
        MovieDatabase database = MovieDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        MovieNetworkDataSource networkDataSource =
                MovieNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return MovieRepository.getInstance(database.movieDAO(), networkDataSource, executors);
    }

    public static MovieNetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return  MovieNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static MovieListActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        MovieRepository repository = provideRepository(context.getApplicationContext());
        return new MovieListActivityViewModelFactory(repository);
    }
}
