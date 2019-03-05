package com.kor.foodmanager.buissness.event;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.NotificationListDto;
import com.kor.foodmanager.data.model.UserDto;

import java.io.IOException;

public interface IEventInteractor {
    void addNewEvent(EventDto event) throws IOException, ServerException;

    UserDto getUser();
}
