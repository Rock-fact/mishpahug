package com.kor.foodmanager.buissness.participationList;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventListDto;

import java.io.IOException;

public interface IParticipationListInteractor {
    EventListDto getParticipationList() throws IOException, ServerException;
}
