package com.willwong.moviepages.data.database.network;

import com.willwong.moviepages.Model.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by WillWong on 1/29/19.
 */

public interface MovieAPIService {
    //
    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    Observable<MoviesResponse> popularMovies(@Query("page") int page);

    @GET("3/movie/now_playing?language=en-US&page=1")
    Observable<MoviesResponse> nowPlayingMovies();

    @GET("3/movie/upcoming?language=en-US&page=1")
    Observable<MoviesResponse> upcomingMovies();

    @GET("3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc")
    Observable<MoviesResponse> topRatedMovies(@Query("page") int page);

    @GET("3/discover/movie?language=en&sort_by=release_date.desc")
    Call<MoviesResponse> newestMovies(@Query("release_date.lte") String maxReleaseDate, @Query("vote_count.gte") int minVoteCount);

    @GET("3/movie/{movieId}/videos")
    Call<MoviesResponse> trailers(@Path("movieId") String movieId);

    @GET("3/movie/{movieId}/reviews")
    Call<MoviesResponse> reviews(@Path("movieId") String movieId);

    @GET("3/search/movie?language=en-US&page=1")
    Call<MoviesResponse> searchMovies(@Query("query") String searchQuery);
}
