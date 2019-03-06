package com.kor.foodmanager.data.participationList;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;

import java.io.IOException;
import java.util.List;

public interface IParticipationListRepository {
    List<EventDto> loadParticipationList (String token) throws IOException, ServerException;
}
