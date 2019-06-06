package com.project.appstreetassignment.di.module;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.project.appstreetassignment.data.Repository;
import com.project.appstreetassignment.data.db.SampleRoomDatabase;
import com.project.appstreetassignment.data.restApi.ApiClient;
import com.project.appstreetassignment.data.restApi.ApiInterface;
import com.project.appstreetassignment.utils.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }


    @Provides
    @Singleton
    SampleRoomDatabase provideWordRoomDatabase(Context context) {
        return SampleRoomDatabase.getDatabase(this.context);
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface() {
        return ApiClient.getClient().create(ApiInterface.class);
    }

    @Provides
    @Singleton
    Repository provideRepository(ApiInterface apiInterface, SampleRoomDatabase db) {
        return new Repository(db, apiInterface);
    }


    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(Repository myRepository) {
        return new ViewModelFactory(myRepository);
    }

}
