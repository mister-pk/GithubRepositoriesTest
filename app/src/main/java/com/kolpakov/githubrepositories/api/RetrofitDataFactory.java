package com.kolpakov.githubrepositories.api;

import com.google.gson.GsonBuilder;
import com.kolpakov.githubrepositories.api.HttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pkolpakov on 05.02.2017.
 */

public class RetrofitDataFactory {
    private static final String GITHUB_URL = "https://api.github.com/";
    private OkHttpClient defaultHttpClient;

    public <T> T createApiService(final Class<T> service) {
        return createApiService(service, getDefaultHttpClient());
    }

    private <T> T createApiService(final Class<T> service, OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(getGitHubApiUrl())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(service);
    }

    public OkHttpClient getDefaultHttpClient() {
        if (defaultHttpClient == null) {
            defaultHttpClient = HttpClient.getHttpClient();
        }
        return defaultHttpClient;
    }

    public String getGitHubApiUrl() {
        return GITHUB_URL;
    }
}
