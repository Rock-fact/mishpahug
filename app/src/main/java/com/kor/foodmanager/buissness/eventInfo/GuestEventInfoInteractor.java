package com.kor.foodmanager.buissness.eventInfo;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.eventInfo.IGuestEventInfoRepository;

import java.io.IOException;

public class GuestEventInfoInteractor implements IGuestEventInfoInteractor{
    //private IAuthRepository authRepository;
    private IGuestEventInfoRepository guestEventInfoRepository;
    private String token, res;

    public GuestEventInfoInteractor(IAuthRepository authRepository, IGuestEventInfoRepository guestEventInfoRepository) {
        //this.authRepository = authRepository;
        this.guestEventInfoRepository = guestEventInfoRepository;
        token = authRepository.getToken();
    }

    @Override
    public String joinEvent(long eventId) throws IOException, ServerException {
        res = guestEventInfoRepository.joinEvent(token,eventId);
        return res;
    }

    @Override
    public String unsubscribeFromEvent(long eventId) throws IOException, ServerException {
        res = guestEventInfoRepository.unsubscribeFromEvent(token, eventId);
        return res;
    }
}
