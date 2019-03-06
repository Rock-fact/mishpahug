package com.kor.foodmanager.di.participationList;

import com.google.gson.Gson;
import com.kor.foodmanager.buissness.participationList.IParticipationListInteractor;
import com.kor.foodmanager.buissness.participationList.ParticipationListInteractor;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.participationList.IParticipationListRepository;
import com.kor.foodmanager.data.participationList.ParticipationListRepository;
import com.kor.foodmanager.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class ParticipationListModule {
    @Provides @ParticipationListScope
    IParticipationListRepository provideParticipationListRepository(Api api, Gson gson){
        return new ParticipationListRepository(api, gson);
    }

    @Provides @ParticipationListScope
    IParticipationListInteractor provideParticipationListInteractor(IAuthRepository authRepository, IParticipationListRepository participationListRepository){
        return new ParticipationListInteractor(authRepository, participationListRepository);
    }
}
