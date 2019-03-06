package com.kor.foodmanager.data.participationList;

import com.kor.foodmanager.data.model.EventListDto;

public interface IParticipationListRepository {
    EventListDto loadParticipationList (String token);
}
