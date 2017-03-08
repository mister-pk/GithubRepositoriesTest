package com.kolpakov.githubrepositories.api;

import com.kolpakov.githubrepositories.data.models.Repositories;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by pkolpakov on 05.03.2017.
 */

public interface GithubApi{

    @GET("/search/repositories")
    Observable<Response<Repositories>> getRepositories(@Query("q") String query);
    @GET
    Observable<Response<Repositories>> getRepositoriesFromUrl(@Url String url);
}
