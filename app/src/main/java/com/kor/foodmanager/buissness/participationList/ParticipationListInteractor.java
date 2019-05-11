package com.kor.foodmanager.buissness.participationList;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.participationList.IParticipationListRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ParticipationListInteractor implements IParticipationListInteractor{
    private IAuthRepository authRepository;
    private IParticipationListRepository participationListRepository;

    public ParticipationListInteractor(IAuthRepository authRepository, IParticipationListRepository participationListRepository) {
        this.authRepository = authRepository;
        this.participationListRepository = participationListRepository;
    }

    @Override
    public List<EventDto> getParticipationList() throws IOException, ServerException {
        List <EventDto> sortedList = participationListRepository.loadParticipationList(authRepository.getToken());
        Collections.sort(sortedList, new EventComparator());
        return sortedList;
    }
}
