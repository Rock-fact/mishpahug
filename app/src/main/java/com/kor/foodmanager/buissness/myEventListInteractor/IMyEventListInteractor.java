package com.kor.foodmanager.buissness.myEventListInteractor;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;

import java.io.IOException;
import java.util.List;

public interface IMyEventListInteractor {
    List<EventDto> getMyEventList() throws IOException, ServerException;
}
