package com.kor.foodmanager.buissness.home;

import com.kor.foodmanager.data.home.ServerException;
import com.kor.foodmanager.data.model.EventDto;

import java.io.IOException;

public interface IHomeInteractor {
    String getName();
    void logout();
    void addNewEvent(EventDto event) throws IOException, ServerException;
}
