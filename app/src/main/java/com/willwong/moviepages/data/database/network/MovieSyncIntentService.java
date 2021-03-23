package com.willwong.moviepages.data.database.network;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.util.Log;

import com.willwong.moviepages.utilities.InjectorUtils;

/**
 * Created by WillWong on 3/8/19.
 */

public class MovieSyncIntentService extends IntentService {
    private static final String TAG = MovieSyncIntentService.class.getSimpleName();

    public MovieSyncIntentService() {
        super("MovieSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "Intent service started");
        MovieNetworkDataSource networkDataSource =
                InjectorUtils.provideNetworkDataSource(this.getApplication());
        networkDataSource.fetchMovie();
    }
}
