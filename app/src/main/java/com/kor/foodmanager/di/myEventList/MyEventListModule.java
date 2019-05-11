package com.kor.foodmanager.di.myEventList;

import com.google.gson.Gson;
import com.kor.foodmanager.buissness.myEventListInteractor.IMyEventListInteractor;
import com.kor.foodmanager.buissness.myEventListInteractor.MyEventListInteractor;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.myEventListRepository.IMyEventListRepository;
import com.kor.foodmanager.data.myEventListRepository.MyEventListRepository;
import com.kor.foodmanager.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;
@Module
public class MyEventListModule {

    @Provides
    @MyEventListScope
    IMyEventListRepository provideMyEventListRepository(Api api, Gson gson){
        return new MyEventListRepository(api,gson);
    }
    @Provides
    @MyEventListScope
    IMyEventListInteractor provideMyEventListInteractor(IAuthRepository authRepository, IMyEventListRepository myEventListRepository){
        return new MyEventListInteractor(authRepository, myEventListRepository);
    }
}
