package com.willwong.moviepages.View.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.data.database.MovieRepository;

import java.util.List;

/**
 * Created by WillWong on 3/14/19.
 */

public class MovieListActivityViewModel extends ViewModel {
    private final LiveData<List<Movie>> listMovies;
    private final MovieRepository mRepository;

    public MovieListActivityViewModel(MovieRepository repository) {
        mRepository = repository;
        listMovies = mRepository.getMovieList();
    }


    public LiveData<List<Movie>> getMoviesList() {
        return listMovies;
    }

    public LiveData<List<Movie>> getPopularMoviesList() { return mRepository.getPopularMoviesList(); }

    public LiveData<List<Movie>> getTopRatedMoviesList() { return mRepository.getTopRatedMoviesList(); }

    public LiveData<List<Movie>> getUpcomingMoviesList() { return mRepository.getUpcomingMoviesList(); }

    public LiveData<List<Movie>> getNowPlayingMoviesList() {return mRepository.getNowPlayingMovies(); }

    public LiveData<Movie> getMovie(Movie movie) {return mRepository.getMovieData(movie.getId());}
}
