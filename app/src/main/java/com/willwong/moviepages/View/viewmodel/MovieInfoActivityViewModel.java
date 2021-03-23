package com.willwong.moviepages.View.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.data.database.MovieRepository;

/**
 * Created by WillWong on 3/5/19.
 */

public class MovieInfoActivityViewModel extends ViewModel {
private LiveData<Movie> movie;
private final MovieRepository mRepository;

public MovieInfoActivityViewModel(MovieRepository repository, Movie movieData) {
    mRepository = repository;
    movie = mRepository.getMovieData(movieData.getId());
}
public LiveData<Movie> getMovie() {
    return movie;
}

}
