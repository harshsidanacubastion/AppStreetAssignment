package com.project.appstreetassignment.di.component;

import com.project.appstreetassignment.di.module.AppModule;
import com.project.appstreetassignment.di.module.UtilsModule;
import com.project.appstreetassignment.ui.newsList.NewsListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {
    void doInjection(NewsListActivity newsListActivity);
}
