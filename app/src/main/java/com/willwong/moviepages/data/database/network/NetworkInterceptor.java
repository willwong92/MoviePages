package com.willwong.moviepages.data.database.network;

import com.willwong.moviepages.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by WillWong on 3/17/19.
 */

public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalUrl = original.url();

        HttpUrl newUrl = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build();

        Request.Builder requestBuilder = original.newBuilder().url(newUrl);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
