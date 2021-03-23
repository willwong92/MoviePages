package com.willwong.moviepages.View.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.willwong.moviepages.data.database.MovieRepository;

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
