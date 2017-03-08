package com.kolpakov.githubrepositories.api;

import com.kolpakov.githubrepositories.data.models.PageLinks;
import com.kolpakov.githubrepositories.data.models.Repositories;

import rx.Observable;

/**
 * Created by pkolpakov on 06.03.2017.
 */

public class WebGithubDataStore {
    private GithubApi githubApi;

    public WebGithubDataStore() {
        this.githubApi = new RetrofitDataFactory().createApiService(GithubApi.class);
    }
    public Observable<Repositories> getRepositories(String query){
        return githubApi.getRepositories(query).map(repositoriesResponse -> {
            Repositories repositories = repositoriesResponse.body();
            if(repositories != null){
                repositories.setPageLinks(new PageLinks(repositoriesResponse));
            }
            return repositories;
        });
    }
    public Observable<Repositories> getRepositoriesFromUrl(String url){
        return githubApi.getRepositoriesFromUrl(url).map(repositoriesResponse -> {
            Repositories repositories = repositoriesResponse.body();
            if(repositories != null){
                repositories.setPageLinks(new PageLinks(repositoriesResponse));
            }
            return repositories;
        });
    }

}
