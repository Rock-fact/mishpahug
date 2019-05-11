package com.kor.foodmanager.buissness.MyEventInProgressInteractor;

import com.kor.foodmanager.data.MyEventInProgressRepository.IMyEventInProgressRepository;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.InvitationStatusDto;

import java.io.IOException;

public class MyEventInProgressInteractor implements IMyEventInProgressInteractor{
    private IAuthRepository authRepository;
    private IMyEventInProgressRepository iMyEventInProgressRepository;

    public MyEventInProgressInteractor(IAuthRepository authRepository, IMyEventInProgressRepository iMyEventInProgressRepository) {
        this.authRepository = authRepository;
        this.iMyEventInProgressRepository = iMyEventInProgressRepository;
    }

    @Override
    public InvitationStatusDto inviteToEvent(Long eventId, Long userId) throws ServerException, IOException {
        return iMyEventInProgressRepository.inviteToEvent(authRepository.getToken(),eventId,userId);
    }

    @Override
    public EventDto changeEventStatus(Long eventId) throws ServerException, IOException {
        return iMyEventInProgressRepository.changeEventStatus(authRepository.getToken(),eventId);
    }
}
