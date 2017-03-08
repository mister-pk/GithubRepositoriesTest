package com.kolpakov.githubrepositories.presenter;

import com.kolpakov.githubrepositories.data.models.Repository;

import java.util.List;

/**
 * Created by pkolpakov on 06.03.2017.
 */

public interface IView {
    void showRepositories(List<Repository> repositoryList);
    void showLoading();
    void showError();
}
