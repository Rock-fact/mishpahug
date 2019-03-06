package com.kor.foodmanager.buissness.participationList;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.participationList.IParticipationListRepository;

import java.io.IOException;

public class ParticipationListInteractor implements IParticipationListInteractor{
    private IAuthRepository authRepository;
    private IParticipationListRepository participationListRepository;

    public ParticipationListInteractor(IAuthRepository authRepository, IParticipationListRepository participationListRepository) {
        this.authRepository = authRepository;
        this.participationListRepository = participationListRepository;
    }

    @Override
    public EventListDto getParticipationList() throws IOException, ServerException {
        return participationListRepository.loadParticipationList(authRepository.getToken());
    }
}
