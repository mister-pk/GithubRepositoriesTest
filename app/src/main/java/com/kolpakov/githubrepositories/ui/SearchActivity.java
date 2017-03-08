package com.kolpakov.githubrepositories.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.kolpakov.githubrepositories.presenter.IView;
import com.kolpakov.githubrepositories.R;
import com.kolpakov.githubrepositories.data.models.Repository;
import com.kolpakov.githubrepositories.presenter.SearchGithubPresenter;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements IView {

    private static final String FRAGMENT_PRESENTER = "presenter";

    private RepositoriesAdapter adapter;
    private SearchGithubPresenter presenter;
    private SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swiperefresh.setEnabled(false);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.search(newText);
                return true;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RepositoriesAdapter(this);
        adapter.setLoadMoreListener(() -> presenter.loadNext());
        recyclerView.setAdapter(adapter);
        FragmentManager fm = getSupportFragmentManager();
        SearchRetainedFragment dataFragment = (SearchRetainedFragment) fm.findFragmentByTag(FRAGMENT_PRESENTER);

        if (dataFragment == null) {
            presenter = new SearchGithubPresenter();
            dataFragment = new SearchRetainedFragment();
            fm.beginTransaction().add(dataFragment, FRAGMENT_PRESENTER).commit();
            dataFragment.setPresenter(presenter);
        } else {
            presenter = dataFragment.getPresenter();
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onDetachView();
    }

    @Override
    public void showRepositories(List<Repository> repositoryList) {
        adapter.setRepositoryList(repositoryList);
        swiperefresh.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        swiperefresh.setRefreshing(true);
    }

    @Override
    public void showError() {
        //TODO show error view or message
        swiperefresh.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.setLoadMoreListener(null);
    }
}
