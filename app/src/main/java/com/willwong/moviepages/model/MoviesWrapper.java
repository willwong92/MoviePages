package com.willwong.moviepages.model;

import com.squareup.moshi.Json;

import java.util.List;



public class MoviesWrapper {

    @Json(name = "results")
    private List<Movie> movies;

    public List<Movie> getMovieList() {
        return movies;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movies = movieList;
    }
}
