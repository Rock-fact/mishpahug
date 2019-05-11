package com.kor.foodmanager.buissness.MyEventInProgressInteractor;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.InvitationStatusDto;

import java.io.IOException;

public interface IMyEventInProgressInteractor {
    InvitationStatusDto inviteToEvent(Long eventId, Long userId)throws ServerException, IOException;
    EventDto changeEventStatus(Long eventId) throws ServerException, IOException;
}

