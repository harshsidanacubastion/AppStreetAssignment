package com.project.appstreetassignment.di.component;

import com.project.appstreetassignment.di.module.AppModule;
import com.project.appstreetassignment.di.module.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {
}
