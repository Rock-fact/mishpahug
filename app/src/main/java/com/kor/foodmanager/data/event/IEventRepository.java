package com.kor.foodmanager.data.event;

import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.MessageDto;

import java.io.IOException;

public interface IEventRepository {
    MessageDto addNewEvent(String token, EventDto event) throws IOException, ServerException;
    String getCityName(String placeId, String API_KEY) throws IOException, ServerException;
}
