package com.kor.foodmanager.di.application;

import com.kor.foodmanager.di.home.HomeComponent;
import com.kor.foodmanager.di.home.HomeModule;
import com.kor.foodmanager.di.login.LoginComponent;
import com.kor.foodmanager.di.login.LoginModule;
import com.kor.foodmanager.ui.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {MainModule.class})
@Singleton
public interface MainComponent{
    void inject(MainActivityPresenter presenter);
    LoginComponent plus(LoginModule module);
    HomeComponent plus(HomeModule module);
}
