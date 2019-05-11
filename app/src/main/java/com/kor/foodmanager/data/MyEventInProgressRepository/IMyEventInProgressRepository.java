package com.kor.foodmanager.data.MyEventInProgressRepository;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.InvitationStatusDto;

import java.io.IOException;

public interface IMyEventInProgressRepository {
    InvitationStatusDto inviteToEvent(String token, Long eventId, Long userId)throws ServerException, IOException;
    EventDto changeEventStatus(String token, Long eventId) throws ServerException, IOException;

}
