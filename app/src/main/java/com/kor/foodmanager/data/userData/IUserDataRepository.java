package com.kor.foodmanager.data.userData;

import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.model.UserDto;

public interface IUserDataRepository {

    StaticfieldsDto staticFields() throws Exception;
    UserDto updateUserProfile(UserDto user) throws Exception;
}
