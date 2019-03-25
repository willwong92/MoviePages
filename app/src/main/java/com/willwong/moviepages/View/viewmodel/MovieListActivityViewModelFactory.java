package com.willwong.moviepages.View.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.Model.MoviesResponse;
import com.willwong.moviepages.data.database.MovieRepository;

import java.util.List;

/**
 * Created by WillWong on 3/18/19.
 */

public class MovieListActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieRepository repository;

    public MovieListActivityViewModelFactory(MovieRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieListActivityViewModel(repository);
    }
}
