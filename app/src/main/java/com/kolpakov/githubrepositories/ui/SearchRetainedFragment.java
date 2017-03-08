package com.kolpakov.githubrepositories.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kolpakov.githubrepositories.presenter.SearchGithubPresenter;

/**
 * Created by pkolpakov on 07.03.2017.
 */

public class SearchRetainedFragment extends Fragment {
    private SearchGithubPresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setPresenter(SearchGithubPresenter presenter) {
        this.presenter = presenter;
    }

    public SearchGithubPresenter getPresenter() {
        return presenter;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null)
            presenter.onDestroy();
    }
}
