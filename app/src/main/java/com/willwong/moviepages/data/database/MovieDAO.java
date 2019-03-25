package com.willwong.moviepages.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.Model.MoviesResponse;

import java.util.List;

/**
 * Created by WillWong on 3/5/19.
 */

@Dao
public interface MovieDAO {
    @Query("SELECT * FROM Movies WHERE popularity_id = :id")
    LiveData<List<Movie>> getPopularMovies(int id);
    @Query("SELECT * FROM Movies WHERE top_rated_id = :id")
    LiveData<List<Movie>> getTopRatedMovies(int id);
    @Query("SELECT * FROM Movies WHERE upcoming_id = :id")
    LiveData<List<Movie>> getUpcomingMovies(int id);
    @Query("SELECT * FROM Movies WHERE nowplaying_id = :id")
    LiveData<List<Movie>> getNowPlayingMovies(int id);
    // Returns a list of all movies
    @Query("SELECT * FROM Movies")
    LiveData<List<Movie>> getAllMovies();
    // Returns a movie based on the title
    @Query("SELECT * FROM Movies WHERE id = :id")
    LiveData<Movie> getMovieByTitle(int id);
    //Insert movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> Movies);
    //Inserts a single movie
    @Insert
    void insert(Movie movie);
    //Updates a single movie
    @Update
    void update(Movie movie);
    //Deletes a single movie
    @Delete
    void delete(Movie movie);
    //removes all entries in database
    @Query("DELETE FROM Movies")
    void deleteAllMovies();
}
