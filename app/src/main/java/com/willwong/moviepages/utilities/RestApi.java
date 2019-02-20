package com.willwong.moviepages.utilities;


public class RestApi {
    public static final int NUM_PAGES = 1;
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w342";
    public static final String BASR_BACKDROP_PATH = "http://image.tmdb.org/t/p/w780";
    public static final String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%1$s";
    public static final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%1$s/0.jpg";

    private RestApi() {
        // hide implicit public constructor
    }

    public static String getPosterPath(String posterPath) {
        return BASE_POSTER_PATH + posterPath;
    }

    public static String getBackdropPath(String backdropPath) {
        return BASR_BACKDROP_PATH + backdropPath;
    }
}
