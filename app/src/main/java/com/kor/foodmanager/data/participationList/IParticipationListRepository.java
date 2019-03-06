package com.kor.foodmanager.data.participationList;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventListDto;

import java.io.IOException;

public interface IParticipationListRepository {
    EventListDto loadParticipationList (String token) throws IOException, ServerException;
}
