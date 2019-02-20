package com.kor.foodmanager.di.home;

import com.kor.foodmanager.buissness.home.HomeInteractor;
import com.kor.foodmanager.buissness.home.IHomeInteractor;
import com.kor.foodmanager.data.auth.IAuthRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides @HomeScope
    IHomeInteractor provideHomeInteractor(IAuthRepository authRepository){
        return new HomeInteractor(authRepository);
    }
}
