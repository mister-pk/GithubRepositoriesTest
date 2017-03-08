package com.kolpakov.githubrepositories.data;

import com.kolpakov.githubrepositories.api.WebGithubDataStore;
import com.kolpakov.githubrepositories.data.models.Repositories;

import rx.Observable;

/**
 * Created by pkolpakov on 05.03.2017.
 */

public class GithubRepository {
    private WebGithubDataStore dataStore;
    public GithubRepository() {
        dataStore = new WebGithubDataStore();
    }
    public Observable<Repositories> getRepositories(String query){
        return dataStore.getRepositories(query);
    }
    public Observable<Repositories> getRepositoriesFromUrl(String url){
        return dataStore.getRepositoriesFromUrl(url);
    }
}
