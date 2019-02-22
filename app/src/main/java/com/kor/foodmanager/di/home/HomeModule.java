package com.kor.foodmanager.di.home;

import com.google.gson.Gson;
import com.kor.foodmanager.buissness.home.HomeInteractor;
import com.kor.foodmanager.buissness.home.IHomeInteractor;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.home.HomeRepository;
import com.kor.foodmanager.data.home.IHomeRepository;
import com.kor.foodmanager.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides @HomeScope
    IHomeInteractor provideHomeInteractor(IAuthRepository authRepository, IHomeRepository homeRepository){
        return new HomeInteractor(authRepository, homeRepository);
    }

    @Provides @HomeScope
    IHomeRepository provideHomeRepository(Api api, Gson gson){
        return new HomeRepository(api, gson);
    }
}
