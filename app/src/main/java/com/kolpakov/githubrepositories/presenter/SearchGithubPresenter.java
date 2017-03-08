package com.kolpakov.githubrepositories.presenter;

import com.kolpakov.githubrepositories.data.GithubRepository;
import com.kolpakov.githubrepositories.data.models.Repositories;
import com.kolpakov.githubrepositories.data.models.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pkolpakov on 07.03.2017.
 */

public class SearchGithubPresenter {
    private IView view;
    private GithubRepository githubRepository = new GithubRepository();
    private Subscription subscription;
    private AtomicBoolean isExecuting = new AtomicBoolean(false);
    private List<Repository> repositoryList = new ArrayList<>();
    private String next;

    public void search(String query){
        if(query == null || query.trim().isEmpty() || view == null)
            return;
        unsubscribe();
        if(isExecuting.compareAndSet(false,true)) {
            showLoading();
            subscription = Observable.just(null).delay(1, TimeUnit.SECONDS).flatMap(s -> githubRepository.getRepositories(query)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Repositories>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            isExecuting.set(false);
                            showError();
                        }

                        @Override
                        public void onNext(Repositories repositories) {
                            isExecuting.set(false);
                            repositoryList = repositories.getItems();
                            next = repositories.getPageLinks().getNext();
                            showRepositories(repositoryList);
                        }
                    });
        }

    }
    public void loadNext(){
        if(isExecuting.compareAndSet(false,true)){
            if(next != null){
                showLoading();
                subscription = githubRepository.getRepositoriesFromUrl(next).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Repositories>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        isExecuting.set(false);
                        showError();
                    }

                    @Override
                    public void onNext(Repositories repositories) {
                        isExecuting.set(false);
                        repositoryList.addAll(repositories.getItems());
                        next = repositories.getPageLinks().getNext();
                        showRepositories(repositoryList);
                    }
                });
            }
        }
    }
    public void attachView(IView view){
        this.view = view;
        showRepositories(repositoryList);
        if(isExecuting.get())
            showLoading();
    }
    public void onDestroy(){
        onDetachView();
        unsubscribe();
    }
    public void onDetachView(){
        view = null;
    }

    private void unsubscribe(){
        if(subscription != null) {
            subscription.unsubscribe();
            isExecuting.set(false);
        }
    }
    private void showLoading(){
        if(view != null)
            view.showLoading();
    }
    private void showError(){
        if(view != null)
            view.showError();
    }
    private void showRepositories(List<Repository> repositoryList){
        if(view != null)
            view.showRepositories(repositoryList);
    }

}
