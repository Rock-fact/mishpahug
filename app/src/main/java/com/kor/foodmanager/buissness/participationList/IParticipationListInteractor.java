package com.kor.foodmanager.buissness.participationList;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;

import java.io.IOException;
import java.util.List;

public interface IParticipationListInteractor {
    List<EventDto> getParticipationList() throws IOException, ServerException;
}
