package com.kor.foodmanager.buissness.home;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.home.IHomeRepository;
import com.kor.foodmanager.data.home.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.UserDto;

import java.io.IOException;

public class HomeInteractor implements IHomeInteractor {
    private IAuthRepository authRepository;
    private IHomeRepository homeRepository;
    private UserDto userDto;

    public HomeInteractor(IAuthRepository authRepository, IHomeRepository homeRepository){
        this.authRepository = authRepository;
        this.homeRepository = homeRepository;
        userDto = authRepository.getUser();
    }

    @Override
    public String getName() {
        if(userDto!=null){
            return userDto.getFirstName()+" "+userDto.getLastName();
        }else {
            return "Guest";
        }

    }

    @Override
    public void logout() {
        authRepository.clearToken();
    }

    @Override
    public void addNewEvent(EventDto event) throws IOException, ServerException {
        String token = authRepository.getToken();
        homeRepository.addNewEvent(token, event);
    }

}
