package com.kor.foodmanager.di.MyEventInProgress;

import com.google.gson.Gson;
import com.kor.foodmanager.buissness.MyEventInProgressInteractor.IMyEventInProgressInteractor;
import com.kor.foodmanager.buissness.MyEventInProgressInteractor.MyEventInProgressInteractor;
import com.kor.foodmanager.data.MyEventInProgressRepository.IMyEventInProgressRepository;
import com.kor.foodmanager.data.MyEventInProgressRepository.MyEventInProgressRepository;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class MyEventInProgressModule {

    @Provides
    @MyEventInProgressScope
    IMyEventInProgressRepository provideMyEventInProgressRepository(Api api, Gson gson){
        return new MyEventInProgressRepository(api,gson);
    }
    @Provides
    @MyEventInProgressScope
    IMyEventInProgressInteractor provideMyEventInProgressInteractor(IAuthRepository authRepository, IMyEventInProgressRepository myEventInProgressRepository){
        return new MyEventInProgressInteractor(authRepository,myEventInProgressRepository);
    }
}
