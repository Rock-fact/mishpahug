package com.kor.foodmanager.data.login;

import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;

import java.io.IOException;

public interface ILoginRepository {
    UserDto login(String token) throws LoginException, IOException;
    StaticfieldsDto registration(String token) throws LoginException, IOException;
}
