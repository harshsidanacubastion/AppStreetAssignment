package com.project.appstreetassignment.ui.newsList;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.project.appstreetassignment.data.Repository;
import com.project.appstreetassignment.data.model.Article;
import com.project.appstreetassignment.utils.ApiResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    private final MutableLiveData<Article> dbResponseLiveData = new MutableLiveData<>();


    public NewsListViewModel(Repository repository) {
        this.repository = repository;
    }


    public MutableLiveData<ApiResponse> fetchApiResponse() {
        return responseLiveData;
    }

    public MutableLiveData<Article> fetchDbResponse() {
        return dbResponseLiveData;
    }


    public void fetchDataFromApi(String country, String category,long pageNo) {


        disposables.add(repository.fetchData(country, category,pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    public void fetchDataFromDb(String country, String category) {
        disposables.add(repository.getFromDb(country, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    public void updateInDb(Article article, String country, String category) {
        disposables.add(repository.fetchPhoto(article, country, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> dbResponseLiveData.setValue(result),
                        throwable -> dbResponseLiveData.setValue(null)
                ));
    }


    @Override
    protected void onCleared() {
        disposables.clear();
    }
}