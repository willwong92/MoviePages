package com.willwong.moviepages.data.database.network;


import com.willwong.moviepages.utilities.RestApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by WillWong on 2/19/19.
 */

public class RetrofitClient {
    private static Retrofit sInstance;
    private static final NetworkInterceptor interceptor = new NetworkInterceptor();
    private static final OkHttpClient client = new OkHttpClient.Builder()
    .connectTimeout(10,TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build();



    public static Retrofit getsInstance() {
        if (sInstance == null) {
            sInstance = new Retrofit.Builder()
                    .baseUrl(RestApi.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return sInstance;

    }
    private RetrofitClient() {

    }

}
