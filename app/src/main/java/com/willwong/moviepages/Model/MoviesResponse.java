package com.willwong.moviepages.Model;


import com.squareup.moshi.Json;

import java.util.List;



public class MoviesResponse {
    @Json(name = "results")
    private List<Movie> movies;

    public List<Movie> getMovieList() {
        return movies;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movies = movieList;
    }


}
