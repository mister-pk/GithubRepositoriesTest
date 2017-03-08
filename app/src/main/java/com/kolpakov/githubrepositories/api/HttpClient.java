package com.kolpakov.githubrepositories.api;

import android.util.Log;

import com.kolpakov.githubrepositories.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by pkolpakov on 05.02.2017.
 */

public class HttpClient extends OkHttpClient {
    private HttpClient(){

    }
    public static OkHttpClient getHttpClient(){
        Builder builder =new Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.d("HttpClient",message));
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging).build();
        }
        return builder.build();
    }

}
