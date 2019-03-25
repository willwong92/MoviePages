package com.willwong.moviepages.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.willwong.moviepages.Model.Movie;

/**
 * Created by WillWong on 3/5/19.
 */

@Database(entities = {Movie.class}, version = 2)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String TAG = MovieDatabase.class.getClass().getSimpleName();
    private static final String DATABASE_NAME = "movie";

    //For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context) {
        Log.d(TAG, "Fetching database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, MovieDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
                Log.d(TAG, "Created new database");
            }
        }
        return sInstance;
    }
    public abstract MovieDAO movieDAO();
}
