package com.kor.foodmanager.di.event;

import com.google.gson.Gson;
import com.kor.foodmanager.buissness.event.EventInteractor;
import com.kor.foodmanager.buissness.event.IEventInteractor;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.EventRepository;
import com.kor.foodmanager.data.event.IEventRepository;
import com.kor.foodmanager.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class EventModule {

    @Provides @EventScope
    IEventInteractor provideHomeInteractor(IAuthRepository authRepository, IEventRepository homeRepository){
        return new EventInteractor(authRepository, homeRepository);
    }

    @Provides @EventScope
    IEventRepository provideHomeRepository(Api api, Gson gson, OkHttpClient okHttpClient){
        return new EventRepository(api, gson, okHttpClient);
    }
}
