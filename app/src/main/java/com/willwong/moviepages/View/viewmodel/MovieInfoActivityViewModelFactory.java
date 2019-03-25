package com.willwong.moviepages.View.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.data.database.MovieRepository;

/**
 * Created by WillWong on 3/12/19.
 */

public class MovieInfoActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieRepository mRepository;
    private final Movie movie;

    public MovieInfoActivityViewModelFactory(MovieRepository repository, Movie movie) {
        mRepository = repository;
        this.movie = movie;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieInfoActivityViewModel(mRepository, movie);
    }
}
