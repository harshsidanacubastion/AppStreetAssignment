package com.project.appstreetassignment.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.project.appstreetassignment.data.Repository;
import com.project.appstreetassignment.ui.newsList.NewsListViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }




    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
//            return (T) new LoginViewModel(repository);
//        }
        if (modelClass.isAssignableFrom(NewsListViewModel.class)) {
            return (T) new NewsListViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown class name");
    }
}