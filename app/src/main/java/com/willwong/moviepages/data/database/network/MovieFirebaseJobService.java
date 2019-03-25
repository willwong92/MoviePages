package com.willwong.moviepages.data.database.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.willwong.moviepages.utilities.InjectorUtils;

/**
 * Created by WillWong on 3/11/19.
 */

public class MovieFirebaseJobService extends JobService {
    private static final String TAG = MovieFirebaseJobService.class.getSimpleName();
    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     * <p>
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
     */
    @Override
    public boolean onStartJob(final JobParameters job) {
        Log.d(TAG, "Job service started");

        MovieNetworkDataSource networkDataSource = InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchMovie();

        jobFinished(job, false);
        return true;
    }

    /**
     * Called when the scheduling engine has decided to interrupt the execution of a running job,
     * most likely because the runtime constraints associated with the job are no longer satisfied.
     *
    */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
