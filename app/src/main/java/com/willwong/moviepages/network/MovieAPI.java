package com.willwong.moviepages.network;

import com.willwong.moviepages.model.Movie;
import com.willwong.moviepages.model.MoviesWrapper;
import com.willwong.moviepages.model.Review;
import com.willwong.moviepages.model.ReviewsWrapper;
import com.willwong.moviepages.model.Video;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by WillWong on 1/29/19.
 */

public interface MovieAPI {
    //
    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    Call<MoviesWrapper> popularMovies(@Query("page") int page,
                                      @Query("api_key")String api_key);

    @GET("3/movie/now_playing?language=en-US&page=1")
    Call<MoviesWrapper> nowPlayingMovies(@Query("api_key") String api_key);

    @GET("3/movie/upcoming?language=en-US&page=1")
    Call<MoviesWrapper> upcomingMovies(@Query("api_key") String api_key);

    @GET("3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc")
    Call<MoviesWrapper> topRatedMovies(@Query("page") int page,
                                       @Query("api_key") String api_key);

    @GET("3/discover/movie?language=en&sort_by=release_date.desc")
    Call<MoviesWrapper> newestMovies(@Query("release_date.lte") String maxReleaseDate,@Query("vote_count.gte") int minVoteCount);

    @GET("3/movie/{movieId}/videos")
    Call<MoviesWrapper> trailers(@Path("movieId") String movieId);

    @GET("3/movie/{movieId}/reviews")
    Call<ReviewsWrapper> reviews(@Path("movieId") String movieId);

    @GET("3/search/movie?language=en-US&page=1")
    Call<MoviesWrapper> searchMovies(@Query("query") String searchQuery,
                                  @Query("api_key")String api_key);
}
