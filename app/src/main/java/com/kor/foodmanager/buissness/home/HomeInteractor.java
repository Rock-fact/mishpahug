package com.kor.foodmanager.buissness.home;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.model.UserDto;

public class HomeInteractor implements IHomeInteractor {
    private IAuthRepository authRepository;
    private UserDto userDto;

    public HomeInteractor(IAuthRepository authRepository){
        this.authRepository = authRepository;
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

}
