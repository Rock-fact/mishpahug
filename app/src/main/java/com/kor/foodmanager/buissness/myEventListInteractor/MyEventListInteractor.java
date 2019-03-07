package com.kor.foodmanager.buissness.myEventListInteractor;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.myEventListRepository.IMyEventListRepository;

import java.io.IOException;
import java.util.List;

public class MyEventListInteractor implements IMyEventListInteractor {
    private IAuthRepository authRepository;
    private IMyEventListRepository myEventListRepository;

    public MyEventListInteractor(IAuthRepository authRepository, IMyEventListRepository myEventListRepository) {
        this.authRepository = authRepository;
        this.myEventListRepository = myEventListRepository;
    }

    @Override
    public List<EventDto> getMyEventList() throws IOException, ServerException {
        return myEventListRepository.loadMyEventList(authRepository.getToken());
    }
}
