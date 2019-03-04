package com.kor.foodmanager.buissness.eventInfo;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.eventInfo.IGuestEventInfoRepository;

import java.io.IOException;

public class GuestEventInfoInteractor implements IGuestEventInfoInteractor{
    private IAuthRepository authRepository;
    private IGuestEventInfoRepository guestEventInfoRepository;

    public GuestEventInfoInteractor(IAuthRepository authRepository, IGuestEventInfoRepository guestEventInfoRepository) {
        this.authRepository = authRepository;
        this.guestEventInfoRepository = guestEventInfoRepository;
    }

    @Override
    public String joinEvent(long eventId) throws IOException, ServerException {
        String token = authRepository.getToken();
        String res = guestEventInfoRepository.joinEvent(token,eventId);
        return res;
    }
}
