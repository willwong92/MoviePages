package com.willwong.moviepages.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.willwong.moviepages.Model.Movie;

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
