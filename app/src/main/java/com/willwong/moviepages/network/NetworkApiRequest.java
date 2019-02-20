package com.willwong.moviepages.network;

import android.os.Build;

import com.willwong.moviepages.BuildConfig;
import com.willwong.moviepages.model.MoviesWrapper;
import com.willwong.moviepages.utilities.RestApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by WillWong on 2/19/19.
 */

public class NetworkApiRequest {

    public static Call<MoviesWrapper> searchMovieRequest(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        return movieAPI.searchMovies(query, BuildConfig.TMDB_API_KEY);
    }

    public static Call<MoviesWrapper> popularMovieRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        return movieAPI.popularMovies(RestApi.NUM_PAGES,BuildConfig.TMDB_API_KEY);
    }

    public static Call<MoviesWrapper> upcomingMovieRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        return movieAPI.upcomingMovies(BuildConfig.TMDB_API_KEY);
    }
    public static Call<MoviesWrapper> nowPlayingMovieRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);
        return movieAPI.nowPlayingMovies(BuildConfig.TMDB_API_KEY);
    }

    public static Call<MoviesWrapper> topRatedMovieRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);
        return movieAPI.topRatedMovies(RestApi.NUM_PAGES, BuildConfig.TMDB_API_KEY);
    }
}
