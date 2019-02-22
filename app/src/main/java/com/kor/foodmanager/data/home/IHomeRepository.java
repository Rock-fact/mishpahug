package com.kor.foodmanager.data.home;

import com.kor.foodmanager.data.model.ErrorDto;
import com.kor.foodmanager.data.model.EventDto;

import java.io.IOException;

public interface IHomeRepository {
    ErrorDto addNewEvent(String token, EventDto event) throws IOException, ServerException;
}
