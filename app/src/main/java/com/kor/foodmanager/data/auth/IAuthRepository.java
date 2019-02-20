package com.kor.foodmanager.data.auth;

import com.kor.foodmanager.data.model.UserDto;

public interface IAuthRepository {
    void saveToken(String token);
    String getToken();
    void clearToken();
    void saveUser(UserDto userDto);
    UserDto getUser();
}
