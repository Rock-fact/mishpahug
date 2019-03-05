package com.kor.foodmanager.buissness.event;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.IEventRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.UserDto;

import java.io.IOException;

public class EventInteractor implements IEventInteractor {
    private IAuthRepository authRepository;
    private IEventRepository homeRepository;

    public EventInteractor(IAuthRepository authRepository, IEventRepository homeRepository){
        this.authRepository = authRepository;
        this.homeRepository = homeRepository;
    }

    @Override
    public void addNewEvent(EventDto event) throws IOException, ServerException {
        String token = authRepository.getToken();
        homeRepository.addNewEvent(token, event);
    }

    @Override
    public UserDto getUser(){
        return authRepository.getUser();
    }

}
