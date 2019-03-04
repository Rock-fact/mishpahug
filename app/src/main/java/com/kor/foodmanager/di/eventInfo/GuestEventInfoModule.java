package com.kor.foodmanager.di.eventInfo;

import com.google.gson.Gson;
import com.kor.foodmanager.buissness.eventInfo.GuestEventInfoInteractor;
import com.kor.foodmanager.buissness.eventInfo.IGuestEventInfoInteractor;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.eventInfo.GuestEventInfoRepository;
import com.kor.foodmanager.data.eventInfo.IGuestEventInfoRepository;
import com.kor.foodmanager.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class GuestEventInfoModule {
    @Provides @GuestEventInfoScope
    IGuestEventInfoInteractor provideGuestEventInfoInteractor(IGuestEventInfoRepository guestEventInfoRepository,
                                                              IAuthRepository authRepository){
        return new GuestEventInfoInteractor(authRepository, guestEventInfoRepository);
    }

    @Provides @GuestEventInfoScope
    IGuestEventInfoRepository provideGuestEventInfoRepository(Api api, Gson gson){
        return new GuestEventInfoRepository(api, gson);
    }
}
