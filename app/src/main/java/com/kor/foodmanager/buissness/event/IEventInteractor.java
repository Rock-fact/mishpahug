package com.kor.foodmanager.buissness.event;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.MessageDto;
import com.kor.foodmanager.data.model.UserDto;

import java.io.IOException;

public interface IEventInteractor {
    MessageDto addNewEvent(EventDto event) throws IOException, ServerException;

    UserDto getUser();
}
