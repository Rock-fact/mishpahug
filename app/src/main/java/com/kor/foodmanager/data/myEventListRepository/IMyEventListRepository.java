package com.kor.foodmanager.data.myEventListRepository;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;

import java.io.IOException;
import java.util.List;

public interface IMyEventListRepository {
    List<EventDto> loadMyEventList (String token) throws ServerException, IOException;
}
