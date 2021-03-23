package com.willwong.moviepages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import android.content.Context;
import androidx.annotation.Nullable;

import com.willwong.moviepages.Model.Movie;
import com.willwong.moviepages.data.database.MovieDAO;
import com.willwong.moviepages.data.database.MovieDatabase;
import com.willwong.moviepages.utilities.Topics;

import junit.framework.TestCase;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


import androidx.test.core.app.ApplicationProvider;

/**
 * Created by WillWong on 3/21/19.
 */
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4.class)
public class NetworkRequestsTest extends TestCase {
    private MovieDAO dao;
    private MovieDatabase db;
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, MovieDatabase.class).build();
        dao = db.movieDAO();
    }
    @After
    public void closeDb(){
        db.close();
    }

    @Test
    public void writeMovieandRead() throws Exception {
        List<Movie> list = new ArrayList<>(20);
        for (Movie movie: list) {
            movie.setPopularity_id(Topics.POPULAR_ORDER);
        }
        dao.insertMovies(list);
        LiveData<List<Movie>> response = dao.getPopularMovies(Topics.POPULAR_ORDER);
        response.observe(ApplicationProvider.getApplicationContext(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                assertEquals(movies, list);
            }
        });

    }
    @Test
    public void isNullorEmpty() throws Exception {
        List<Movie> movies = new ArrayList<>(20);
        for (Movie movie: movies) {
            movie.setPopularity_id(Topics.POPULAR_ORDER);
        }
        dao.insertMovies(movies);
        assertNotNull(dao.getPopularMovies(Topics.POPULAR_ORDER));
    }
}
